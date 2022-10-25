package engine.utils.logger;

import java.util.ArrayList;

import engine.utils.Lambda.Func0;
import engine.utils.Lambda.Func1;
import engine.utils.color.ANSICode;

public final class LoggerInformationBuilder 
{
    private final ArrayList<Func1<Object, String>> parts;
    
    private final ArrayList<Func1<ANSICode, String>> ansiCodes;

    LoggerInformationBuilder() { parts = new ArrayList<>(); ansiCodes = new ArrayList<>(); }

    private final LoggerInformationBuilder add(final Func1<Object, String> part)
    {
        parts.add(part);

        return this;
    }

    public final LoggerInformationBuilder add(final Func0<Object> part) { add((level) -> part.get()); return this; }

    public final LoggerInformationBuilder add(final Func1<Boolean, String> condition, final Func0<Object> part) 
    { 
        return add((level) -> condition.get(level) ? part.get() : ""); 
    }

    public final LoggerInformationBuilder add(final Func1<Boolean, String> condition, final Object object) 
    { 
        return add((level) -> condition.get(level) ? object : ""); 
    }

    private final LoggerInformationBuilder setMessageColorCode(final Func1<ANSICode, String> ansiCodeGetter)
    {
        ansiCodes.add(ansiCodeGetter);

        return this;
    }

    public final LoggerInformationBuilder setMessageColorCode(final Func0<ANSICode> ansiCodeGetter) { setMessageColorCode((level) -> ansiCodeGetter.get()); return this; }

    public final LoggerInformationBuilder setMessageColorCode(final Func1<Boolean, String> condition, final Func0<ANSICode> ansiCodeGetter) 
    { 
        return setMessageColorCode((level) -> condition.get(level) ? ansiCodeGetter.get() : null); 
    }

    public final LoggerInformationBuilder setMessageColorCode(final Func1<Boolean, String> condition, final ANSICode ansiCode) 
    { 
        return setMessageColorCode((level) -> condition.get(level) ? ansiCode : null); 
    }

    @SuppressWarnings("unchecked")
    public final LoggerInformation done()
    {
        return new LoggerInformation(parts.toArray(new Func1[parts.size()]), (level) ->
        {
            ANSICode ansiCode = ANSICode.NONE;
            
            for (int i = 0; i < ansiCodes.size(); i++)
            {
                ANSICode temp = ansiCodes.get(i).get(level);

                if (temp == null) continue;
                //System.out.println(temp.toString().replaceAll("\u001B\\[[;\\d]*m", ""));
                ansiCode = temp;
            }

            return ansiCode;
        });
    }
}
