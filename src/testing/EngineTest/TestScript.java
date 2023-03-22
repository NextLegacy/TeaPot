package testing.EngineTest;

import engine.Script;
import engine.utils.random.Random;

public class TestScript extends Script
{
    Random random;
    protected void start() 
    {
        random = new Random();
        System.out.println("TestScript.start()");
    }

    protected void update() 
    {
        System.out.println("TestScript.update()");

        System.out.println(engine());
    }

    @Override
    protected void render() 
    {
        for (int i = 0; i < image().pixels(); i++)
            image().drawPixel(i, 1, 0xff000000 + random.color());
    }

    protected void end() 
    {
        System.out.println("TestScript.end()");
    }
}
