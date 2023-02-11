package engine.utils.logger;

import engine.utils.Lambda.Func1;
import engine.utils.color.ANSICode;

public class LoggerLabel 
{
    final Func1<Boolean, String> condition;
    final ANSICode labelColorCode;
    final ANSICode messageColorCode;
    final Func1<String, String> label;

    public LoggerLabel(Func1<Boolean, String> condition, Func1<String, String> label, ANSICode labelColorCode, ANSICode messageColorCode) 
    { 
        this.condition        = condition        == null ? (level) -> true : condition       ;
        this.labelColorCode   = labelColorCode   == null ? ANSICode.NONE   : labelColorCode  ;
        this.messageColorCode = messageColorCode == null ? ANSICode.NONE   : messageColorCode;
        this.label            = label            == null ? (level) -> ""   : label           ;
    }

    final String getLabel(String level) { return labelColorCode + label.get(level) + ANSICode.NONE; }
}
