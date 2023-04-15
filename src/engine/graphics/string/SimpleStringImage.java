package engine.graphics.string;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import engine.graphics.Image;
import engine.math.FinalVector;
import engine.math.Vector;

import static engine.utils.MathUtils.*;

/**
 * A simple string image that can be rendered to the screen.
 * 
 * @author NextLegacy
 * @version 1.0
 */
public class SimpleStringImage extends Image 
{
    private String string;
    private Font font;
    private int color;
    private boolean bold;
    
    private boolean italic;
    private boolean underline;
    private boolean strikethrough;

    public SimpleStringImage(String string, Font font, int color)
    {
        this(string, font, color, false, false, false, false);

        if (string.contains("\n"))
        {
            throw new IllegalArgumentException("String must not contain newlines, use engine.graphics.StringImage instead.");
        }
    }

    public SimpleStringImage(String string, Font font, int color, boolean bold, boolean italic, boolean underline, boolean strikethrough)
    {
        super(calculateSize(string, font));

        this.string = string;
        this.font = font;
        this.color = color;
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
        this.strikethrough = strikethrough;
    }

    public String  string       () { return string       ; }
    public Font    font         () { return font         ; }
    public int     color        () { return color        ; }
    public boolean bold         () { return bold         ; }
    public boolean italic       () { return italic       ; }
    public boolean underline    () { return underline    ; }
    public boolean strikethrough() { return strikethrough; }

    public void setString       (String  string       ) { this.string        = string            ; }
    public void setFont         (Font    font         ) { this.font          = font              ; }
    public void setColor        (int     color        ) { this.color         = color             ; }
    public void setBold         (boolean bold         ) { this.bold          = bold              ; }
    public void setItalic       (boolean italic       ) { this.italic        = italic            ; }
    public void setUnderline    (boolean underline    ) { this.underline     = underline         ; }
    public void setStrikethrough(boolean strikethrough) { this.strikethrough = strikethrough     ; }

    public void render()
    {
        // TODO: extract code from StringImage into here
    }

    private static FinalVector calculateSize(String string, Font font)
    {
        final String[] lines = string.split("\n");
        
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
}
