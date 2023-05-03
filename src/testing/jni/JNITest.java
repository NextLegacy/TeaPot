package testing.jni;

public class JNITest 
{
    static 
    {
        System.load(System.getProperty("user.dir") + "/bin/Release/Engine.dll");
    }

    public static void main(String[] args) 
    {
        new JNITest().helloWorld();
    }

    private native void helloWorld();
}
