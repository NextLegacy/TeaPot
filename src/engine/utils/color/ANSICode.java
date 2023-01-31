package engine.utils.color;

public final class ANSICode 
{
    static int FOREGROUND = 38;
    static int BACKGROUND = 48;

    static final String START = "\033[";
    static final String END   = "m";
    static final String AND   = ";";

    static final String BOLD      = "1";
    static final String ITALIC    = "3";
    static final String UNDERLINE = "4";
    static final String STRIKE    = "9";

    static final String BOLD_AND      = BOLD      + AND;
    static final String ITALIC_AND    = ITALIC    + AND;
    static final String UNDERLINE_AND = UNDERLINE + AND;
    static final String STRIKE_AND    = STRIKE    + AND;

    static final String RESET = "\033[0m";
    
    static final String COLOR  (int whichGround, int r, int g, int b) { return whichGround + ";2;" + r + ";" + g + ";" + b; }
    static final String FOREGROUND(int r, int g, int b) { return COLOR(FOREGROUND, r, g, b); }
    static final String BACKGROUND(int r, int g, int b) { return COLOR(BACKGROUND, r, g, b); }

    final String code;

    public ANSICode(boolean bold, boolean italic, boolean underline, boolean strike, int fR, int fG, int fB, int bR, int bG, int bB)
    {
        code = START +
            (bold      ? BOLD_AND      : "") +
            (italic    ? ITALIC_AND    : "") +
            (underline ? UNDERLINE_AND : "") +
            (strike    ? STRIKE_AND    : "") +
            BACKGROUND(bR, bG, bB)           + AND +
            FOREGROUND(fR, fG, fB)           +
            END;
    }

    public ANSICode(boolean bold, boolean italic, boolean underline, boolean strike, int r, int g, int b, int whichGround)
    {
        code = START +
            (bold      ? BOLD_AND      : "") +
            (italic    ? ITALIC_AND    : "") +
            (underline ? UNDERLINE_AND : "") +
            (strike    ? STRIKE_AND    : "") +
            COLOR(whichGround, r, g, b)      +
            END;
    }

    public ANSICode(boolean bold, boolean italic, boolean underline, boolean strike)
    {
        code = START +
            (bold      ? (italic || underline || strike ? BOLD_AND      : BOLD     ) : "") +
            (italic    ? (          underline || strike ? ITALIC_AND    : ITALIC   ) : "") +
            (underline ? (                       strike ? UNDERLINE_AND : UNDERLINE) : "") +
            (strike    ? STRIKE                                                      : "") +
            END;
    }

    public String useOn(String string) { return toString() + string + RESET; }
    public String useOn(Object object) { return useOn(object.toString()); }

    @Override public String toString() { return code; }

    public static ANSICodeBuilder build() { return new ANSICodeBuilder(); }

    public static final ANSICode NONE = build().done();
    public static final ANSICode NONE() { return NONE; }
}
