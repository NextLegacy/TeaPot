package engine.utils.activatable;

/**
 * This exception is thrown if an object is not active and an action is performed on it. <p>
 * 
 * @author NextLegacy
 * @version 1.0
 */
public final class ObjectIsNotActiveException extends RuntimeException
{
    public ObjectIsNotActiveException(IActivatable activatable)
    {
        super("Can not perform Action, " + activatable + " is not active!");
    }

    /**
     * This method throws an {@link ObjectIsNotActiveException} if the object is not active. <p>
     * This method can be used to validate if an object is active. <p>
     * 
     * @param activatable The object to check if it is active.
     * @throws ObjectIsNotActiveException If the object is not active.
     */
    public static <T extends IActivatable> void throwIfIsNotActive(T activatable)
    {
        if (activatable == null || activatable.isNotActive())
            throw new ObjectIsNotActiveException(activatable);
    }
}
