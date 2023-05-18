package engine.window.Input;

import java.awt.event.KeyEvent;

/**
 * This class represents a key on the keyboard. <p>
 * 
 * It extends {@link Button} and has theirfor all the functionality of a button. <p>
 * 
 * @author NextLegacy
 * @version 1.0
 */
public class Key extends Button
{
    static final Key NULL_KEY = new Key(-1);

    final int keyEvent;

    Key(final int keyEvent)
    {
        super();
        this.keyEvent = keyEvent;
    }

    public int keyEvent() { return keyEvent; }
    public String name () { return KeyEvent.getKeyText(keyEvent); }
}