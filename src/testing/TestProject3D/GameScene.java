package testing.TestProject3D;

import engine.GameObject;
import engine.Scene;

public class GameScene extends Scene
{
    @Override
    protected void init() 
    {
        GameObject gameObject = new GameObject();

        gameObject.addScript(new GameScript());

        addGameObject(gameObject);

        gameObject.activate();
    }   
}
