package engine.utils.color;

public final class ANSICode 
{
    private static final String START = "\033[";
    private static final String END   = "m";
    private static final String AND   = ";";

    private static final String BOLD      = "1";
    private static final String ITALIC    = "3";
    private static final String UNDERLINE = "4";
    private static final String STRIKE    = "9";

    private static final String BOLD_AND      = BOLD      + AND;
    private static final String ITALIC_AND    = ITALIC    + AND;
    private static final String UNDERLINE_AND = UNDERLINE + AND;
    private static final String STRIKE_AND    = STRIKE    + AND;

    private static final String RESET = "\033[0m";
    
    private static final String FOREGROUND(int r, int g, int b) { return "38;2;" + r + ";" + g + ";" + b; }
    private static final String BACKGROUND(int r, int g, int b) { return "48;2;" + r + ";" + g + ";" + b; }

    private final String code;

    public ANSICode(boolean bold, boolean italic, boolean underline, boolean strike, int fR, int fG, int fB, int bR, int bG, int bB)
    {
        code = START +
            (bold      ? BOLD_AND      : "") +
            (italic    ? ITALIC_AND    : "") +
            (underline ? UNDERLINE_AND : "") +
            (strike    ? STRIKE_AND    : "") +
            BACKGROUND(bR, bG, bB) + AND +
            FOREGROUND(fR, fG, fB) +
            END;
    }

    public ANSICode(boolean bold, boolean italic, boolean underline, boolean strike, int r, int g, int b)
    {
        code = START +
            (bold      ? BOLD_AND      : "") +
            (italic    ? ITALIC_AND    : "") +
            (underline ? UNDERLINE_AND : "") +
            (strike    ? STRIKE_AND    : "") +
            FOREGROUND(r, g, b)              +
            END;
    }

    public ANSICode(boolean bold, boolean italic, boolean underline, boolean strike, int r, int g, int b, Object anyToColorizeBackground)
    {
        code = START +
            (bold      ? BOLD_AND      : "") +
            (italic    ? ITALIC_AND    : "") +
            (underline ? UNDERLINE_AND : "") +
            (strike    ? STRIKE_AND    : "") +
            BACKGROUND(r, g, b)              +
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

    public static final class ANSICodeBuilder
    {
        private boolean bold, italic, underline, strike;
        private int fR, fG, fB, bR, bG, bB;

        private ANSICodeBuilder() 
        { 
            bold = italic = underline = strike = false;
            fR = fG = fB = bR = bG = bB = -1;
        }

        public ANSICodeBuilder bold     () { bold      = true; return this; }
        public ANSICodeBuilder italic   () { italic    = true; return this; }
        public ANSICodeBuilder underline() { underline = true; return this; }
        public ANSICodeBuilder strike   () { strike    = true; return this; }

        public ANSICodeBuilder foreground(int r, int g, int b) { fR = r; fG = g; fB = b; return this; }
        public ANSICodeBuilder background(int r, int g, int b) { bR = r; bG = g; bB = b; return this; }

        public ANSICodeBuilder foreground(int rgb) { fR = (rgb & 0xff0000) >> 16; fG = (rgb & 0x00ff00) >> 8; fB = (rgb & 0x0000ff); return this; }
        public ANSICodeBuilder background(int rgb) { bR = (rgb & 0xff0000) >> 16; bG = (rgb & 0x00ff00) >> 8; bB = (rgb & 0x0000ff); return this; }

        public ANSICode done()
        {
            return bR == -1 && fR == -1 ? new ANSICode(bold, italic, underline, strike                        ) :
                   bR == -1 ?             new ANSICode(bold, italic, underline, strike, fR, fG, fB            ) :
                   fR == -1 ?             new ANSICode(bold, italic, underline, strike, bR, bG, bB            ) :
                                          new ANSICode(bold, italic, underline, strike, fR, fG, fB, bR, bG, bB);
        }
    }

    public static final ANSICode NONE = build().done();
    public static final ANSICode NONE() { return NONE; }
}
