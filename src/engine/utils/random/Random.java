package engine.utils.random;

public class Random<T extends RandomState> 
{
    public final T state;

    public Random(T state) 
    {
        this.state = state;
    }

    private final void next()
    {
        state.next();
    }

    private final long value()
    {
        return state.value();
    }
}
