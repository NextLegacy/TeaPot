package testing.TestProject;

import static engine.utils.MathUtils.*;

import engine.Engine;
import engine.utils.Screen;

public class Test 
{
    public static void main(String[] args)
    {
        Engine engine = new Engine(Screen.get(0), vec(1080, 720), 60, 60, "main");

        engine.setActiveScene(new StartScene());
        
        engine.activate();
    }    
}
