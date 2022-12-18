package testing.DrawLineTest;

import engine.utils.GraphicsUtils;
import engine.utils.color.Color;
import engine.utils.graphics.DrawableImage;

import static engine.utils.MathUtils.*;

import java.util.Arrays;

import engine.math.Vector;

public class DrawLineTest 
{
    public static void main(String[] args)
    {
        final Vector size = vec(16, 9).times(10);

        DrawableImage image = new DrawableImage(size);

        image.fillColor(0xffffffff);

        image.line(vec(33, 34), vec(123, 60), 2, 10, 0xff000ff0);
        //image.line(vec(5,  5), size.minus(vec(5, 5)), 2, (v, t) -> 0xff000000 + Color.lerp(new int[] { 0xf35ba4, 0x11f3ff, 0x0a652f, 0xf0a343 }, t));
        //image.line(vec(size.x / 2, 0), vec(size.x / 2, size.y), 2, (v, t) -> 0xff000000 + Color.lerp(new int[] { 0xf35ba4, 0x11f3ff, 0x0a652f, 0xf0a343 }, t));
        //image.line(vec(0, size.y / 2), vec(size.x, size.y / 2), 2, (v, t) -> 0xff000000 + Color.lerp(new int[] { 0xf35ba4, 0x11f3ff, 0x0a652f, 0xf0a343 }, t));
    
        //image.export("rsc/out.png", "png");
    }
}
