package testing.DrawLineTest;

import engine.utils.color.Color;
import engine.utils.graphics.DrawableImage;

import static engine.utils.MathUtils.*;

import engine.math.Vector;

public class DrawLineTest 
{
    public static void main(String[] args)
    {
        final Vector size = vec(16, 9).times(10);

        DrawableImage image = new DrawableImage(size);

        image.fillColor(0xffffffff);

        image.line(vec(0,  0), size, 30, (v, t) -> 0xff000000 + Color.lerp(new int[] { 0xf35ba4, 0x11f3ff, 0x0a652f, 0xf0a343 }, t));
    
        image.export("rsc/out.png", "png");
    }
}
