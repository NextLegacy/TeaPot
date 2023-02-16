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

    private ANSICode ansiCode;

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
        ansiCode = ANSICode.NONE;
        plainString = coloredString = "";
    }

    public ColoredStringBuilder defaultSettings() 
    {
        anythingChanged = true; bold = italic = underline = strike = false;
        foreground = -1; background = -1;
        ansiCode = ANSICode.NONE;

        return this; 
    }

    public ColoredStringBuilder bold     (boolean state) { anythingChanged = true; bold      = state; return this; }
    public ColoredStringBuilder italic   (boolean state) { anythingChanged = true; italic    = state; return this; }
    public ColoredStringBuilder underline(boolean state) { anythingChanged = true; underline = state; return this; }
    public ColoredStringBuilder strike   (boolean state) { anythingChanged = true; strike    = state; return this; }

    public ColoredStringBuilder foreground(int rgb) { anythingChanged = true; foreground = rgb; return this; }
    public ColoredStringBuilder background(int rgb) { anythingChanged = true; background = rgb; return this; }

    public String getCurrentANSICode()
    {
        if (anythingChanged)
        {
            final int fR = (foreground & 0xff0000) >> 16, fG = (foreground & 0x00ff00) >> 8, fB = (foreground & 0x0000ff);
            final int bR = (background & 0xff0000) >> 16, bG = (background & 0x00ff00) >> 8, bB = (background & 0x0000ff);
        
            ansiCode = new ANSICode
            (
                bold, 
                italic, 
                underline, 
                strike, 
                fR, fG, fB,
                bR, bG, bB
            );
        }

        return ansiCode.toString();
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
