package testing.DrawLineTest;

import engine.GameObject;
import engine.Scene;
import engine.Script;
import engine.math.FinalVector;
import engine.math.Vector;

import static engine.utils.MathUtils.*;

import engine.Engine;

public class DrawLineEngineTest 
{
    public static void main(String[] args)
    {
        Engine engine = new Engine(vec(1080, 720), 40, 40, "main");

        engine.setActiveScene(new LineTestScene());

        engine.activate();
    }    

    public static class LineTestScene extends Scene
    {
        @Override
        protected void init() 
        {
            GameObject gameObject = new GameObject();
            addGameObject(gameObject);
            
            gameObject.addScript(new LineTestScript());

            gameObject.scripts().get(0).activate();
            gameObject.activate();

        }
    }

    public static class LineTestScript extends Script
    {
        @Override protected void awake() { setActive(true); }

        private FinalVector a;
        private FinalVector b;

        @Override
        protected void start() 
        {
            a = b = fvec(0, 0);
        }

        @Override
        protected void update() 
        {
            if (left().isDown())
                a = mouse().position();
            if (right().isDown())
                b = mouse().position();    
        }

        @Override
        protected void render() 
        {
            image().line(a.plus(0, 50), b.plus(0, 50), 4, 0xff00ff00);    
            image().bresenham(a, b, 4, 0xff00ff00);    
        }
    }
}
