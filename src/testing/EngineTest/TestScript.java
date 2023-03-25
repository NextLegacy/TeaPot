package testing.EngineTest;

import engine.Script;
import engine.graphics.DrawableImage;
import engine.graphics.Image;
import engine.utils.random.Random;

public class TestScript extends Script
{
    Random random;
    protected void start() 
    {
        random = new Random();
    }

    protected void update() 
    {
        window().setTitle(engine().toString());
    }

    @Override
    protected void render() 
    {
        image().drawLine(0, 0, 100, 0, 1d, 0xff00ff00);

        //for (int i = 0; i < image().pixels(); i++)
        //    image.drawPixel(i, 0, random.randomInt(0x00ffffff) | 0xff000000);

        //for (int i = 0; i < image().pixels(); i++)
        //    image().drawPixel(i, 1, 0xff000000);
    }

    protected void end() 
    {
        System.out.println("TestScript.end()");
    }
}
