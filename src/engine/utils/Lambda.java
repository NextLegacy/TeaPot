package engine.utils;

/**
 * A bunch of useful methods that can be used as lambda expressions.
 *
 * @author NextLegacy
 * @version 1.0
 */
public final class Lambda 
{
    private Lambda() { }

    public static final void NOOP() { }

    public static final <T> T self(T self) { return self; }
    public static final <T> boolean isNotNull(T item) { return item != null; }
    public static final <T> boolean isNull   (T item) { return item == null; }

    public static final <T> void iterate(Iterable<T> iterable, Action1<T> action)
    {
        for (T item : iterable)
            action.run(item);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * This interaface is the super interface of all lambda expressions.
     */
    public static abstract interface ILambda 
    {
        /**
         * This method is used to invoke the Lambda expression without knowing the type of it.
         * This may be useful when you have a list of Lambda expressions and you want to invoke them all.
         */
        Object invokeLambda(Object... args);
    }

    /**
     * This interface is used to create a lambda expression that does not return anything.
     */
    public static abstract interface IAction extends ILambda 
    {
        @Override default Object invokeLambda(Object... args) 
        { 
            invokeAction(args); 
            return null; 
        } 

        /**
         * This method is used to invoke the action.
         */
        public void invokeAction(Object... args); 
    }

    @FunctionalInterface 
    public static interface Action extends IAction
    { 
        public void invoke(Object... args); 
    
        @Override 
        default void invokeAction(Object... args) 
        { 
            invoke(args); 
        }
    }

    @FunctionalInterface 
    public static interface Action0 extends IAction 
    { 
        public void run();
    
        @Override 
        default void invokeAction(Object... args) 
        { 
            run(); 
        }
    }
    
    @FunctionalInterface 
    public static interface Action1<T1> extends IAction 
    { 
        public void run(T1 arg1);
    
        @Override 
        @SuppressWarnings("unchecked")
        default void invokeAction(Object... args) 
        { 
            run((T1) args[0]); 
        }
    }

    @FunctionalInterface
    public static interface Action2<T1, T2> extends IAction 
    { 
        public void run(T1 arg1, T2 arg2);
    
        @Override 
        @SuppressWarnings("unchecked")
        default void invokeAction(Object... args) 
        { 
            run((T1) args[0], (T2) args[1]); 
        }
    }

    @FunctionalInterface
    public static interface Action3<T1, T2, T3> extends IAction 
    { 
        public void run(T1 arg1, T2 arg2, T3 arg3);
    
        @Override 
        @SuppressWarnings("unchecked")
        default void invokeAction(Object... args) 
        { 
            run((T1) args[0], (T2) args[1], (T3) args[2]); 
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * This interface is used to create a lambda expression that returns something.
     * @param <TReturn> The type of the return value.
     */
    public abstract interface IFunc<TReturn> extends ILambda 
    {
        @Override default Object invokeLambda(Object... args) 
        { 
            invokeFunc(args); 
            return null; 
        } 

        /**
         * This method is used to invoke the function and return the result with the specified type.
         */
        public TReturn invokeFunc(Object... args); 
    }

    @FunctionalInterface 
    public static interface Func extends IFunc<Object>
    {
        public Object get(Object... args);

        @Override
        default Object invokeFunc(Object... args)
        {
            return get(args);
        }
    }

    @FunctionalInterface 
    public static interface Func0<TReturn> extends IFunc<TReturn>
    {
        public TReturn get();

        @Override
        default TReturn invokeFunc(Object... args)
        {
            return get();
        }
    }
    
    @FunctionalInterface 
    public static interface Func1<TReturn, T1> extends IFunc<TReturn>
    {
        public TReturn get(T1 arg1);

        @Override
        @SuppressWarnings("unchecked")
        default TReturn invokeFunc(Object... args)
        {
            return get((T1) args[0]);
        }
    }

    @FunctionalInterface 
    public static interface Func2<TReturn, T1, T2> extends IFunc<TReturn>
    {
        public TReturn get(T1 arg1, T2 arg2);

        @Override
        @SuppressWarnings("unchecked")
        default TReturn invokeFunc(Object... args)
        {
            return get((T1) args[0], (T2) args[1]);
        }
    }

    @FunctionalInterface 
    public static interface Func3<TReturn, T1, T2, T3> extends IFunc<TReturn>
    {
        public TReturn get(T1 arg1, T2 arg2, T3 arg3);

        @Override
        @SuppressWarnings("unchecked")
        default TReturn invokeFunc(Object... args)
        {
            return get((T1) args[0], (T2) args[1], (T3) args[2]);
        }
    }
}
