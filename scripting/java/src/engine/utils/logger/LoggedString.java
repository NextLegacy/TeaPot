package engine.utils.logger;

/**
 * This class represents a logged string.
 * A logged string consists of a level, a message and a plain message.
 * 
 * @author NextLegacy
 */
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
