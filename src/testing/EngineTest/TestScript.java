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
        image().fillColor(0xffab2341); // FIXME: This is not working, why?!
        image().fillColor(0xffab2341);

        image().drawLine(vec(0, 0), vec(100, 100), 1, 0xff00ffff); // but this does...

        //System.out.println("TestScript.render()");
    }

    protected void end() 
    {
        System.out.println("TestScript.end()");
    }
}
