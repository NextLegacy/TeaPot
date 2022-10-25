package engine.utils.time;

import engine.utils.Lambda.Action0;

public final class Time 
{
    private Time() { }

    public static final double NANOS_TO_MILLIS  = 1.0E-6;
    public static final double NANOS_TO_SECONDS = 1.0E-9;

    public static final double MILLIS_TO_NANOS  = 1.0E6;
    public static final double SECONDS_TO_NANOS = 1.0E9;

    public static long   nanos  () { return System.nanoTime()         ; }
    public static double millis () { return nanos() * NANOS_TO_MILLIS ; }
    public static double seconds() { return nanos() * NANOS_TO_SECONDS; }
    
    public static double measureTime(Action0 action)
    {
        long before = nanos();
        
        action.run();

        return nanos() - before;
    }
}
