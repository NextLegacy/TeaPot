package engine.utils.destroyable;

final class DestroyedException extends RuntimeException
{
    public DestroyedException(IDestroyable destroyable)
    {
        super("Can not perform Action, " + destroyable + " is destroyed!");
    }
}
