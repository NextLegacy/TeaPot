package engine.utils.logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import engine.utils.Lambda.Action1;
import engine.utils.color.ANSICode;

/**
 * A logger class that can be used to log messages to output streams.
 * 
 * @author NextLegacy
 */
public final class Logger
{
    private LoggerInformation information;
    private Action1<LoggedString> outputStream;
    
    private final ArrayList<LoggedString> log;

    private boolean newLine;

    public Logger() { this((message) -> {}); }

    @SafeVarargs
    public Logger(Action1<LoggedString>... outputStreams)
    {
        this((message) -> 
        {
            for (int i = 0; i < outputStreams.length; i++)
                outputStreams[i].run(message);
        });
    }

    public Logger(Action1<LoggedString> outputStream)
    {
        this.outputStream = outputStream;

        information = LoggerInformation.NONE;

        log = new ArrayList<>();

        newLine = true;
    }

    private Logger logInternal(String message)
    {
        LoggedString loggedString = newLine ? information.useOn(message, " ") : new LoggedString(getLevel(), message);

        newLine = loggedString.plainMessage.lastIndexOf("\n") == loggedString.plainMessage.length() - 1;

        outputStream.run(loggedString);
        log.add(loggedString);

        return this;
    }

    public Logger log(String message) 
    {
        String[] lines = message.split("\n");
     
        if (lines.length > 1)
        {
            for (int i = 0; i < lines.length; i++)
                logInternal(lines[i] + (i != lines.length - 1 ? "\n" : ""));

            return this;
        }

        return logInternal(message);
    }

    public Logger log  (Object message) { return log  (message.toString()         ); }
    
    public Logger logf (String format, Object... args) { return log  (String.format(format, args)); }
    public Logger logf (Object format, Object... args) { return logf (format .toString(), args   ); }

    public Logger logln(String message) { return log  (message + "\n"             ); }
    public Logger logln(Object message) { return logln(message.toString()         ); }

    public Logger log  (String level, Object message) { final String temp = getLevel(); setLevel(level); return log  (message.toString()         ).setLevel(temp); }
    
    public Logger logf (String level, String format, Object... args) { final String temp = getLevel(); setLevel(level); return log  (String.format(format, args)).setLevel(temp); }
    public Logger logf (String level, Object format, Object... args) { final String temp = getLevel(); setLevel(level); return logf (format .toString(), args   ).setLevel(temp); }

    public Logger logln(String level, String message) { final String temp = getLevel(); setLevel(level); return logln(message           ).setLevel(temp); }
    public Logger logln(String level, Object message) { final String temp = getLevel(); setLevel(level); return logln(message.toString()).setLevel(temp); }

    public Logger setLevel(String level) { information.setLevel(level); return this; }
    public String getLevel() { return information.getLevel(); }

    public Logger setLoggerInformations(LoggerInformation information) 
    {
        this.information = information == null ? LoggerInformation.NONE : information; 
        
        return this;
    }

    public Logger outputLog(Action1<LoggedString> outputStream) 
    {
        this.outputStream = outputStream;

        for (int i = 0; i < log.size(); i++)
            outputStream.run(log.get(i));

        return this;
    }

    public static final Logger DEFAULT;

    static
    {
        DEFAULT = new Logger(System.out::print);

        ANSICode green  = ANSICode.build().foreground(0x013A12).done();
        ANSICode red    = ANSICode.build().foreground(0xF94A29).done();
        ANSICode cyan   = ANSICode.build().foreground(0x30E3DF).done();
        ANSICode yellow = ANSICode.build().foreground(0xFCE22A).done();
        
        LoggerLabel timeLabel = new LoggerLabel((str) -> true, (str) -> "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "]", null, null);
        LoggerLabel infoLabel = new LoggerLabel("info"::equals, (str) -> "[INFO]", cyan, cyan);
        LoggerLabel warnLabel = new LoggerLabel("warn"::equals, (str) -> "[WARN]", yellow, yellow);
        LoggerLabel errorLabel = new LoggerLabel("error"::equals, (str) -> "[ERROR]", red, red);
        LoggerLabel debugLabel = new LoggerLabel("debug"::equals, (str) -> "[DEBUG]", green, green);

        DEFAULT.setLoggerInformations(new LoggerInformation(new LoggerLabel[] 
        { 
            timeLabel, infoLabel, warnLabel, errorLabel, debugLabel 
        }));
    }
}
