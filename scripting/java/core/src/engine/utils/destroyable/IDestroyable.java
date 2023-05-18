package engine.utils.destroyable;

/**
 * This interface is used to mark objects that can be destroyed. <p>
 * 
 * The {@link #destroy()} method is used to destroy the object. <p>
 * 
 * The {@link #isDestroyed()} method is used to check if the object is destroyed. <p>
 * 
 * You can use {@link ObjectIsDestroyedException#throwIfIsDestroyed(IDestroyable)} to throw an exception if the object is destroyed. <p>
 * 
 * @author NextLegacy
 */
public interface IDestroyable 
{
    /**
     * Destroys the object.
     */
    void destroy();

    /**
     * Checks if the object is destroyed.
     */
    boolean isDestroyed();

    /**
     * Checks if the object is not destroyed.
     */
    default boolean isNotDestroyed() { return !isDestroyed(); }
}
