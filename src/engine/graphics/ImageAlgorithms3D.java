package engine.graphics;

import engine.graphics.threed.Triangle;
import engine.graphics.threed.Vertex;
import engine.math.Vector;
import engine.utils.MathUtils;

final class ImageAlgorithms3D 
{
    private ImageAlgorithms3D() { }
    
    static void line(final DrawableImage image, final Vertex a, final Vertex b, final Image texture)
    {
        final int dx = (int) Math.abs(b.x - a.x);
        final int dy = (int) Math.abs(b.y - a.y);

        final int sx = a.x < b.x ? 1 : -1;
        final int sy = a.y < b.y ? 1 : -1;

        int err = dx + dy;
        int e2 = 0;
        
        int x = (int) a.x;
        int y = (int) a.y;

        final double increment = 1 / (dx > dy ? dx : dy);

        double t = 0;

        final int[] colorBuffer = texture.colorBuffer;

        while (true)
        {
            image.drawPixel(x, y, MathUtils.lerp(a.z, b.z, t), 
                colorBuffer[
                    (int) MathUtils.lerp(a.texture.x, b.texture.x, t) * image.width () + 
                    (int) MathUtils.lerp(a.texture.y, b.texture.y, t) * image.height() * image.width()
                ]
            );

            if (x == b.x && y == b.y) break;

            e2 = 2 * err;
            
            if (e2 > -dy) { err -= dy; x += sx; }
            if (e2 <  dx) { err += dx; y += sy; }
        
            t += increment;
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
        else
        {
            final double x = a.x + ((b.y - a.y) / (c.y - a.y)) * (c.x - a.x);
            final double y = b.y;
            	
            final double dx = (c.x - a.x);
            final double dy = (c.y - a.y);

            final double pdx = (c.x - x); // distance from point to c
            final double pdy = (c.y - y);

            final double t = Math.sqrt(pdx * pdx + pdx * pdy) / Math.sqrt(dx * dx + dy * dy);

            final Vertex d = new Vertex
            (
                x,
                y,
                MathUtils.lerp(a.z, c.z, t),
                MathUtils.lerp(a.w, c.w, t),
                Vector.lerp(a.texture, c.texture, t),
                Vector.lerp(a.normal, c.normal, t)
            );

            fillBottomFlatTriangle(image, a, b, d, texture);
            fillTopFlatTriangle   (image, b, d, c, texture);
        }
    }

    static void fillBottomFlatTriangle(final DrawableImage image, final Vertex a, final Vertex b, final Vertex c, final Image texture)
    {
        double invslope1 = (b.x() - a.x()) / (b.y() - a.y());
        double invslope2 = (c.x() - a.x()) / (c.y() - a.y());

        double curx1 = a.x();
        double curx2 = a.x();

        for (int scanlineY = (int) a.y(); scanlineY <= b.y(); scanlineY++)
        {
            line(image, (int) curx1, scanlineY, (int) curx2, scanlineY, 1, texture);

            curx1 += invslope1;
            curx2 += invslope2;
        }
    }

    static void fillTopFlatTriangle(final DrawableImage image, final Vertex a, final Vertex b, final Vertex c, final Image texture)
    {
        double invslope1 = (c.x() - a.x()) / (c.y() - a.y());
        double invslope2 = (c.x() - b.x()) / (c.y() - b.y());

        double curx1 = c.x();
        double curx2 = c.x();

        for (int scanlineY = (int) c.y(); scanlineY > a.y(); scanlineY--)
        {
            line(image, (int) curx1, scanlineY, (int) curx2, scanlineY, 1, texture);

            curx1 -= invslope1;
            curx2 -= invslope2;
        }
    }
}
