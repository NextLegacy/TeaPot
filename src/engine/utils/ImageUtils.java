package engine.utils;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import engine.graphics.Image;
import engine.math.FinalVector;
import engine.math.Vector4;

public final class ImageUtils 
{
    private ImageUtils() { }

    private final static GraphicsConfiguration gfx_config = GraphicsEnvironment
        .getLocalGraphicsEnvironment().getDefaultScreenDevice()
        .getDefaultConfiguration();

    public static int[] getBufferedImageDataArray(final BufferedImage bufferedImage)
    {
        return ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
    }

    //bug: None of this methods work for different sized Images
    public static void bufferedImageCopy(BufferedImage source, BufferedImage destination)
    {
        ArrayUtils.copy(getBufferedImageDataArray(source), getBufferedImageDataArray(destination), destination.getWidth() * destination.getHeight());
    }

    public static void bufferedImageCopy(BufferedImage source, BufferedImage destination, int x0, int y0, int x1, int y1, int width, int height)
    {
        //ArrayUtils.copy(getBufferedImageDataArray(source), getBufferedImageDataArray(destination), x0, y0, x1, y1, width, height);
    }

    public static void imageCopy(Image source, Image destination, int x0, int y0, int x1, int y1)
    {
        //ArrayUtils.copy(source.colorBuffer(), destination.colorBuffer(), x0, y0, x1, y1, destination.width(), destination.height());
        //ArrayUtils.copy(source.zBuffer    (), destination.zBuffer    (), x0, y0, x1, y1, destination.width(), destination.height());
    }

    public static void imageCopy(Image source, Image destination)
    {
        ArrayUtils.copy(source.colorBuffer(), destination.colorBuffer(), destination.pixels());
        ArrayUtils.copy(source.zBuffer    (), destination.zBuffer    (), destination.pixels());
    }

    //https://stackoverflow.com/q/29067108/12821391
    public static BufferedImage createCompatibleBufferedImage(int width, int height)
    {
        return gfx_config.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
    }

    public static BufferedImage createCompatibleBufferedImage(Vector4 size)
    {
        return createCompatibleBufferedImage(size.int_x(), size.int_y());
    }

    public static BufferedImage toCompatibleBufferedImage(BufferedImage image) 
    {
        if (image.getColorModel().equals(gfx_config.getColorModel()))
            return image;

        BufferedImage new_image = createCompatibleBufferedImage(image.getWidth(), image.getHeight());

        new_image.getGraphics().drawImage(image, 0, 0, null);

        return new_image;
    }

    public static Image scaled(final Image image, final double factor)
    {
        return scaled(image, image.size().times(factor));
    }

    public static Image scaled(final Image image, Vector4 size)
    {
        if (size.equals(image.size())) return image;
        
        if (size.int_x() <= 0 || size.int_y() <= 0) size = FinalVector.one;

        BufferedImage result = ImageUtils.createCompatibleBufferedImage(size);

        Graphics2D graphics = result.createGraphics();

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        graphics.drawImage(image.toBufferedImage(), 0, 0, size.int_x(), size.int_y(), null);

        graphics.dispose();

        return Image.fromBufferedImage(result);
    }
}
