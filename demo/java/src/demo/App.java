package demo;

import java.util.concurrent.CompletableFuture;

import testing.core.CoreTest;

public class App 
{
    public static void main(String[] args) 
    {
        System.load(System.getProperty("user.dir") + "/engine_java_api.dll");
        //SharedLibraryLoader.load("engine_core.lib");
        //JNITest.main(args);

        System.out.println("Hello World!");

        CoreTest.test();

        CompletableFuture.runAsync(() -> {
            System.out.println("Hallo");
        });

        new Thread(() -> { }).run();
    }    
}
  