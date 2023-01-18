package engine.graphics;

import engine.graphics.threed.Triangle;
import engine.graphics.threed.Vertex;
import engine.math.FinalVector;
import engine.math.Vector;
import engine.utils.MathUtils;

public final class ImageAlgorithms3D 
{
    private ImageAlgorithms3D() { }
    
    public static void renderTestMeshWithTestImage(final DrawableImage image)
    {
        final DrawableImage texture = new DrawableImage(new FinalVector(2, 2));
        
        texture.setPixel(0, 1, 0xFFFF0000);
        texture.setPixel(1, 1, 0xFF00FF00);
        texture.setPixel(2, 1, 0xFF0000FF);
        texture.setPixel(3, 1, 0xFFFFFFFF);
        
        final Triangle[] triangles = new Triangle[]
        {
            new Triangle
            (
                new Vertex(42, 23, 1, 1, new Vector(0.25, 0.25, 1), new Vector(0, 0, 0)),
                new Vertex(1452, 345, 1, 1, new Vector(0.25, 1   , 1), new Vector(0, 0, 0)),
                new Vertex(23, 700, 1, 1, new Vector(0.75, 0.25, 1), new Vector(0, 0, 0))
            ),
            new Triangle
            (
                new Vertex(180,  50, 0, 1, new Vector(1, 1, 1), new Vector(0, 0, 0)),
                new Vertex(150,   1, 0, 1, new Vector(0, 1, 1), new Vector(0, 0, 0)),
                new Vertex(70 , 180, 0, 1, new Vector(1, 0, 1), new Vector(0, 0, 0))
            ),
            new Triangle
            (
                new Vertex(180, 150, 0, 1, new Vector(0, 0, 1), new Vector(0, 0, 0)),
                new Vertex(120, 160, 0, 1, new Vector(1, 0, 1), new Vector(0, 0, 0)),
                new Vertex(130, 180, 0, 1, new Vector(0, 1, 1), new Vector(0, 0, 0))
            ),
            new Triangle
            (
                new Vertex(180, 150, 0, 1, new Vector(1, 1, 1), new Vector(0, 0, 0)),
                new Vertex(120, 160, 0, 1, new Vector(0, 1, 1), new Vector(0, 0, 0)),
                new Vertex(130, 180, 0, 1, new Vector(1, 0, 1), new Vector(0, 0, 0))
            )
        };

        Image texture2 = Image.fromFile("./rsc/images/A.png");

        for (final Triangle triangle : triangles)
        {
            triangle(image, triangle, texture2);
        }
    }

    static void line(final DrawableImage image, final Vertex a, final Vertex b, final Image texture)
    {
        final double dxd = Math.abs(b.x - a.x);
        double dyd = Math.abs(b.y - a.y);
        dyd = dyd == 0 ? 1 : dyd;

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
            image.drawPixel(x, y, z, texture.pixelUVW(u, v, w));
            
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

    static void triangle(final DrawableImage image, Triangle triangle, final Image texture)
    {
        //Sort points for y ascending
        triangle = triangle.sortedForY();

        final Vertex a = triangle.a;
        final Vertex b = triangle.b;
        final Vertex c = triangle.c;

        if      (b.y == c.y) fillBottomFlatTriangle(image, a, b, c, texture);
        else if (a.y == b.y) fillTopFlatTriangle   (image, a, b, c, texture);
        else                 fillNoneFlatTriangle  (image, a, b, c, texture);
    }

    static void fillNoneFlatTriangle(final DrawableImage image, final Vertex a, final Vertex b, final Vertex c, final Image texture)
    {
        final double v_slope = (b.y - a.y) / (c.y - a.y); // slope between c and a on y axis

        final double v_dcax = (c.x - a.x); // distance between c and a on x axis
        final double v_dcay = (c.y - a.y); // distance between c and a on y axis

        final double x = a.x + (v_slope * v_dcax);
        final double y = b.y;

        final double dl = Math.sqrt(v_dcax * v_dcax + v_dcay * v_dcay);
        final double dp = Math.sqrt((x - a.x) * (x - a.x) + (y - a.y) * (y - a.y));

        final double t = dp / dl;

        Vertex d = new Vertex
        (
            a.x + (v_slope * v_dcax),
            b.y,
            MathUtils.lerp(a.z, c.z, t),
            MathUtils.lerp(a.w, c.w, t),
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

    static void fillBottomFlatTriangle(final DrawableImage image, final Vertex a, final Vertex b, final Vertex c, final Image texture)
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
                curx1,
                scanlineY,
                MathUtils.lerp(a.z, b.z, t),
                MathUtils.lerp(a.w, b.w, t),
                Vector.lerp(a.texture, b.texture, t),
                Vector.lerp(a.normal, b.normal, t)
            );

            final Vertex v2 = new Vertex
            (
                curx2,
                scanlineY,
                MathUtils.lerp(a.z, c.z, t),
                MathUtils.lerp(a.w, c.w, t),
                Vector.lerp(a.texture, c.texture, t),
                Vector.lerp(a.normal, c.normal, t)
            );

            line(image, v1, v2, texture);

            curx1 += invslope1;
            curx2 += invslope2;
        }
    }

    static void fillTopFlatTriangle(final DrawableImage image, final Vertex a, final Vertex b, final Vertex c, final Image texture)
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
                curx1,
                scanlineY,
                MathUtils.lerp(a.z, c.z, t),
                MathUtils.lerp(a.w, c.w, t),
                Vector.lerp(a.texture, c.texture, t),
                Vector.lerp(a.normal , c.normal, t)
            );

            final Vertex v2 = new Vertex
            (
                curx2,
                scanlineY,
                MathUtils.lerp(b.z, c.z, t),
                MathUtils.lerp(b.w, c.w, t),
                Vector.lerp(b.texture, c.texture, t),
                Vector.lerp(b.normal , c.normal, t)
            );

            line(image, v1, v2, texture);
            
            curx1 -= invslope1;
            curx2 -= invslope2;
        }
    }
}
