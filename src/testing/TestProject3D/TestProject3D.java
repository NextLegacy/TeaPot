package testing.TestProject3D;

import engine.Engine;
import engine.utils.Screen;

import static engine.utils.MathUtils.*;

public class TestProject3D 
{
    public static void main(String[] args)
    {
        final Engine engine = new Engine(Screen.get(0), vec(1080, 720), 60, 60, "main");

        engine.setActiveScene(new GameScene());

        engine.activate();
    }
}
