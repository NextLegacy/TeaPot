package engine.window.Input;

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