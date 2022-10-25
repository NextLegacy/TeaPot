package engine.utils.color;

public final class ColoredStringBuilder 
{
    private boolean bold     ;
    private boolean italic   ;
    private boolean underline;
    private boolean strike   ;
    
    private int foreground;
    private int background;

    private boolean anythingChanged;

    private String ansiCode;

    private String plainString  ;
    private String coloredString;

    public static ColoredStringBuilder build()
    {
        return new ColoredStringBuilder();
    }

    ColoredStringBuilder()
    {
        anythingChanged = bold = italic = underline = strike = false;
        foreground = -1; background = -1;
        ansiCode = plainString = coloredString = "";
    }

    public ColoredStringBuilder defaultSettings() 
    {
        anythingChanged = true; bold = italic = underline = strike = false;
        foreground = -1; background = -1;
        ansiCode = "\033[0";

        return this; 
    }

    public ColoredStringBuilder bold     (boolean state) { anythingChanged = true; bold      = state; return this; }
    public ColoredStringBuilder italic   (boolean state) { anythingChanged = true; italic    = state; return this; }
    public ColoredStringBuilder underline(boolean state) { anythingChanged = true; underline = state; return this; }
    public ColoredStringBuilder strike   (boolean state) { anythingChanged = true; strike    = state; return this; }

    public ColoredStringBuilder foreground(int color) { anythingChanged = true; foreground = color; return this; }
    public ColoredStringBuilder background(int color) { anythingChanged = true; background = color; return this; }

    //public ColoredStringBuilder foreground(int rgb)             { return foreground(Color.fromRGB(rgb)); }
    //public ColoredStringBuilder background(int rgb)             { return background(Color.fromRGB(rgb)); }
    //
    //public ColoredStringBuilder foreground(int r, int g, int b) { return foreground(Color.fromRGB(r, g, b)); }
    //public ColoredStringBuilder background(int r, int g, int b) { return background(Color.fromRGB(r, g, b)); }

    public String getCurrentANSICode()
    {
        if (!anythingChanged)
            return ansiCode;

        return ansiCode = "\033[" + 
            (bold      ? "1;" : "") +
            (italic    ? "3;" : "") +
            (underline ? "4;" : "") +
            (strike    ? "9;" : "") +    
            (
                background == -1 ? "" :
                "48;2;" +
                ((background >>> 16) & 0xFF) + ";" +
                ((background >>>  8) & 0xFF) + ";" +
                ((background       ) & 0xFF) + (foreground == -1 ? "" : ";")
            ) +
            (
                foreground == -1 ? "" :
                "38;2;" +
                ((foreground >>> 16) & 0xFF) + ";" +
                ((foreground >>>  8) & 0xFF) + ";" +
                ((foreground       ) & 0xFF) + ""
            ) + 
            "m";
    }
    
    public ColoredStringBuilder append(String string) 
    {
        plainString += string;

        coloredString += (anythingChanged ? getCurrentANSICode() : "") + string; 
        anythingChanged = false;

        return this; 
    }

    public ColoredStringBuilder append(Object object)
    {
        return append(object.toString());
    }

    public ColoredStringBuilder appendPlain(String string)
    {
        plainString += string;
        coloredString += "\033[0m" + string;
        anythingChanged = true;
        
        return this;
    }

    public ColoredStringBuilder line()
    {
        return append("\n");
    }

    public ColoredStringBuilder linePlain()
    {
        return appendPlain("\n");
    }

    public ColoredStringBuilder line(int count)
    {
        for (int i = 0; i < count; i++)
            line();
            
        return this;
    }

    public ColoredStringBuilder linePlain(int count)
    {
        for (int i = 0; i < count; i++)
            linePlain();

        return this;
    }

    public ColoredString coloredString() { return new ColoredString(plainString, coloredString + "\033[0m"); }
}
