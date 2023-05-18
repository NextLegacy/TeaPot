package engine.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is not meant to be instantiated.
 * It contains static methods for file operations.
 * 
 * @author NextLegacy
 */
public final class FileUtils 
{
    private FileUtils() { }
    
    /**
     * Reads a file and returns its content as a String.
     * 
     * @param file The file to read.
     * @return The content of the file.
     */
    public static String[] getLines(String file)
    {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file)))
        {
            ArrayList<String> lines = new ArrayList<>();

            while(true)
            {
                String line = bufferedReader.readLine();
                if (line == null) break;
                lines.add(line);
            }

            return lines.toArray(String[]::new);
        } catch (IOException e) 
        {
            e.printStackTrace();
        }

        return new String[0];
    }

    /**
     * Returns the extension of a file.
     * 
     * @param path The path of the file.
     * @return The extension of the file.
     */
    public static String getExtension(final String path)
    {
        String extension = "";

        int i = path.lastIndexOf('.');
        
        if (i > 0) extension = path.substring(i+1);

        return extension;
    }

    /**
     * Returns the extension of a file.
     * If the file has no extension, the default extension is returned.
     * 
     * @param path The path of the file.
     * @param defaultExtension The default extension.
     * @return The extension of the file.
     */
    public static String getExtension(final String path, final String defaultExtension)
    {
        final String extension = getExtension(path);
        
        return extension == "" ? defaultExtension : extension;
    }

    /**
     * Writes a String to a file.
     * 
     * @param file The file to write to.
     * @param content The content to write.
     */
    public static void write(String file, String content)
    {
        FileWriter fileWriter;
        try 
        {
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}
