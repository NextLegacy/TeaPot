package engine.graphics;

import engine.math.FinalVector;
import engine.math.Matrix;
import engine.math.Vector;
import engine.threed.Camera;
import engine.threed.Mesh;
import engine.threed.Triangle;
import engine.threed.Vertex;
import engine.utils.MathUtils;

final class ImageAlgorithms3D 
{
    private ImageAlgorithms3D() { }
    
    static void line(final DrawableImage image, 
        final Vertex a, final Vertex b, final Image texture)
    {
        final double dxd = Math.abs(b.x - a.x);
        final double dyd = b.y == a.y ? 1 : Math.abs(b.y - a.y);

        final int dx = (int) Math.abs((int)b.x - (int)a.x);
        final int dy = (int) Math.abs((int)b.y - (int)a.y);

        final int sx = ((int) a.x) < ((int) b.x) ? 1 : -1;
        final int sy = ((int) a.y) < ((int) b.y) ? 1 : -1;

        int err = dx - dy;
        int e2 = 0;

        int x = (int) a.x;
        int y = (int) a.y;

        double xd = a.x;
        double yd = a.y;
        double z = a.z;

        double u = a.texture.x;
        double v = a.texture.y;
        double w = a.texture.z;

        double dl = Math.sqrt(dxd * dxd + dyd * dyd);

        while (true)
        {
            image.drawPixel(x, y, z, texture.getPixel(u, v, w));
            
            double dp = Math.sqrt((xd - x) * (xd - x) + (yd - y) * (yd - y));

            double t = dp / dl;

            if (x == (int) b.x && y == (int) b.y) break;

            e2 = 2 * err;

            if (e2 > -dy) { err -= dy; x += sx; }
            if (e2 <  dx) { err += dx; y += sy; }

            z = MathUtils.lerp(a.z, b.z, t);

            u = MathUtils.lerp(a.texture.x, b.texture.x, t);
            v = MathUtils.lerp(a.texture.y, b.texture.y, t);
            w = MathUtils.lerp(a.texture.z, b.texture.z, t);
        }
    }

    static void triangleUnchecked(final DrawableImage image,
        final Triangle triangle, final Image texture)
    {
        Vertex a = triangle.a; Vertex b = triangle.b; Vertex c = triangle.c;

        if      (b.y == c.y) fillBottomFlatTriangle(image, a, b, c, texture);
        else if (a.y == b.y) fillTopFlatTriangle   (image, a, b, c, texture);
        else                 fillNoneFlatTriangle  (image, a, b, c, texture);
    }

    static void triangle(final DrawableImage image, 
        final Triangle triangle, final Image texture)
    {
        triangleUnchecked(image, triangle.sortedForY(), texture);
    }

    static void fillNoneFlatTriangle(final DrawableImage image, 
        final Vertex a, final Vertex b, final Vertex c, final Image texture)
    {
        final double v_slope = (b.y - a.y) / (c.y - a.y);

        final double v_dcax = (c.x - a.x);
        final double v_dcay = (c.y - a.y);

        final double x = a.x + (v_slope * v_dcax);
        final double y = b.y;

        final double dl = Math.sqrt(v_dcax * v_dcax + v_dcay * v_dcay);
        final double dp = Math.sqrt((x - a.x) * (x - a.x) + (y - a.y) * (y - a.y));

        final double t = dp / dl;

        Vertex d = new Vertex
        (
            new FinalVector(
                a.x + (v_slope * v_dcax),
                b.y,
                MathUtils.lerp(a.z, c.z, t),
                MathUtils.lerp(a.w, c.w, t)
            ),
            new FinalVector
            (
                MathUtils.lerp(a.texture.x, c.texture.x, t),
                MathUtils.lerp(a.texture.y, c.texture.y, t),
                MathUtils.lerp(a.texture.z, c.texture.z, t)
            ),
            new FinalVector
            (
                MathUtils.lerp(a.normal.x, c.normal.x, t),
                MathUtils.lerp(a.normal.y, c.normal.y, t),
                MathUtils.lerp(a.normal.z, c.normal.z, t),
                MathUtils.lerp(a.normal.w, c.normal.w, t)
            )
        );

        fillBottomFlatTriangle(image, a, b, d, texture);
        fillTopFlatTriangle   (image, b, d, c, texture);
    }

