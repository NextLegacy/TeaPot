package engine.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.math.FinalVector;
import engine.math.Vector4;
import engine.utils.ArrayUtils;
import engine.utils.ImageUtils;

/**
 * This Image class does store colors and depths in form of a {@link #colorBuffer} and {@link #zBuffer}.
 * This is only a simple wrapper class whose purpose is to wrap up the attributes of an image and with some 
 * utility functions like {@link #export(String path, String format)}. 
 * 
 * <p>
 * 
 * To use Graphics use {@link DrawableImage}.
 */
public class Image
{
    private final static int ERROR_COLOR = 0xFFFF00FF;

    protected final FinalVector size;

    protected final int pixels; 

    protected final int   [] colorBuffer;
    protected final double[] zBuffer    ;

    public Image(final BufferedImage image) 
    {
        this(ImageUtils.getBufferedImageDataArray(image), new FinalVector(image.getWidth(), image.getHeight())); 
    }

    public Image(final Vector4  size) 
    { 
        this(new int[(int) size.x() * (int) size.y()], size); 
    }
    public Image(final int[] colorBuffer, final Vector4 size) 
    {
        this(colorBuffer, new double[(int) size.x() * (int) size.y()], size);
    }

    public Image(final double[] zBuffer, final Vector4 size) 
    { 
        this(new int[(int) size.x() * (int) size.y()], zBuffer, size);
    }

    public Image(final int[] colorBuffer, final double[] zBuffer, final Vector4 size)
    {
        this.size = size.toFinalVector();

        this.colorBuffer = colorBuffer;
        this.zBuffer     = zBuffer    ;
        
        pixels = width() * height();

        if (pixels != colorBuffer.length ||
            pixels != zBuffer    .length)
        {
            throw new RuntimeException("Image size does not match colorBuffer and/or zBuffer size!");
        }
    }

    /**
     * @param index The index of the pixel.
     * 
     * @return The color of the pixel at the given index.
     */
    public final int getPixel(int index) { return isPixelValid(index) ? colorBuffer[index] : ERROR_COLOR; }

    /**
     * @param u The u coordinate, in range [0, 1].
     * @param v The v coordinate, in range [0, 1].
     * @param w The w coordinate, in range [0, 1].
     * 
     * @return The color of the pixel at the given uvw coordinates.
     */
    public final int getPixel(double u, double v, double w) { int index = getIndex(u, v, w); return isPixelValid(index) ? colorBuffer[index] : ERROR_COLOR; }

    /**
     * @param x The x coordinate, in range [0, width].
     * @param y The y coordinate, in range [0, height].
     * 
     * @return returns the index of the pixel at the given x and y coordinates or -1 if the pixel is out of bounds.
     */
    public final int getIndex(int x, int y) { return isPixelValid(x, y) ? x + y * width() : -1; }

    /**
     * @param u The u coordinate, in range [0, 1].
     * @param v The v coordinate, in range [0, 1].
     * @param w The w coordinate, in range [0, 1].
     * 
     * @return returns the index of the pixel at the given uvw coordinates or -1 if the pixel is out of bounds.
     */
    public final int getIndex(double u, double v, double w) 
    {
        u /= w;
        v /= w;
        
        //This image starts at the top left corner, so we need to flip the v coordinate
        v = 1 - v;

        double x = u * width();
        double y = v * height();

        int index = getIndex((int) x, (int) y);

        return index;
    }

    /**
     * @param x The x coordinate, in range [0, width].
     * @param y The y coordinate, in range [0, height].
     * 
     * @return true if the coordinates are in image bounds
     */
    boolean isPixelValid(final int x, final int y) { return (x < width() && y < height() && x >= 0 && y >= 0); }
    boolean isPixelValid(final int index) { return index >= 0 && index < pixels; }

    public FinalVector size       () { return size          ; }
    public int         width      () { return (int) size.x(); }
    public int         height     () { return (int) size.y(); }
    public int         pixels     () { return pixels        ; }
    public int   []    colorBuffer() { return colorBuffer   ; }
    public double[]    zBuffer    () { return zBuffer       ; }
    
    public BufferedImage toBufferedImage()
    { 
        BufferedImage bufferedImage = ImageUtils.createCompatibleBufferedImage(size());
        
        int[] buffer = ImageUtils.getBufferedImageDataArray(bufferedImage);

        ArrayUtils.copy(colorBuffer, buffer, pixels);

        return bufferedImage;
    }

    public void export(String path, String format)
    {
        try 
        {
            ImageIO.write(toBufferedImage(), format, new File(path));
        }
        catch (IOException e) { e.printStackTrace(); }
    }

    @Override public String toString() { return getClass().getSimpleName() + "(size: " + pixels + ")"; }

    public static Image fromFile(String path)
    {
        File file = new File(path);

        try 
        {
            BufferedImage bufferedImage = ImageIO.read(file);

            Image image = new Image(bufferedImage);

            return image;
        } 
        catch (IOException e) { e.printStackTrace(); }

        return null;
    }
}