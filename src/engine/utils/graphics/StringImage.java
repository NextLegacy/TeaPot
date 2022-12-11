package engine.utils.graphics;

import static engine.utils.MathUtils.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import engine.math.FinalVector;
import engine.math.Vector;
import engine.utils.ArrayUtils;
import engine.utils.ImageUtils;

//TODO: add way to set colors of String and optimize
public class StringImage extends Image 
{
    public StringImage(String string, Font font)
    {
        this(string.split("\n"), font);
    }

    public StringImage(String[] lines, Font font)
    {
        super(calculateSize(lines, font));
        renderString(this, lines, font);
    }

    private static FinalVector calculateSize(String[] lines, Font font)
    {
        final Vector totalSize = vec();
        
        for (int i = 0; i < lines.length; i++)
        {
            Rectangle2D rect = font.getStringBounds(lines[i], new FontRenderContext(new AffineTransform(), false, false));
            
            final double width  = rect.getWidth ();
            final double height = rect.getHeight();    

            if (width > totalSize.x) totalSize.x = width;

            totalSize.y += height;
        }

        return fvec(totalSize);
    } 

    private static void renderString(Image image, String[] lines, Font font)
    {
        BufferedImage bufferedImage = ImageUtils.createCompatibleBufferedImage(image.size);

        Graphics2D graphics = bufferedImage.createGraphics();
        
        graphics.setFont(font);
        
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON   );
        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, (int) image.size.x, (int) image.size.y);

        graphics.setColor(Color.black);

        FontMetrics fontMetrics = graphics.getFontMetrics(font);

        int y = fontMetrics.getAscent();

        for (int i = 0; i < lines.length; i++)
        {
            int x = ((int)image.size.x - fontMetrics.stringWidth(lines[i])) / 2;
            
            graphics.drawString(lines[i], x, y);
            y += fontMetrics.getHeight();
        }

        ArrayUtils.copy(ImageUtils.getBufferedImageDataArray(bufferedImage), image.colorBuffer, image.pixels);

        graphics.dispose();
    }
}
