package testing.jni;

import engine.api.SharedLibraryLoader;

public class JNITest 
{
    static 
    {
        SharedLibraryLoader.init();
    }
    
    public static void main(String[] args) 
    {
        new JNITest().helloWorld();
    }

    private native void helloWorld();
}
