package engine.utils.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.math.FinalVector;
import engine.math.Vector4;
import engine.utils.ArrayUtils;
import engine.utils.GraphicsUtils;
import engine.utils.ImageUtils;
import engine.utils.MathUtils;
import engine.utils.Lambda.Action0;
import engine.utils.Lambda.Action1;
import engine.utils.Lambda.Func1;
import engine.utils.Lambda.Func2;
import engine.utils.Lambda.Func3;
import engine.utils.Lambda.Func4;
import engine.utils.color.Color;

import static engine.utils.MathUtils.*;

public class DrawableImage extends Image
{
    public static final int CLEAR_COLOR = 0xff000000;
    public static final double CLEAR_Z  = -1;

    public DrawableImage(final int[] data, Vector4 size)
    {
        super(data, size);
        clear();
    }

    public DrawableImage(final BufferedImage bufferedImage)
    {
        this(ImageUtils.getBufferedImageDataArray(bufferedImage), new FinalVector(bufferedImage.getWidth(), bufferedImage.getHeight()));
    }

    public DrawableImage(final Vector4 size)
    {
        this(ImageUtils.createCompatibleBufferedImage(size.int_x(), size.int_y()));
    }

    public DrawableImage(final Image image)
    {
        this(image.colorBuffer(), image.size());
    }

    public void fillColor(final int    color) { ArrayUtils.fill(colorBuffer, color); }
    public void fillZ    (final double z    ) { ArrayUtils.fill(zBuffer    , z    ); }

    public void fill(final double z, final int color) { fillZ(z); fillColor(color); }

    public void clear() { fill(DrawableImage.CLEAR_Z, DrawableImage.CLEAR_COLOR); }

    //private boolean isPixelValid(final int x, final int y) { return isPixelValid(getIndex(x, y)); }
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

    public void line(Vector4 a, Vector4 b, int z, int size, int color)
    {
        line(a.int_x(), a.int_y(), b.int_x(), b.int_y(), z, size, color);
    }

    public void line(int x0, int y0, int x1, int y1, double z, int size, int color)
    {
        if (size <= 0) return;

        if (size == 1) { line(x0, y0, x1, y1, z, color); return; }

        final double dx = x1 - x0;
        final double dy = y1 - y0;

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

        double vx = (double) Math.cos(theta);
        double vy = (double) Math.sin(theta);

        int x2 = (int) (vx * size0) + x0;
        int y2 = (int) (vy * size0) + y0;

        int x3 = (int) (vx * size0) + x1;
        int y3 = (int) (vy * size0) + y1;

        int x4 = (int) (-vx * size1) + x0;
        int y4 = (int) (-vy * size1) + y0;

        int x5 = (int) (-vx * size1) + x1;
        int y5 = (int) (-vy * size1) + y1;

        line(x0, y0, x1, y1, z, color);

        line(x2, y2, x3, y3, z, color);
        line(x4, y4, x5, y5, z, color);
    }

