package engine.api;

public final class SharedLibraryLoader 
{
    private SharedLibraryLoader() { }

    public static void initLibrarys() 
    { 
        load("Engine.dll");
    }

    public static void load(String relativePath)
    {
        String path = getPath(relativePath);

        try
        {
            System.load(path);
        }
        catch (UnsatisfiedLinkError e)
        {
            System.err.println("Failed to load native library: " + path);
            System.exit(1);
        }
    }

    public static String getPath(String relativePath)
    {
        return System.getProperty("user.dir") + "/bin/" + relativePath;
    }
}
