package engine.window.Input;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import engine.utils.ArrayUtils;

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
    private static final int[] keyEvents;

    static 
    {
        Field[] fields = KeyEvent.class.getDeclaredFields();
    
        int[] result = new int[fields.length];

        int length = 0;

        for (Field field : fields) 
        {
            int modifiers = field.getModifiers();

            if (Modifier.isPrivate(modifiers) || !Modifier.isStatic(modifiers)) 
                continue;

            try
            {
                if (field.get(null) instanceof Integer event) // TODO: investigate why Integer and not int?
                {
                    result[length] = (Integer) field.get(null);
                    length++;
                }
            } 
            catch (IllegalArgumentException | IllegalAccessException e) 
            {
                e.printStackTrace();
            }
        }

        keyEvents = ArrayUtils.resize(result, length);
    }

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

    private void initializeKeyMap()
    {
        for (int keyEvent : keyEvents)
            KEY_MAP.put(keyEvent, new Key(keyEvent));
    }
}