package engine.utils.array;

import engine.utils.ArrayUtils;

public class ArrayList<T>
{
    private final Class<T> componentType;

    private T[] buffer;
    private int length;

    public ArrayList(final T... array)
    {
        componentType = ArrayUtils.componentTypeOf(array);

        set(array);
    }


    private final T[] newInstance(final int length)
    {
        return ArrayUtils.newInstance(componentType, length);
    }
    
    private final void set(final T[] newBuffer)
    {
        buffer = newBuffer;
        length = buffer.length;
    }
}
