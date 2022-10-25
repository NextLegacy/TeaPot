package engine.utils.logger;

import engine.utils.Lambda.Func1;
import engine.utils.color.ANSICode;

public final class LoggerInformation 
{
    private String level;

    private final Func1<Object, String>[] parts;

    private final Func1<ANSICode, String> messageColorCode;

    LoggerInformation(Func1<Object, String>[] parts, Func1<ANSICode, String> messageColorCode) 
    { 
        this.parts = parts;
        this.messageColorCode = messageColorCode;
    }

    public final String useOn(String message)
    {
        return this + messageColorCode.get(level).useOn(message);
    }

    public final String useOn(Object object)
    {
        return useOn(object);
    }

    public final String toString() 
    { 
        String information = "";
        
        for (int i = 0; i < parts.length; i++)
            information += parts[i].get(level).toString();

        return information;
    }

    public final String getLevel() { return level; }
    public final LoggerInformation setLevel(String level) { this.level = level; return this; }

    public static final LoggerInformationBuilder build() { return new LoggerInformationBuilder(); }

    public static final LoggerInformation NONE = build().done();
}