    static void fillBottomFlatTriangle(final DrawableImage image, 
        final Vertex a, final Vertex b, final Vertex c, final Image texture)
    {
        double invslope1 = (b.x - a.x) / (b.y - a.y);
        double invslope2 = (c.x - a.x) / (c.y - a.y);

        double curx1 = a.x;
        double curx2 = a.x;

        final double dl = Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y));

        for (int scanlineY = (int) a.y; scanlineY <= b.y; scanlineY++)
        {
            final double dp = Math.sqrt((curx1 - a.x) * (curx1 - a.x) + (scanlineY - a.y) * (scanlineY - a.y));

            final double t = dp / dl;
            
            final Vertex v1 = new Vertex
            (
                new FinalVector(
                    curx1,
                    scanlineY,
                    MathUtils.lerp(a.z, b.z, t),
                    MathUtils.lerp(a.w, b.w, t)
                ),
                Vector.lerp(a.texture, b.texture, t),
                Vector.lerp(a.normal, b.normal, t)
            );

            final Vertex v2 = new Vertex
            (
                new FinalVector(
                    curx2,
                    scanlineY,
                    MathUtils.lerp(a.z, c.z, t),
                    MathUtils.lerp(a.w, c.w, t)
                ),
                Vector.lerp(a.texture, c.texture, t),
                Vector.lerp(a.normal, c.normal, t)
            );

            line(image, v1, v2, texture);

            curx1 += invslope1;
            curx2 += invslope2;
        }
    }

    static void fillTopFlatTriangle(final DrawableImage image, 
        final Vertex a, final Vertex b, final Vertex c, final Image texture)
    {
        double invslope1 = (c.x - a.x) / (c.y - a.y);
        double invslope2 = (c.x - b.x) / (c.y - b.y);

        double curx1 = c.x;
        double curx2 = c.x;

        final double dl = Math.sqrt((c.x - a.x) * (c.x - a.x) + (c.y - a.y) * (c.y - a.y));

        for (int scanlineY = (int) c.y; scanlineY > a.y; scanlineY--)
        {
            final double dp = Math.sqrt((curx1 - a.x) * (curx1 - a.x) + (scanlineY - a.y) * (scanlineY - a.y));
            final double t = dp / dl;

            final Vertex v1 = new Vertex
            (
                new FinalVector(
                    curx1,
                    scanlineY,
                    MathUtils.lerp(a.z, c.z, t),
                    MathUtils.lerp(a.w, c.w, t)
                ),
                Vector.lerp(a.texture, c.texture, t),
                Vector.lerp(a.normal , c.normal, t)
            );

            final Vertex v2 = new Vertex
            (
                new FinalVector(
                    curx2,
                    scanlineY,
                    MathUtils.lerp(b.z, c.z, t),
                    MathUtils.lerp(b.w, c.w, t)
                ),
                Vector.lerp(b.texture, c.texture, t),
                Vector.lerp(b.normal , c.normal, t)
            );

            line(image, v1, v2, texture);
            
            curx1 -= invslope1;
            curx2 -= invslope2;
        }
    }

    static Triangle clipAgainstPlane(

    static void mesh(final DrawableImage image, final Mesh mesh, final Matrix world, final Matrix projection, final Matrix view, final Image texture)
    {
        Triangle[] triangles = new Triangle[0];

        for (int i = 0; i < mesh.triangles.length; i++)
        {
            final Triangle triangle = mesh.triangles[i];

            triangles = ArrayUtils.add(triangles, triangle.transform(transform, projection, view));
        }
    }
}
