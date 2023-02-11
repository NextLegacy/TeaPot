package testing.TestProject3D;

import engine.GameObject;
import engine.Scene;
import engine.threed.Camera;

public class GameScene extends Scene
{
    public Camera camera;
    
    @Override
    protected void init() 
    {
        camera = new Camera(90, 16.0 / 9.0, 0.1, 1000);
        
        GameObject gameObject = new GameObject();

        gameObject.addScript(new GameScript());

        addGameObject(gameObject);

        gameObject.activate();
    }   
}
