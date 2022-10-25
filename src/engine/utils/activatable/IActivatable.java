package engine.utils.activatable;

public interface IActivatable 
{
    void setActive(boolean state);

    default void activate  () { setActive(true ); }
    default void deactivate() { setActive(false); }    

    boolean isActive();
    default boolean isNotActive() { return !isActive(); }
}
