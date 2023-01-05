package testing.TestProject;

import static engine.utils.MathUtils.*;

import engine.Engine;

public class Test 
{
    public static final GameScene GAME_SCENE = new GameScene();
    public static final StartScene START_SCENE = new StartScene();

    public static void main(String[] args)
    {
        Engine engine = new Engine(vec(1080, 720), 60, 60, "main");

        engine.setActiveScene(START_SCENE);
        
        engine.activate();
    }    
}
