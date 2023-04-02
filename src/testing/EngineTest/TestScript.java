package testing.EngineTest;

import engine.Script;
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

        image().drawImage(image(), 1, 1);
    }

    protected void end() 
    {
        System.out.println("TestScript.end()");
    }
}
