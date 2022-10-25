package engine.utils.color;

public final class ColoredString
{
    public final String plain;
    public final String colored;

    ColoredString(final String plain, final String colored) 
    {
        this.plain   = plain;
        this.colored = colored;
    }

    @Override public String toString() { return colored; }
}
