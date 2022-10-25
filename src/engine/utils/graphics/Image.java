package engine.utils.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.math.FinalVector;
import engine.math.Vector;
import engine.math.Vector4;
import engine.utils.ArrayUtils;
import engine.utils.ImageUtils;
import engine.utils.MathUtils;
import engine.utils.Lambda.Func3;
import engine.utils.Lambda.Func4;
import engine.utils.color.Color;

@SuppressWarnings("unused")
public class Image
{
    protected final FinalVector size;

    //protected final BufferedImage bufferedImage;

    protected final int pixels; 

    protected final int   [] colorBuffer;
    protected final double[] zBuffer    ;

    public Image(final int[] data, final Vector4 size)
    {
        this.size = size.toFinalVector();

        pixels = width() * height();

        colorBuffer = data;

        zBuffer = new double[pixels];
    }

    public Image(BufferedImage bufferedImage)
    {
        this(ImageUtils.getBufferedImageDataArray(ImageUtils.toCompatibleBufferedImage(bufferedImage)), new FinalVector(bufferedImage.getWidth(), bufferedImage.getHeight()));
    }
    
    public final int getIndex(int x, int y) { return x + y * width(); }

    public int           width        () { return size.int_x() ; }
    public int           height       () { return size.int_y() ; }
    public FinalVector   size         () { return size         ; }
    public int           pixels       () { return pixels       ; }
    public int[]         colorBuffer  () { return colorBuffer  ; }
    public double[]      zBuffer      () { return zBuffer      ; }
    
    public BufferedImage toBufferedImage()
    { 
        BufferedImage bufferedImage = ImageUtils.createCompatibleBufferedImage(size());
        
        int[] buffer = ImageUtils.getBufferedImageDataArray(bufferedImage);

        ArrayUtils.copy(colorBuffer, buffer, pixels);

        return bufferedImage;
    }

    public Image scaled(final double factor)
    {
        return scaled(size().times(factor));
    }

    public Image scaled(Vector4 size)
    {
        if (size.equals(size())) return this;
        
        if (size.int_x() <= 0 || size.int_y() <= 0) size = FinalVector.one;

        BufferedImage result = ImageUtils.createCompatibleBufferedImage(size);

        Graphics2D graphics = result.createGraphics();

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        graphics.drawImage(toBufferedImage(), 0, 0, size.int_x(), size.int_y(), null);

        graphics.dispose();
        
        return new Image(result);
    }

    public static Image fromFile(String path)
    {
        File file = new File(path);

        try 
        {
            BufferedImage bufferedImage = ImageIO.read(file);

            Image image = new Image(bufferedImage);

            return image;
        } catch (IOException e) { e.printStackTrace(); }

        return null;
    }
}