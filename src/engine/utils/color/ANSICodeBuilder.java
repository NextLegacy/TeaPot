package engine.utils.color;

public final class ANSICodeBuilder
{
    private boolean bold, italic, underline, strike;
    private int fR, fG, fB, bR, bG, bB;

    ANSICodeBuilder() 
    { 
        bold = italic = underline = strike = false;
        fR = fG = fB = bR = bG = bB = -1;
    }

    public ANSICodeBuilder bold     () { bold      = true; return this; }
    public ANSICodeBuilder italic   () { italic    = true; return this; }
    public ANSICodeBuilder underline() { underline = true; return this; }
    public ANSICodeBuilder strike   () { strike    = true; return this; }

    public ANSICodeBuilder bold     (boolean state) { bold      = state; return this; }
    public ANSICodeBuilder italic   (boolean state) { italic    = state; return this; }
    public ANSICodeBuilder underline(boolean state) { underline = state; return this; }
    public ANSICodeBuilder strike   (boolean state) { strike    = state; return this; }

    public ANSICodeBuilder foreground(int r, int g, int b) { fR = r; fG = g; fB = b; return this; }
    public ANSICodeBuilder background(int r, int g, int b) { bR = r; bG = g; bB = b; return this; }

    public ANSICodeBuilder foreground(int rgb) { fR = (rgb & 0xff0000) >> 16; fG = (rgb & 0x00ff00) >> 8; fB = (rgb & 0x0000ff); return this; }
    public ANSICodeBuilder background(int rgb) { bR = (rgb & 0xff0000) >> 16; bG = (rgb & 0x00ff00) >> 8; bB = (rgb & 0x0000ff); return this; }

    public ANSICode done()
    {
        return bR == -1 && fR == -1 ? new ANSICode(bold, italic, underline, strike                                 ) :
               bR == -1 ?             new ANSICode(bold, italic, underline, strike, fR, fG, fB, ANSICode.FOREGROUND) :
               fR == -1 ?             new ANSICode(bold, italic, underline, strike, bR, bG, bB, ANSICode.BACKGROUND) :
                                      new ANSICode(bold, italic, underline, strike, fR, fG, fB, bR, bG, bB);
    }
}