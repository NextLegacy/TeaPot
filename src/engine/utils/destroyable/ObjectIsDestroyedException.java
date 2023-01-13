package engine.utils.destroyable;

public final class ObjectIsDestroyedException extends RuntimeException
{
    public ObjectIsDestroyedException(IDestroyable destroyable)
    {
        super("Can not perform Action, " + destroyable + " is destroyed!");
    }

    public static <T extends IDestroyable> void throwIfIsDestroyed(T destroyable)
    {
        if (destroyable.isDestroyed())
            throw new ObjectIsDestroyedException(destroyable);
    }
}
