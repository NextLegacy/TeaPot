package engine.window.Input;

/**
 * This class represents a mouse wheel. <p>
 * 
 * It extends {@link Button} and has theirfor all the functionality of a button. <p>
 * 
 * @author NextLegacy
 * @version 1.0
 */
public class Wheel extends Button
{
    double mouseWheelBefore;
    double mouseWheelNow;

    Wheel()
    {
        mouseWheelBefore = 0d;
        mouseWheelNow = 0d;
    }

    public double direction() { return speed() == 0 ? 0 : speed() > 0d ? 1d : -1d; }
    public double speed    () { return mouseWheelNow - mouseWheelBefore;           }
}