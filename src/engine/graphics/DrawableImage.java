package engine.graphics;

import java.awt.image.BufferedImage;

import engine.math.FinalVector;
import engine.math.Vector4;
import engine.threed.Triangle;
import engine.threed.Vertex;
import engine.utils.ArrayUtils;
import engine.utils.ImageUtils;
import engine.utils.color.Color;

public class DrawableImage extends Image
{
    public static final int    ERROR_COLOR = 0xffff00ff;
    public static final int    CLEAR_COLOR = 0xff000000;
    public static final double CLEAR_Z     = Double.MAX_VALUE;

    public DrawableImage(final BufferedImage image) { super(ImageUtils.getBufferedImageDataArray(image), new FinalVector(image.getWidth(), image.getHeight())); }
    public DrawableImage(final Vector4       size ) { super(size); clear(); }
    public DrawableImage(final Image         image) { super(image.colorBuffer, image.zBuffer, image.size); }

    public void fillColor(final int    color) { ArrayUtils.fill(colorBuffer, color); }
    public void fillZ    (final double z    ) { ArrayUtils.fill(zBuffer    , z    ); }

    public void fill(final double z, final int color) { fillZ(z); fillColor(color); }

    public void clear() { fill(CLEAR_Z, CLEAR_COLOR); }

    final void overridePixel(final int index, final double z, final int argb)
    { 
        zBuffer    [index] = z   ; 
        colorBuffer[index] = argb; 
    }

    public final void setPixel(int index, double z, int argb)
    {
        if (isPixelValid(index))
            overridePixel(index, z, argb);
    }

    public void drawPixel(int x, int y, double z, int argb) { drawPixel(getIndex(x, y), z, argb); }
    public void drawPixel(int index, double z, int argb)
    {
        if (!isPixelValid(index)) return; 

        final boolean nearer = z < zBuffer[index]; 

        if (!nearer && colorBuffer[index] >> 24 == 0xff) return;

        overridePixel(
            index, 
            nearer ? z : zBuffer[index],
            nearer ? Color.mix(colorBuffer[index], argb) :
                     Color.mix(argb, colorBuffer[index])  
        );
    }

    //
    // BEGIN OF 2D GRAPHICS
    //

    public void drawLine(int x1, int y1, int x2, int y2, double z, int color) 
    { 
        ImageAlgorithms.line(this, x1, y1, x2, y2, z, color); 
    }

    public void drawLine(int x1, int y1, int x2, int y2, double z, int color, int size) 
    { 
        ImageAlgorithms.line(this, x1, y1, x2, y2, z, color, size); 
    }

    public void drawLine(Vector4 p1, Vector4 p2, double z, int color) 
    { 
        drawLine((int) p1.x(), (int) p1.y(), (int) p2.x(), (int) p2.y(), z, color);
    }

    public void drawLine(Vector4 p1, Vector4 p2, double z, int color, int size) 
    { 
        drawLine((int) p1.x(), (int) p1.y(), (int) p2.x(), (int) p2.y(), z, color, size);
    }

    public void fillTriangle(Vector4 a, Vector4 b, Vector4 c, int color) 
    { 
        ImageAlgorithms.triangle(this, a, b, c, color); 
    }

    public void drawRect(int x0, int y0, int x1, int y1, double z, int color) 
    { 
        ImageAlgorithms.drawRect(this, x0, y0, x1, y1, z, color); 
    }

    public void drawRect(int x0, int y0, int x1, int y1, double z, int color, int size) 
    { 
        ImageAlgorithms.drawRect(this, x0, y0, x1, y1, z, color, size); 
    }

    public void drawRect(Vector4 p0, Vector4 p1, double z, int color) 
    { 
        drawRect((int) p0.x(), (int) p0.y(), (int) p1.x(), (int) p1.y(), z, color);
    }

    public void drawRect(Vector4 p0, Vector4 p1, double z, int color, int size) 
    { 
        drawRect((int) p0.x(), (int) p0.y(), (int) p1.x(), (int) p1.y(), z, color, size);
    }

    public void fillRect(int x0, int y0, int x1, int y1, double z, int color) 
    { 
        ImageAlgorithms.fillRect(this, x0, y0, x1, y1, z, color); 
    }

    public void fillRect(Vector4 p0, Vector4 p1, double z, int color) 
    { 
        fillRect((int) p0.x(), (int) p0.y(), (int) p1.x(), (int) p1.y(), z, color);
    }

    public void drawImage(Image image, int x, int y) 
    { 
        ImageAlgorithms.drawImage(this, image, x, y); 
    }

    public void drawImage(Image image) 
    { 
        ImageAlgorithms.drawImage(this, image, 0, 0); 
    }

    //
    // BEGIN OF 3D GRAPHICS
    //

    public void drawLine(Vertex p1, Vertex p2, Image texture) 
    { 
        ImageAlgorithms3D.line(this, p1, p2, texture); 
    }

    public void fillTriangle(Triangle t, Image texture) 
    { 
        ImageAlgorithms3D.triangle(this, t, texture); 
    }
}
