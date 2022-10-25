package engine.utils;

public final class ClassUtils 
{
    private ClassUtils() { }
    
    public static <T> String simpleToString(Class<T> clazz, String data)
    {
        return clazz.getSimpleName() + "(" + data + ")";
    }
}
