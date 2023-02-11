package testing.TestProject3D;

import engine.Script;
import engine.math.Transform;

import static engine.utils.MathUtils.*;

public class GameScript extends Script
{
    GameScene scene;
    Transform transform;

    public GameScript() { activate(); }

    @Override
    protected void start() 
    {
        scene = scene();

        transform = new Transform();    
    }

    protected void update()
    {
        
    }

    protected void render()
    {
    
    }
}
