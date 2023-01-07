package engine.graphics;

import static engine.utils.MathUtils.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.math.FinalVector;
import engine.math.Vector;
import engine.math.Vector4;
import engine.utils.ArrayUtils;
import engine.utils.GraphicsUtils;
import engine.utils.ImageUtils;
import engine.utils.Lambda.Action1;
import engine.utils.color.Color;

public class DrawableImage extends Image
{
    public static final int    CLEAR_COLOR = 0xff000000;
    public static final double CLEAR_Z     = -1        ;

    public DrawableImage(final int[] colorBuffer, final double[] zBuffer, final Vector4 size)
    {
        super(colorBuffer, zBuffer, size);

        clear();
    }

    public void fillColor(final int    color) { ArrayUtils.fill(colorBuffer, color); }
    public void fillZ    (final double z    ) { ArrayUtils.fill(zBuffer    , z    ); }

    public void fill(final double z, final int color) { fillZ(z); fillColor(color); }

    public void clear() { fill(CLEAR_Z, CLEAR_COLOR); }

    private boolean isPixelValid(final int index) { return index >= 0 && index < pixels; }

    private void overridePixel(final int index, final double z, final int argb)
    { 
        zBuffer    [index] = z   ; 
        colorBuffer[index] = argb; 
    }

    public void setPixel(int index, double z, int argb)
    {
        if (isPixelValid(index))
            overridePixel(index, z, argb);
    }

    public void drawPixel(int x, int y, double z, int argb) { drawPixel(getIndex(x, y), z, argb); }
    public void drawPixel(int index, double z, int argb)
    {
        if (!isPixelValid(index)) return; 

        final boolean t = zBuffer[index] > z; 

        //if current z is higher and current color is not transparent, do not draw the Pixel
        //even if current z is higher, if the current color is transparent, the Pixel must be drawn 
        if (t && colorBuffer[index] >>> 24 == 0xff) return; 

        overridePixel(
            index, 
            z,
            t ? Color.mix(argb, colorBuffer[index]) :
                Color.mix(colorBuffer[index], argb)  
        );
    }

    public void line(final int x0, final int y0, final int x1, final int y1, final double z, final int color) 
    { 
        ImageAlgorithms.line(this, x0, y0, x1, y1, z, color); 
    }

    public void rect(final int x0, final int y0, final int x1, final int y1, final double z, final int color)
    {
        ImageAlgorithms.rect(this, x0, y0, x1, y1, z, color);
    }

    public void drawImage(final Image image, final int x, final int y)
    {
        ImageAlgorithms.drawImage(this, image, x, y);
    }

    public void line(final Vector4 a, final Vector4 b, final int z, final int size, final int color)
    {
        line((int) a.x(), (int) a.y(), (int) b.x(), (int) b.y(), z, size, color);
    }

    public void line(int x0, int y0, int x1, int y1, double z, int size, int color) 
    {
        if (size <= 0) return;

        if (size == 1) { line(x0, y0, x1, y1, z, color); return; }

        Vector a = vec(x0, y0);
        Vector b = vec(x1, y1);

        final double dx = x1 - x0;
        final double dy = y1 - y0;

        if (dx == 0 && dy == 0) { drawPixel(x0, y0, z, color); return; }

        final int size0 = floor(size / 2d);
        final int size1 = round(size / 2d);

        if (dx == 0) 
        {
            rect(x0 - size0, y0, x1 + size1, y1, z, color);

            return; 
        }

        if (dy == 0) 
        { 
            rect(x0, y0 - size0, x1, y1 + size1, z, color);

            return; 
        }

        double theta = Math.atan(-dx/dy);
        
        Vector v = Vector.fromAngle(theta);
        
        Vector p0 = vec(v).times(size0).add(a);
        Vector p1 = vec(v).times(size0).add(b);
        Vector p2 = vec(v).times(-size1).add(a);
        Vector p3 = vec(v).times(-size1).add(b);

        drawPixel((int) p0.x, (int) p0.y, 1, 0xffff00ff);
        drawPixel((int) p1.x, (int) p1.y, 1, 0xffffff00);
        drawPixel((int) p2.x, (int) p2.y, 1, 0xff0000ff);
        drawPixel((int) p3.x, (int) p3.y, 1, 0xffff0000);

        //TODO: we have now 4 points, these points form a rectangle
        //this rectangle must we now split into two triangles 
        //afterwards we rende those triangles via simple scanline algorithm

        //TODO: also implement the scanline algorithm
    }

    public void line(Vector4 a, Vector4 b, double z, int color)
    {
        line(a.int_x(), a.int_y(), b.int_x(), b.int_y(), z, color);
    }

    public void rect(Vector4 a, Vector4 b, double z, int color)
    {
        a = a.toVector().clamp(FinalVector.zero, size());
        b = b.toVector().clamp(FinalVector.zero, size());

        rect(a.int_x(), a.int_y(), b.int_x(), b.int_y(), z, color);
    }

    public void drawImage(final Image image) { drawImage(image, 0, 0);}

    //FIXME: Does not work with different sized Images
    //Goal of this implementation is fast image rendering, but disregardes z-buffering and transparancy
    //but should be super fast
    //TODO: Implmenent already, should not be too hard
    //public void pasteImage(final Image image) { ImageUtils.imageCopy(image, this); }
    //public void pasteImage(final Image image, int x0, int y0, int x1, int y1) { ImageUtils.imageCopy(image, this, x0, y0, x1, y1); }

    public static DrawableImage fromFile(String path)
    {
        File file = new File(path);

        try 
        {
            BufferedImage bufferedImage = ImageIO.read(file);

            DrawableImage image = new DrawableImage(bufferedImage);

            return image;
        } catch (IOException e) { e.printStackTrace(); }

        return null;
    }
}
