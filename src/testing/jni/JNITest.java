package testing.jni;

public class JNITest 
{
    static 
    {
        //System.load(System.getProperty("user.dir") + "/cpp/dependencies/glfw/lib/glfw3.dll");
        System.load(System.getProperty("user.dir") + "/cpp/build/Release/vulkan-1.dll");
        System.load(System.getProperty("user.dir") + "/cpp/build/Release/Engine.dll");
    }

    public static void main(String[] args) 
    {
        new JNITest().helloWorld();

        System.out.println("End");
    }
    
    private native void helloWorld();
}
