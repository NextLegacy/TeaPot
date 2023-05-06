package engine.api;

public final class SharedLibraryLoader 
{
    private SharedLibraryLoader() { }

    static
    {
        load("bin/Release/Engine.dll");
    }

    public static void init() { }

    public static void load(String relativePath)
    {
        try
        {
            System.load(getPath(relativePath));
        }
        catch (UnsatisfiedLinkError e)
        {
            System.err.println("Failed to load native library: " + e.getMessage());
            System.exit(1);
        }
    }

    public static String getPath(String relativePath)
    {
        return System.getProperty("user.dir") + "/bin/" + relativePath;
    }
}
