package engine.window.Input;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;

import engine.math.FinalVector;
import engine.utils.MathUtils;

public class InputListener extends InputAdapter
{
    private final Keyboard KEYBOARD;
    
    private final Mouse MOUSE;
    private final Wheel WHEEL;
    private final Button[] BUTTONS;
    
    private boolean isWindowFocused;
    private boolean isWindowActive;

    private int windowState;
    
    public InputListener()
    {
        KEYBOARD = new Keyboard();

        MOUSE = new Mouse();
        WHEEL = new Wheel();
        BUTTONS = new Button[] { new Button(), WHEEL, new Button() };

        isWindowFocused = false;
        isWindowActive  = false;

        windowState = Frame.NORMAL;
    }

    public void update()
    {
        for (int i = 0; i < BUTTONS.length; i++)
            BUTTONS[i].update();

        WHEEL.mouseWheelNow = WHEEL.mouseWheelBefore = 0;
    }

    public Key key(int keyEvent) { return KEYBOARD.get(keyEvent); }

    public Mouse  mouse() { return MOUSE     ; }
    public Button left () { return BUTTONS[0]; }
    public Button right() { return BUTTONS[2]; }
    public Wheel  wheel() { return WHEEL     ; }

    public int windowState() { return windowState; }

    public boolean isActive () { return isWindowActive ; }
    public boolean isFocused() { return isWindowFocused; }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        Key key = KEYBOARD.get(e.getKeyCode());

        key.press();
    }


    @Override
    public void keyReleased(KeyEvent e) 
    {
        Key key = KEYBOARD.get(e.getKeyCode());
        
        key.release();
    }

    private Button tryGetButton(MouseEvent e)
    {
        final int buttonIndex = e.getButton() - 1;

        if (MathUtils.IsNotInRange(buttonIndex, 0, 2)) 
            throw new RuntimeException("Unknown MouseButton: " + e.getButton());
        
        return BUTTONS[buttonIndex];
    }

    @Override
    public void mousePressed(MouseEvent e) 
    { 
        tryGetButton(e).press();

        //button.isDown = true;
        //button.timeOnStart = Time.nanos();
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    { 
        tryGetButton(e).release();

        //button.isDown = false;
        //button.timeOnStart = 0.0d;
    }

    //@Override
    //public void mouseClicked(MouseEvent e) 
    //{
    //    final int buttonIndex = e.getButton() - 1;
    //    
    //    if (MathUtils.IsNotInRange(buttonIndex, 0, 2)) return;
//
    //    Button button = BUTTONS[buttonIndex];
//
    //    button.press();
//
    //    //button.isClicked = true;
    //}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) 
    {
        WHEEL.mouseWheelBefore = WHEEL.mouseWheelNow;
        // WheelRotation -> int 'full' clicks only | PreciseWheelRotation -> double 'fraction + full' click
        WHEEL.mouseWheelNow = e.getPreciseWheelRotation();
    }

    @Override
    public void mouseMoved(MouseEvent e) 
    {
        MOUSE.POSITION_BEFORE = MOUSE.POSITION_NOW;
        MOUSE.POSITION_NOW    = new FinalVector(e.getX(), e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) 
    {
        MOUSE.POSITION_BEFORE = MOUSE.POSITION_NOW;
        MOUSE.POSITION_NOW    = new FinalVector(e.getX(), e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) 
    {
        MOUSE.POSITION_BEFORE = FinalVector.zero;
        MOUSE.POSITION_NOW    = FinalVector.zero;
    }

    @Override
    public void windowActivated(WindowEvent e) 
    {
        isWindowActive = true;
    }

    @Override
    public void windowClosing(WindowEvent e) 
    {
        isWindowActive = false;
    }

    @Override
    public void windowGainedFocus(WindowEvent e) 
    {
        isWindowFocused = true;
    }

    @Override
    public void windowLostFocus(WindowEvent e) 
    {
        isWindowFocused = false;
    }

    @Override
    public void windowStateChanged(WindowEvent e) 
    {
        windowState = e.getNewState();
    }

    public InputListener bindToFrame(Frame frame)
    {
        frame.addKeyListener        (this);
        frame.addMouseListener      (this);
        frame.addMouseMotionListener(this);
        frame.addMouseWheelListener (this);
        frame.addWindowListener     (this);
        frame.addWindowStateListener(this);
        frame.addWindowFocusListener(this);

        return this;
    }
}
