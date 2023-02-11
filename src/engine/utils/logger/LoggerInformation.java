package engine.utils.logger;

import engine.utils.color.ANSICode;

public final class LoggerInformation 
{
    private String level;

    private final LoggerLabel[] labels;

    LoggerInformation(LoggerLabel[] labels) 
    { 
        this.labels = labels;

        level = "NONE";
    }

    public final LoggedString useOn(final String message, final String join) 
    { 
        String information = "";
        
        ANSICode messageANSICode = ANSICode.NONE;

        for (int i = 0; i < labels.length; i++)
        {
            if (!labels[i].condition.get(level)) continue;

            information += labels[i].getLabel(level) + join;

            if (labels[i].messageColorCode != ANSICode.NONE)
                messageANSICode = labels[i].messageColorCode;
        }

        //if (information.length() > 0)
        //    information = information.substring(0, information.length() - join.length());

        return new LoggedString
        (
            level,
            information + messageANSICode + message + ANSICode.NONE
        );
    }

    public final String getLevel() { return level; }
    public final LoggerInformation setLevel(String level) { this.level = level; return this; }

    public static final LoggerInformationBuilder build() { return new LoggerInformationBuilder(); }

    public static final LoggerInformation NONE = build().done();
}
