package testing.TestProject;

import static engine.utils.MathUtils.*;

import engine.GameObject;
import engine.Scene;

public class GameScene extends Scene
{
    @Override
    protected void init() 
    {
        GameObject main = new GameObject("main");
        addGameObject(main);

        getGameObject("main").addScript
        (
            new ButtonScript(window().size(0.5, 0.5).minus(vec(100, 100)), window().size(0.5, 0.5).plus(100, 100), 1, 0xaaaf3fdd, () -> 
            {
                engine().setActiveScene(new StartScene());
            }
        ));

        getGameObject("main").activate();
    }
}
