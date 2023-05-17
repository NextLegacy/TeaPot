package engine.utils.destroyable;

/**
 * This exception is thrown if an object is destroyed and an action is performed on it. <p>
 */
public final class ObjectIsDestroyedException extends RuntimeException
{
    public ObjectIsDestroyedException(IDestroyable destroyable)
    {
        super("Can not perform Action, " + destroyable + " is destroyed!");
    }

    /**
     * This method throws an {@link ObjectIsDestroyedException} if the object is destroyed. <p>
     * This method can be used to validate if an object is destroyed. <p>
     * 
     * @param destroyable The object to check if it is destroyed.
     * @throws ObjectIsDestroyedException If the object is destroyed.
     */
    public static <T extends IDestroyable> void throwIfIsDestroyed(T destroyable)
    {
        if (destroyable == null || destroyable.isDestroyed())
            throw new ObjectIsDestroyedException(destroyable);
    }
}
