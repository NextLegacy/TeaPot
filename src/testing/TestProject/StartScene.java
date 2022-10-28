package testing.TestProject;

import engine.Scene;

import static engine.utils.MathUtils.*;

import engine.GameObject;

public class StartScene extends Scene 
{
    @Override
    protected void init() 
    {
        GameObject main = new GameObject("main");
        addGameObject(main);

        getGameObject("main").addScript
        (
            new ButtonScript(window().size(0.5, 0.5).minus(vec(100, 100)), window().size(0.5, 0.5).plus(100, 100), 3, 0xaaafff6d, () -> 
            {
                engine().setActiveScene(Test.GAME_SCENE);
            }
        ));

        getGameObject("main").addScript
        (
            new ButtonScript(window().size(0.3, 0.3).minus(vec(125, 125)), window().size(0.3, 0.3).plus(125, 125), 1, 0x6fafaffd, () -> 
            {
                engine().setActiveScene(Test.GAME_SCENE);
            }
        ));
        getGameObject("main").addScript
        (
            new ButtonScript(window().size(0.45, 0.4).minus(vec(125, 125)), window().size(0.45, 0.4).plus(125, 125), 2, 0x3ff2232d, () -> 
            {
                engine().setActiveScene(Test.GAME_SCENE);
            }
        ));

        getGameObject("main").addScript
        (
            new ButtonScript(window().size(0.6, 0.4).minus(vec(125, 125)), window().size(0.6, 0.4).plus(125, 125), 3, 0x3fffffff, () -> 
            {
                engine().setActiveScene(Test.GAME_SCENE);
            }
        ));

        getGameObject("main").setActive(true);
    }
}
