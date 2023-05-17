package engine.utils.time;

import engine.utils.ArrayUtils;
import engine.utils.Lambda.Action0;

/**
 * This class provides utility methods for time. <p>
 * It is not possible to instantiate this class. <p>
 * 
 * @author NextLegacy
 * @version 1.0
 */
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
    
    /**
     * This method measures the time taken to run the given action. <p>
     *  
     * @param action the action to run
     * 
     * @return the time taken to run the action
     */
    public static double measureTime(Action0 action)
    {
        long before = nanos();
        
        action.run();

        return nanos() - before;
    }

    /**
     * This method benchmarks the given actions. <p>
     * 
     * @param actions the actions to benchmark
     * @param iterations the number of iterations to run
     * 
     * @return the average time taken to run each action
     */
    public static double[] benchmark(Action0[] actions, int iterations)
    {
        double[] results = new double[actions.length];

        for (int i = 0; i < iterations; i++)
        {
            for (int j = 0; j < actions.length; j++)
            {
                results[j] += measureTime(actions[j]);
            }
        }

        ArrayUtils.forEach(results, (result) -> result /= iterations);

        return results;
    }
}
