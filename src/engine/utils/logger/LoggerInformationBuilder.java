package engine.utils.logger;

import java.util.ArrayList;

public final class LoggerInformationBuilder 
{
    final ArrayList<LoggerLabel> labels;

    LoggerInformationBuilder() 
    { 
        labels = new ArrayList<>(); 
    }

    public final LoggerInformationBuilder addLabel(final LoggerLabel label)
    {
        labels.add(label);

        return this;
    }

    public final LoggerInformation done()
    {
        return new LoggerInformation(labels.toArray(LoggerLabel[]::new));
    }
}
