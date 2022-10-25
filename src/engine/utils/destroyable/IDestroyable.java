package engine.utils.destroyable;

public interface IDestroyable 
{
    void destroy();

    default boolean isDestroyed   () { return false         ; }
    default boolean isNotDestroyed() { return !isDestroyed(); }
}
