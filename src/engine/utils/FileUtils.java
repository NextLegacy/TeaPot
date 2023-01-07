package engine.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class FileUtils 
{
    private FileUtils() { }
    
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

    public static String getExtension(final String path)
    {
        String extension = "";

        int i = path.lastIndexOf('.');
        
        if (i > 0) extension = path.substring(i+1);

        return extension;
    }

    public static String getExtension(final String path, final String defaultExtension)
    {
        final String extension = getExtension(path);
        
        return extension == "" ? defaultExtension : extension;
    }
}
