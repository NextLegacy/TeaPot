package engine.window.Input;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * This class represents the keyboard. <p>
 * 
 * It contains a {@link HashMap} of all keys on the keyboard. <p>
 * 
 * @author NextLegacy
 * @version 1.0
 */
public final class Keyboard
{
    private final HashMap<Integer, Key> KEY_MAP;

    Keyboard()
    {
        KEY_MAP = new HashMap<>();

        initializeKeyMap();
    }

    void update()
    {
        for (Key key : KEY_MAP.values())
            key.update();
    }

    public Key get(Integer keyEvent)
    {
        return !KEY_MAP.containsKey(keyEvent) ? Key.NULL_KEY : KEY_MAP.get(keyEvent);
    }

    //Super Lazy but easy initializing 
    //TODO: Make a initializing Method that does not use reflection
    private void initializeKeyMap()
    {
        Field[] fields = KeyEvent.class.getDeclaredFields();
    
        for (Field field : fields) 
        {
            int modifiers = field.getModifiers();

            if (Modifier.isPrivate(modifiers) || !Modifier.isStatic(modifiers)) 
                continue;

            try
            {
                if (field.get(null) instanceof Integer event)
                { 
                    KEY_MAP.put(event, new Key(event));
                }
            } 
            catch (IllegalArgumentException | IllegalAccessException e) 
            {
                e.printStackTrace();
            }
        }   
    }
}