package engine.utils;

import java.util.ArrayList;
import java.util.HashMap;

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

    public static abstract interface IAction { }

    @FunctionalInterface public static interface Action extends IAction
    { public void run(Object... args); }

    @FunctionalInterface public static interface Action0 extends IAction 
    { public void run(); }
    
    @FunctionalInterface public static interface Action1<T1> extends IAction 
    { public void run(T1 arg1); }
    
    @FunctionalInterface public static interface Action2<T1, T2> extends IAction 
    { public void run(T1 arg1, T2 arg2); }
    
    @FunctionalInterface public static interface Action3<T1, T2, T3> extends IAction 
    { public void run(T1 arg1, T2 arg2, T3 arg3); }
    
    @FunctionalInterface public static interface Action4<T1, T2, T3, T4> extends IAction 
    { public void run(T1 arg1, T2 arg2, T3 arg3, T4 arg4); }
    
    @FunctionalInterface public static interface Action5<T1, T2, T3, T4, T5> extends IAction 
    { public void run(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5); }
    
    @FunctionalInterface public static interface Action6<T1, T2, T3, T4, T5, T6> extends IAction 
    { public void run(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6); }
    
    @FunctionalInterface public static interface Action7<T1, T2, T3, T4, T5, T6, T7> extends IAction 
    { public void run(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7); }
    
    @FunctionalInterface public static interface Action8<T1, T2, T3, T4, T5, T6, T7, T8> extends IAction 
    { public void run(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8); }
    
    @FunctionalInterface public static interface Action9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends IAction 
    { public void run(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9); }

    public static class ActionList<T extends IAction> extends ArrayList<T>
    {
        public void runAll(Action1<T> actionRunner)
        {
            for (T action : this) actionRunner.run(action);
        }
    }

    public static class ActionHashMap<TKey, T extends IAction> extends HashMap<TKey, T>
    {
        public void runAll(Action1<T> actionRunner)
        {
            for (T action : this.values()) actionRunner.run(action);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public abstract interface IFunc<TReturn> { }

    @FunctionalInterface public static interface Func extends IFunc<Object>
    { public Object get(Object... args); }

    @FunctionalInterface public static interface Func0<TReturn> extends IFunc<TReturn>
    { public TReturn get(); }
    
    @FunctionalInterface public static interface Func1<TReturn, T1> extends IFunc<TReturn> 
    { public TReturn get(T1 arg1); }
    
    @FunctionalInterface public static interface Func2<TReturn, T1, T2> extends IFunc<TReturn> 
    { public TReturn get(T1 arg1, T2 arg2); }
    
    @FunctionalInterface public static interface Func3<TReturn, T1, T2, T3> extends IFunc<TReturn> 
    { public TReturn get(T1 arg1, T2 arg2, T3 arg3); }
    
    @FunctionalInterface public static interface Func4<TReturn, T1, T2, T3, T4> extends IFunc<TReturn>
    { public TReturn get(T1 arg1, T2 arg2, T3 arg3, T4 arg4); }
    
    @FunctionalInterface public static interface Func5<TReturn, T1, T2, T3, T4, T5> extends IFunc<TReturn>
    { public TReturn get(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5); }
    
    @FunctionalInterface public static interface Func6<TReturn, T1, T2, T3, T4, T5, T6> extends IFunc<TReturn> 
    { public TReturn get(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6); }
    
    @FunctionalInterface public static interface Func7<TReturn, T1, T2, T3, T4, T5, T6, T7> extends IFunc<TReturn> 
    { public TReturn get(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7); }
    
    @FunctionalInterface public static interface Func8<TReturn, T1, T2, T3, T4, T5, T6, T7, T8> extends IFunc<TReturn> 
    { public TReturn get(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8); }
    
    @FunctionalInterface public static interface Func9<TReturn, T1, T2, T3, T4, T5, T6, T7, T8, T9> extends IFunc<TReturn> 
    { public TReturn get(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9); }

    public static class FuncList<TReturn, T extends IFunc<TReturn>> extends ArrayList<T>
    {
        public TReturn[] getAll(Func1<TReturn, T> funcGetter)
        {
            @SuppressWarnings("unchecked")
            TReturn[] result = (TReturn[]) new Object[size()];

            for (int i = 0, length = size(); i < length; i++)
                result[i] = funcGetter.get(this.get(i));

            return result;
        }
    }

    public static class FuncHashMap<TReturn, TKey, T extends IFunc<TReturn>> extends HashMap<TKey, T>
    {
        public TReturn[] getAll(Func1<TReturn, TKey> funcGetter)
        {
            @SuppressWarnings("unchecked")
            TReturn[] result = (TReturn[]) new Object[size()];

            @SuppressWarnings("unchecked")
            TKey[] keys = (TKey[]) this.keySet().toArray();

            for (int i = 0, length = size(); i < length; i++)
                result[i] = funcGetter.get(keys[i]);

            return result;
        }
    }
}
