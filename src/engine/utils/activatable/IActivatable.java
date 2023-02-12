package engine.utils.activatable;

/**
 * The IActivatable interface is used to define an object that can be activated or deactivated. <p>
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
