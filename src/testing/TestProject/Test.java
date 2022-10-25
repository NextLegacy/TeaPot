package testing.TestProject;

import engine.Engine;

import static engine.utils.MathUtils.*;

public class Test 
{
    public static final GameScene GAME_SCENE = new GameScene();
    public static final StartScene START_SCENE = new StartScene();

    public static void main(String[] args)
    {
        Engine engine = new Engine(vec(1080, 720), 600, 600, "main");

        engine.setActiveScene(START_SCENE);
        
        engine.activate();
    }    
}
