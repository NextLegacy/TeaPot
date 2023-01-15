package engine.window.Input;

import java.awt.Frame;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;

import engine.math.FinalVector;
import engine.utils.MathUtils;

public class Input
{
    private final Keyboard KEYBOARD;
    
    private final Mouse MOUSE;
    private final Wheel WHEEL;
    private final Button[] BUTTONS;
    
    private boolean isWindowFocused;
    private boolean isWindowActive;

    private int windowState;

    private InputListener listener;
    
    public Input()
    {
        listener = new InputListener();

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
        KEYBOARD.update();

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

    private Button tryGetButton(MouseEvent e)
    {
        final int buttonIndex = e.getButton() - 1;

        if (MathUtils.IsNotInRange(buttonIndex, 0, 2)) 
        {
            System.out.println("ERROR: unknown mouse button: " + e.getButton());
            return Button.NULL_BUTTON;
        }
        
        return BUTTONS[buttonIndex];
    }

    private Button tryGetKey(KeyEvent e)
    {
        return KEYBOARD.get(e.getKeyCode());
    }

    public Input bindToFrame(Window frame)
    {
        frame.addKeyListener        (listener);
        frame.addMouseListener      (listener);
        frame.addMouseMotionListener(listener);
        frame.addMouseWheelListener (listener);
        frame.addWindowListener     (listener);
        frame.addWindowStateListener(listener);
        frame.addWindowFocusListener(listener);

        return this;
    }

    private final class InputListener extends InputAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) 
        {
            tryGetKey(e).press();
        }
    
        @Override
        public void keyReleased(KeyEvent e) 
        {
            tryGetKey(e).release();
        }

        @Override
        public void mousePressed(MouseEvent e) 
        { 
            tryGetButton(e).press();
        }
    
        @Override
        public void mouseReleased(MouseEvent e) 
        { 
            tryGetButton(e).release();
        }
    
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
    }
}
