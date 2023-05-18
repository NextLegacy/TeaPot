package engine.utils.activatable;

/**
 * The IActivatable interface is used to define an object that can be activated or deactivated. <p>
 * 
 * The {@link #setActive(boolean)} method is used to set the state of the object. <p>
 * 
 * Also the {@link #activate()} method can be used to activate the object and the {@link #deactivate()} method can be used to deactivate the object. <p>
 * 
 * You can check if the object is active with the {@link #isActive()} and {@link #isNotActive()} methods. <p>
 * 
 * You can use {@link ObjectIsNotActiveException#throwIfIsNotActive(IActivatable)} to throw an exception if the object is not active. <p>
 * 
 * @author NextLegacy
 * @version 1.0
 */
public interface IActivatable 
{
    /**
     * Sets the state of the object to active or inactive. <p>
     * 
     * @param state The state to set the object to
     */
    void setActive(boolean state);

    /**
     * Activates the object. <p>
     */
    default void activate  () { setActive(true ); }

    /**
     * Deactivates the object. <p>
     */
    default void deactivate() { setActive(false); }    

    /**
     * Returns true if the object is active. <p>
     * 
     * @return true if the object is active
     */
    boolean isActive();

    /**
     * Returns true if the object is inactive. <p>
     * 
     * @return true if the object is inactive
     */
    default boolean isNotActive() { return !isActive(); }
}
