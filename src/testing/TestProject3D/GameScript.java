package testing.TestProject3D;

import engine.Script;
import engine.math.Vector;

import static engine.utils.MathUtils.*;

public class GameScript extends Script
{
    public GameScript() { activate(); }

    Vector position = vec(0, 0, 0);
    Vector rotation = vec(0, 0, 0);
    Vector scale    = vec(1, 1, 1);

    protected void update()
    {
        
    }

    protected void render()
    {
    
    }
}
