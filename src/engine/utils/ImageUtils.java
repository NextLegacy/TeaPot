package engine.utils;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import engine.math.Vector4;
import engine.utils.graphics.Image;

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
}
