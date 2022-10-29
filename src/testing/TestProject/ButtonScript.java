package testing.TestProject;

import java.awt.event.KeyEvent;

import engine.Script;
import engine.math.FinalVector;
import engine.math.Vector;
import engine.math.Vector4;
import engine.utils.Lambda.Action0;
import engine.utils.time.Time;

import static engine.utils.MathUtils.*;

public class ButtonScript extends Script
{
    private final FinalVector a;
    private final FinalVector b;
    private final FinalVector da;
    private final FinalVector db;
    private Vector used_a;
    private Vector used_b;

    private final double z;

    private final int color;

    private final Action0 onClick;

    public ButtonScript(Vector4 a, Vector4 b, double z, int color, Action0 onClick)
    {
        this.a = a.toFinalVector();
        this.b = b.toFinalVector();
        da = a.minus(10).toFinalVector();
        db = b.plus(10).toFinalVector();

        used_a = a.toVector();
        used_b = b.toVector();

        this.z = z;

        this.color = color;

        this.onClick = onClick;
    }

    @Override
    protected void awake() {
        setActive(true);
    }

    boolean hovering = false;

    double t = 0;

    @Override
    protected void update() 
    {
        if (input().key(KeyEvent.VK_ESCAPE).isDown())
            engine().deactivate();

        if (input().left().isClicked() && 
            input().mouse().isInRange(a, b))
            onClick.run();
        
        if (input().mouse().isInRange(a, b))
        {
            t += engine().deltaTime()*4;

            double step = smoothStep(-0.5, 1, t);

            used_a = used_a.lerpTo(da, step);
            used_b = used_b.lerpTo(db, step);
        }
        else {
            t = 0;
            used_a = a.toVector();
            used_b = b.toVector();
        }
    }

    @Override
    protected void render() 
    {
        image().rect(used_a, used_b, z, (x, y, z) -> color);
    }
}
