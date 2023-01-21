package testing.DrawLineTest;

import engine.GameObject;
import engine.Scene;
import engine.Script;
import engine.graphics.Image;
import engine.math.FinalVector;
import engine.math.Vector;
import engine.threed.Triangle;
import engine.threed.Vertex;
import engine.math.Vector;
import engine.utils.Screen;

import static engine.utils.MathUtils.*;

import engine.Engine;

public class DrawLineEngineTest 
{
    public static void main(String[] args)
    {
        Engine engine = new Engine(Screen.get(0), vec(1080, 720), 60, 60, "main");

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

            gameObject.scripts()[0].activate();
            gameObject.activate();

        }
    }

    public static class LineTestScript extends Script
    {
        @Override protected void start() { setActive(true); a = b = c = fvec(0, 0); }

        private FinalVector a;
        private FinalVector b;
        private FinalVector c;

        @Override
        protected void update() 
        {
            if (left().isDown())
                a = mouse().position();
            if (right().isDown())
                b = mouse().position();  
            if (wheel().isDown())
                c = mouse().position();
        }

        @Override
        protected void render() 
        {
            image().fillTriangle(
                new Triangle
                (
                    new Vertex(a, new Vector(0.25, 0.25, 1), a), 
                    new Vertex(b, new Vector(0.25, 1   , 1), b), 
                    new Vertex(c, new Vector(0.75, 0.25, 1), c)
                ), 
                Image.fromFile("./rsc/out.png")
            );
        }
    }
}
