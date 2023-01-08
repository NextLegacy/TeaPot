package engine.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import engine.math.FinalVector;
import engine.math.Vector;
import engine.math.Vector4;
import engine.utils.ArrayUtils;
import engine.utils.ImageUtils;
import engine.utils.MathUtils;
import engine.utils.Lambda.Action1;
import engine.utils.Lambda.Func3;
import engine.utils.Lambda.Func4;
import engine.utils.color.Color;

/**
 * This Image class does store colors and depths in form of a {@link #colorBuffer} and {@link #zBuffer}.
 * This is only a simple wrapper class whose purpose is to wrap up the attributes of an image and with some 
 * utility functions like {@link #export(String path, String format)}. 
 * 
 * <p>
 * 
 * To use Graphics use {@link DrawableImage}.
 */
@SuppressWarnings("unused")
public class Image
{
    protected final FinalVector size;

    protected final int pixels; 

    protected final int   [] colorBuffer;
    protected final double[] zBuffer    ;

    public Image(final BufferedImage image)
    {
        this(ImageUtils.getBufferedImageDataArray(image), new FinalVector(image.getWidth(), image.getHeight()));
    }

    public Image(final Vector4 size)
    {
        this(new int[size.int_x() * size.int_y()], size);
    }

    public Image(final int[] colorBuffer, final Vector4 size)
    {
        this(colorBuffer, new double[size.int_x() * size.int_y()], size);
    }

    public Image(final double[] zBuffer, final Vector4 size)
    {
        this(new int[size.int_x() * size.int_y()], zBuffer, size);
    }

    public Image(final int[] colorBuffer, final double[] zBuffer, final Vector4 size)
    {
        this.size = size.toFinalVector();

        this.pixels = width() * height();

        this.colorBuffer = colorBuffer;
        this.zBuffer     = zBuffer    ;

        if (pixels != colorBuffer.length ||
            pixels != zBuffer    .length)
        {
            throw new ImageAttributesUnequalSizeException(this);
        }
    }

    public final int getIndex(int x, int y) { return x > width() || y > height() || x < 0 || y < 0 ? -1 : x + y * width(); }

    public FinalVector size       () { return size        ; }

    public int         width      () { return size.int_x(); }
    public int         height     () { return size.int_y(); }

    public int         pixels     () { return pixels      ; }

    public int   []    colorBuffer() { return colorBuffer ; }
    public double[]    zBuffer    () { return zBuffer     ; }
    
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

    public static Image fromFile(String path)
    {
        File file = new File(path);

        try 
        {
            BufferedImage bufferedImage = ImageIO.read(file);

            Image image = Image.fromBufferedImage(bufferedImage);

            return image;
        } 
        catch (IOException e) { e.printStackTrace(); }

        return null;
    }

    public static Image fromBufferedImage(final BufferedImage bufferedImage)
    {
        final BufferedImage compatibleBufferedImage = ImageUtils.toCompatibleBufferedImage(bufferedImage);

        final Vector4 size = new FinalVector(compatibleBufferedImage.getWidth(), compatibleBufferedImage.getHeight());

        final int[] colorBuffer = ImageUtils.getBufferedImageDataArray(compatibleBufferedImage);

        return new Image(colorBuffer, size);
    }

    public static Image fromUsingGraphics(Vector4 size, Action1<Graphics2D> action)
    {
        BufferedImage bufferedImage = ImageUtils.createCompatibleBufferedImage(size);

        Graphics2D graphics = bufferedImage.createGraphics();
    
        graphics.setColor(new java.awt.Color(0, 0, 0, 0));
        graphics.fillRect(0, 0, size.int_x(), size.int_y());

        action.run(graphics);

        graphics.dispose();

        return Image.fromBufferedImage(bufferedImage);
    }

    @Override
    public String toString()
    {
        return "Image(size: " + pixels + ")";
    }

    private static final class ImageAttributesUnequalSizeException extends RuntimeException
    {
        ImageAttributesUnequalSizeException(final Image image)
        {
            super("The size or the bufferlengths in the Image do not match!");
        }
    }
}