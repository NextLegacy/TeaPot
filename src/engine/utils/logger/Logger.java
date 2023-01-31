package engine.utils.logger;

import java.util.ArrayList;

import engine.utils.Lambda.Action1;

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
}
