package testing.Test;

import java.util.ArrayList;

import engine.math.Vector;
import engine.math.Vector4;
import engine.utils.Screen;
import engine.utils.time.Time;
import engine.window.Window;
import engine.window.WindowLayer;

public class Test
{
    public static void main(String[] args)
    {
        //System.out.println(ScreenUtils.SCREENS.length);
        //System.out.println(fvec(1, 2, 0).angle().times(DEGREE));

        //System.out.println(StringUtils.count("aaaaaa", "aa"));
        //test();

        //Window window = new TestWindow(Screen.get(0), new Vector(1080, 720), "main");
//
        //window.start();
//
        //window.setFullScreen(true);
//
        //window.render();
    }

    public static class TestWindow extends Window 
    {
        public TestWindow(Screen screen, Vector4 size, String... layers)
        {
            super(screen, size, layers);
        }

        @Override
        public void renderImage(WindowLayer image) {
            image.drawPixel(20, 20, 0, 0xff00ff00);
        }
    }

    public static void test()
    {
        Vector4[] vectors = new Vector4[100000000];

        for (int i = 0; i < vectors.length; i++)
        {
            vectors[i] = new Vector(Math.random() * 7645.13451, Math.random() * 7645.13451, Math.random() * 7645.13451);
        }

        double test1 = test(vectors);

        Test.vectors = new ArrayList<>();

        for (int i = 0; i < vectors.length; i++)
        {
            vectors[i] = new Vector(Math.random() * 7645.13451, Math.random() * 7645.13451, Math.random() * 7645.13451);
        }

        double test2 = test(vectors);

        System.out.println(test1 + "\n" + test2);
    }

    public static double test(Vector4[] vectors)
    {
        return Time.measureTime(() -> {
            for (Vector4 vector : vectors)
            {
                testVector(vector);
                dumpVector(vector);
            }
        });
    }
    
    public static Vector testVector(Vector4 vector)
    {
        return vector.normalized().plus(vector.inverted().divideBy(vector.normalized()).abs());
    }

    public static ArrayList<Vector4> vectors = new ArrayList<>();
    public static void dumpVector(Vector4 vector)
    {
        if (Math.random() * vector.magnitude() > vector.magnitude() / 2)
            vectors.add(vector);
    }
}
