package testing.jni;

public class JNITest 
{
    static 
    {
        System.load("ABSOLUTE_PATH_TO_ENGINE_DLL");
    }

    public static void main(String[] args) 
    {
        new JNITest().helloWorld();
    }
    
    private native void helloWorld();
}
