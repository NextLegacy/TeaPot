package engine.utils.destroyable;

public interface IDestroyable 
{
    void destroy();

    boolean isDestroyed();
    default boolean isNotDestroyed() { return !isDestroyed(); }
}
