package engine.utils.logger;

import engine.utils.Lambda.Func1;
import engine.utils.color.ANSICode;

public class LoggerLabel 
{
    final Func1<Boolean, String> condition;
    final ANSICode labelColorCode;
    final ANSICode messageColorCode;
    final Func1<String, String> label;

    LoggerLabel(Func1<Boolean, String> condition, Func1<String, String> label, ANSICode labelColorCode, ANSICode messageColorCode) 
    { 
        this.condition = condition;
        this.labelColorCode = labelColorCode;
        this.messageColorCode = messageColorCode;
        this.label = label;
    }

    final String getLabel(String level) { return labelColorCode + label.get(level) + ANSICode.NONE; }

    public static final LoggerLabelBuilder build() { return new LoggerLabelBuilder(); }
}
