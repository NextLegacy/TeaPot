package testing.DrawLineTest;

import engine.utils.GraphicsUtils;
import engine.utils.Lambda.Action0;
import engine.utils.color.Color;
import engine.utils.graphics.DrawableImage;
import engine.utils.time.Time;

import static engine.utils.MathUtils.*;

import java.util.Arrays;

import engine.math.Vector;

public class DrawLineTest 
{
    public static void main(String[] args)
    {
        final Vector size = vec(16, 9).times(100);

        DrawableImage image = new DrawableImage(size);

        image.fillColor(0xffffffff);

        final int tests = 100;

        double a = test(tests, () ->  
        {
            for (int i = 0; i < 9999; i++)
                image.bresenham(Vector.random().times(size), Vector.random().times(size), 1, 0xff000000 + ((int) (0xffffff * Math.random())));
        });

        //double a = test(tests, () ->
        //{
        //    for (int i = 0; i < 9999; i++)
        //        image.line(Vector.random().times(size), Vector.random().times(size), 1, 0xff000000 + ((int) (0xffffff * Math.random())));
        //});

        a *= Time.NANOS_TO_SECONDS;

        System.out.println(a);

        main(args);

        image.export("rsc/out.png", "png");
    }

    public static double test(int tests, Action0 action)
    {
        double result = 0;

        for (int i = 0; i < tests; i++)
        {
            result += Time.measureTime(action);
            //System.out.println("Test " + i + " completed.");
        }

        result /= tests;

        return result;
    }
}