    public void line(int x0, int y0, int x1, int y1, double z, int color)
    {
        x0 = (int) MathUtils.clamp(x0, 0, width());
        y0 = (int) MathUtils.clamp(y0, 0, height() - 1);
        
        x1 = (int) MathUtils.clamp(x1, 0, width());
        y1 = (int) MathUtils.clamp(y1, 0, height() - 1);

        int[][] pixels = GraphicsUtils.bresenham(x0, y0, x1, y1);

        for (int i = 0; i < pixels.length; i++)
            drawPixel(pixels[i][0], pixels[i][1], z, color);
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

    public void rect(int x0, int y0, int x1, int y1, double z, int color)
    {
        int x = x0;
        int y = y0;

        //w and h are just the width and height of the Rectangle, width and height are the clipped version of this
        int w = x1 - x0;
        int h = y1 - y0;        

        //Not a single Pixel is inside the Bounds of this
        if (x >= width() || y >= height() || x <= -w || y <= -h) 
            return;

        //width and height, that lies inside the Bounds of this
        int width  = (x + w > width () ? width () - x : w) + (x < 0 ? x : 0);
        int height = (y + h > height() ? height() - y : h) + (y < 0 ? y : 0);

        //Start Index
        int thisStartIndex  = (x < 0 ?  0 : x) + (y < 0 ?  0 : y) * width();

        //Needed in the While-Loop, to avoid usage of repetitive multiplication (y * any_width = any_height). 
        //It may be faster, for that reason its here.
        int thisHeight  = 0;

        //Last Index in current line
        int thisMaxWidthIndex = thisStartIndex + width;
        //Last Index that has to be filled
        int thisMaxIndex = thisStartIndex + height * width();

        //Index of this that is gonna be drawn on
        int thisIndex = thisStartIndex;

        while(true)
        {
            //TODO: find out x y z for color.get(...) | 
            //TODO: Even better, find a better solution to use instead of color.get(...)

            //draw Pixel
            drawPixel(thisIndex, z, color);
            
            thisIndex++;

            if (thisIndex == thisMaxWidthIndex) 
            {
                //Increase Height by one (width of Rectangle)
                thisHeight += width(); 

                //Reset Indices to most left point shifted down by current Height
                thisIndex = thisStartIndex  + thisHeight;

                //Set new last point in line
                thisMaxWidthIndex = thisIndex + width;

                //If reached last Index, step out of loop
                if (thisIndex == thisMaxIndex) break;
            }
        }
    }

    public void drawImage(Image image, int x, int y)
    {
        //Not a single Pixel is inside the Bounds of this
        if (x >= width() || y >= height() || x <= -image.width() || y <= -image.height()) 
            return;

        //width and height, that lies inside the Bounds of this
        int width  = (x + image.width()  > width()  ? width()  - x : image.width() ) + (x < 0 ? x : 0);
        int height = (y + image.height() > height() ? height() - y : image.height()) + (y < 0 ? y : 0);

        //Start Index, for image and this
        int imageStartIndex = (x < 0 ? -x : 0) + (y < 0 ? -y : 0) * image.width();
        int thisStartIndex  = (x < 0 ?  0 : x) + (y < 0 ?  0 : y) * width();

        //Needed in the While-Loop, to avoid usage of repetitive multiplication (y * any_width = any_height (whereas any means image or this)). 
        //It may be faster, for that reason its here.
        int imageHeight = 0;
        int thisHeight  = 0;

        //Last Index in current line
        int imageMaxWidthIndex = imageStartIndex + width;
        //Last Index that has to be filled
        int imageMaxIndex = imageStartIndex + height * image.width();
        
        //Index of Image that is gonna be used to get Color and ZValue
        int imageIndex = imageStartIndex;
        //Index of this that is gonna be drawn on
        int thisIndex  = thisStartIndex;

        while(true)
        {
            //draw Pixel
            drawPixel(thisIndex, image.zBuffer[imageIndex], image.colorBuffer[imageIndex]);

            //Increase Index (going one step right)
            thisIndex++;
            imageIndex++;

            //If reached last Point in Line, execute following
            if (imageIndex == imageMaxWidthIndex) 
            {
                //Increase Height by one (width of corresponding Image)
                thisHeight  += width(); 
                imageHeight += image.width();
                
                //Reset Indices to most left point shifted down by current Height
                thisIndex  = thisStartIndex  + thisHeight;
                imageIndex = imageStartIndex + imageHeight;
                
                //Set new last point in line
                imageMaxWidthIndex = imageIndex + width;

                //If reached last Index, step out of loop
                if (imageIndex == imageMaxIndex) break;
            }
        }
    }

    public void drawImage(final Image image) { drawImage(image, 0, 0);}

    //FIXME: Does not work with different sized Images
    public void pasteImage(final Image image) { ImageUtils.imageCopy(image, this); }
    public void pasteImage(final Image image, int x0, int y0, int x1, int y1) { ImageUtils.imageCopy(image, this, x0, y0, x1, y1); }

    public static DrawableImage fromUsingGraphics(Vector4 size, Action1<Graphics2D> action)
    {
        BufferedImage bufferedImage = ImageUtils.createCompatibleBufferedImage(size);

        Graphics2D graphics = bufferedImage.createGraphics();
    
        graphics.setColor(new java.awt.Color(0, 0, 0, 0));
        graphics.fillRect(0, 0, size.int_x(), size.int_y());

        action.run(graphics);

        graphics.dispose();

        return new DrawableImage(bufferedImage);
    }

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
