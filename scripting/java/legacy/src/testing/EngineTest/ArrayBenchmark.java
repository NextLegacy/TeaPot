package testing.EngineTest;

import java.util.Arrays;

import engine.utils.ArrayUtils;
import engine.utils.Lambda.Action0;
import engine.utils.time.Time;

public class ArrayBenchmark 
{
    public static void main(String[] args) 
    {
        double[] time = Time.benchmark(new Action0[] { 
            () ->
            {
                int[] array = new int[10000];
                Arrays.fill(array, 0, array.length - 1, 1561361);
            },
            () ->
            {
                int[] array = new int[10000];
                ArrayUtils.fill(array, 1561361, 0, array.length - 1);
            }
        }, 100);

        System.out.println("Time: " + ArrayUtils.toString(time));
    }
}
