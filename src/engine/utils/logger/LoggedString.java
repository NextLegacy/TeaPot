package engine.utils.logger;

public final class LoggedString 
{
    final String level;
    final String message;
    final String plainMessage;

    LoggedString(String level, String message) 
    { 
        this.level = level;
        this.message = message;
        
        plainMessage = message.replaceAll("\u001B\\[[;\\d]*m", "");
    }

    @Override public String toString() { return toString(true); }

    public String toString(boolean includeColorCodes) 
    { 
        return includeColorCodes ? message : plainMessage;
    }
}
