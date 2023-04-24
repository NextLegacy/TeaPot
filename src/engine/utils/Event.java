package engine.utils;

import engine.utils.Lambda.Func0;
import engine.utils.Lambda.ILambda;

// a rough idea on how to implement a state machine

public class Event<TState, TKey, TLambda extends ILambda>
{
    private TKey key;
    
    private TState state;

    private Func0<Boolean> condition;

    private TLambda lambda;

    private Event<TState, TKey, ? extends TLambda> next;

    public Event(TKey key, TState state, Func0<Boolean> condition, TLambda lambda)
    {
        this.key = key;
        this.state = state;
        this.condition = condition;
        this.lambda = lambda;
    }

    public Event(TKey key, TState state, Func0<Boolean> condition, TLambda lambda, Event<TState, TKey, ? extends TLambda> next)
    {
        this.key = key;
        this.state = state;
        this.condition = condition;
        this.lambda = lambda;
        this.next = next;
    }
}
