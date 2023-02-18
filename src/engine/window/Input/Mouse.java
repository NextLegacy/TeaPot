package engine.window.Input;

import engine.math.FinalVector;
import engine.math.Vector;
import engine.math.Vector4;

/**
 * This class represents the mouse. <p>
 * 
 * It has all the functionality of a mouse. <p>
 * 
 * @author NextLegacy
 * @version 1.0
 */
public final class Mouse
{
    FinalVector POSITION_BEFORE;
    FinalVector POSITION_NOW;

    Mouse()
    {
        POSITION_BEFORE = FinalVector.zero;
        POSITION_NOW    = FinalVector.zero;
    }

    public FinalVector position() { return POSITION_NOW; }
    public Vector      speed   () { return POSITION_NOW.minus(POSITION_BEFORE); }


    public boolean isInRange(Vector4 from, Vector4 to)
    {
        return position().isInRange(from, to);
    }

    public boolean isInBounds(Vector4 position, Vector4 size)
    {
        return position().isInBounds(position, size);
    }
}