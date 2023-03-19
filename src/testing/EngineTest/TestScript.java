package testing.EngineTest;

import engine.Script;

import static engine.utils.MathUtils.*;

public class TestScript extends Script
{
    protected void start() 
    {
        //System.out.println("TestScript.start()");
    }

    protected void update() 
    {
        //System.out.println("TestScript.update()");
    }

    @Override
    protected void render() 
    {
        image().fillColor(0xffab2341); // pretty nice color, keep in mind.
        
        image().drawLine(vec(0, 0), vec(100, 100), 1, 0xff00ffff);
    }

    protected void end() 
    {
        System.out.println("TestScript.end()");
    }
}
