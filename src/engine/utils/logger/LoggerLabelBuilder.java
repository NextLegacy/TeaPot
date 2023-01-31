package engine.utils.logger;

import engine.utils.Lambda.Func1;
import engine.utils.color.ANSICode;

public class LoggerLabelBuilder 
{
    Func1<Boolean, String> condition;
    ANSICode labelColorCode;
    ANSICode messageColorCode;
    Func1<String, String> label;

    LoggerLabelBuilder() 
    { 
        condition = level -> true;
        labelColorCode = ANSICode.NONE;
        messageColorCode = ANSICode.NONE;
        label = level -> level;
    }

    public final LoggerLabelBuilder setCondition(Func1<Boolean, String> condition)
    {
        this.condition = condition;

        return this;
    }

    public final LoggerLabelBuilder setLabel(Func1<String, String> label)
    {
        this.label = label;

        return this;
    }

    public final LoggerLabelBuilder setLabelColorCode(ANSICode labelColorCode)
    {
        this.labelColorCode = labelColorCode;

        return this;
    }

    public final LoggerLabelBuilder setMessageColorCode(ANSICode messageColorCode)
    {
        this.messageColorCode = messageColorCode;

        return this;
    }

    public final LoggerLabel done()
    {
        return new LoggerLabel(condition, label, labelColorCode, messageColorCode);
    }    
}
