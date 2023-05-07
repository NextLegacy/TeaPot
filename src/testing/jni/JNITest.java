package testing.jni;

import engine.api.SharedLibraryLoader;

public class JNITest 
{
    static 
    {
        SharedLibraryLoader.initLibrarys();
    }
    
    public static void main(String[] args) 
    {
        new JNITest().helloWorld();
    }

    private native void helloWorld();
}
