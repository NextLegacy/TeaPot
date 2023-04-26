package testing.jni;

public class JNITest 
{
    static 
    {
        // TODO: craete library and set correct path
        System.loadLibrary("engine.dll");
    }

    public static void main(String[] args) 
    {
        helloWorld();
    }
    
    public static native void helloWorld();
}
