package testing.EngineTest;

import engine.Engine;
import engine.GameObject;
import engine.Scene;
import engine.utils.Screen;

import static engine.utils.MathUtils.*;

public class EngineTest extends Scene
{
    protected void init() 
    {
        System.out.println("TestScene.init()");

        GameObject gameObject = new GameObject();

        TestScript script = new TestScript();

        addGameObject(gameObject);
    
        gameObject.addScript(script);

        gameObject.activate();
    }

    protected void end() 
    {
        System.out.println("TestScene.end()");
    }

    public static void main(String[] args) 
    {
        Engine engine = new Engine(Screen.get(0), vec(1080, 720), 60, 60, "main");

        engine.setActiveScene(new EngineTest());

        engine.activate();
    }
}
