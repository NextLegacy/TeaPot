package engine.utils.graphics;

import static engine.utils.MathUtils.*;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.LineMetrics;

import engine.math.Vector;
import engine.math.Vector4;
import engine.utils.StringUtils;

/*
 * The Purpose of this class is to be able to render Strings.
 * The Idea is as follows:
 *     Get size for the String
 *     Create an BufferedImage with the right Size
 *     Draw the String in the center of the BufferedImage
 *     Draw the BufferedImage on to this Image
 * 
 * This way we have a easy way to implement a custom Font renderer later on.
 * 
 * Also we only have to render the String when it changes.
 * I don't know if Graphics::drawString is that efficient to call every Frame.
 * 
 * Only bad side is maybe, if we change the String realy often, lets say every Frame,
 * then this may be slower and this is probably gonna happen often... hmmm
 * 
 * But even if it is slower, then its okay, not gonna think about it
 * 
 * And its super nice for 3D textures Later on, super easy way of doing 3D Strigs
 *     -> 3D Plane with some String as the Texture and because this is an Image
 *        we can directy use this
 */
public class StringImage extends Image 
{
    private static final Canvas canvas;
    //Used for FontMetrics.getLineMetices
    //Reverse engineered and found out that only RenderingHints are getting used (AntiAliasing and FractionalMetrics)
    private static final Graphics2D graphics;

    static {
        canvas = new Canvas();
        graphics = (Graphics2D) canvas.getGraphics(); 
        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING     , RenderingHints.VALUE_ANTIALIAS_ON        );
    }

    private String string;
    private Font font;
    private Vector bounds; //If null, the String is gonna take as much space as possible

    public StringImage(String string, Font font)
    {
        super(new int[0], vec());
        this.string = string;
        this.font = font;
    }

    public String string() { return string; }
    public Font   font  () { return font  ; }
    public Vector bounds() { return bounds; }

    public void string(String string ) { this.string = string     ; }
    public void font  (Font font     ) { this.font   = font       ; }
    public void bounds(Vector4 bounds) { this.bounds = vec(bounds); }

    public void render()
    {
        //could use sun.font.FontDesignMetrics.getMetrics(font) here
        //but using such internal methods is not recommended and may be
        //in later updates Deprecated because internal methods seem to change uncommunicated
        //either way, there is not access to it, to get access
        //we would have to use something like module-info.java
        //but this is not quite working and I am to lazy to look more into it.
        
        FontMetrics metrics = canvas.getFontMetrics(font);
        LineMetrics lineMetrics = metrics.getLineMetrics(string, graphics);

        int width  = metrics.stringWidth(string);
        int height = metrics.getHeight() * ((int)(width / bounds.x()) + StringUtils.count(string, "\n") + 1);
    }
}
