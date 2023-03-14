package engine.utils; 
//FIXME: The type java.lang.NegativeArraySizeException cannot be resolved. It is indirectly referenced from required type java.lang.reflect.ArrayJava(16777563)
//seems like the error is gone,  weird

import java.lang.reflect.Array;
import java.util.Arrays;

import engine.utils.ArrayUtils.ArrayFunctions.*;
import engine.utils.random.Random;

/** 
 * This class provides utility methods for arrays. <p>
 * It is not possible to instantiate this class. <p>
 * 
 * It has overall the same methods as {@link java.util.Arrays}, but with some differences. <p>
 * 
 * The methods of this class are designed for best performance and type safety.
 * Because of the way how java handles generics, objects and primitives, most of the methods of this class are duplicated for each type. <p>
 * 
 * @author NextLegacy
 * @version 1.0
 */
public final class ArrayUtils
{
    private ArrayUtils() { }

    /*
     * because javas way of dealing with generics, objects and primitves is a huge mess
     * this is gonna be a pretty (unnecessary) long class full of boilerplates, 
     * but as much I have thought about it, its the best way, without risking weird type unsafety and bad performance
     *
     * these are over and over the same methods for different types
     *
     * here a list of the methods of this class in an compact way
     * in which T and U will stand for all primitives, objects and other types:
     * 
     *  T[] push(T[] array, T[] values)
     *  T[] pushAtIndex(T[] array, int index, T[] values)
     *
     *  T[] remove(T[] array, T[] values)
     *  T[] removeRange(T[] array, int start, int end);
     *  T[] remove(T[] array, int[] indecies)
     *
     *  T[] combine(T[][] arrays)
     * 
     *  T[] resize(T[] array, int newLength)
     *
     *  int indexOf(T[] array, T value)
     *  int[] indeciesOf(T[] array, T[] values)
     *
     *  boolean contains(T[] array, T value)
     * 
     *  void sort(T[] array, (T a, T b) => int)
     *  void sort(T[] array, int start, int end, (T a, T b) => int)
     * 
     *  void reverse(T[] array)
     *  void reverse(T[] array, int start, int end)
     * 
     *  void fill(T[] array, T value)
     *  void fill(T[] array, T value, int start, int end)
     * 
     *  void copy(T[] source, int sourceOffset, T[] destination, int destinationOffset, int length)
     *  void copy(T[] source, T[] destination, int length)
     *  void copy(T[] source, T[] destination)
     * 
     *  void clone(T[] array)
     *  void clone(T[] array, int start, int end)
     *
     *  String toString(T[] array)
     *  String toString(T[] array, int depth)
     * 
     *  T[] newInstance(Class<T> type, int length)
     *  Class<T> componentTypeOf(T[] array)
     *
     *  U[] map(T[] array, (T value) => U, U... junk)
     * 
     *  T = boolean | byte | char | short | int | long | float | double | any
     *  U = boolean | byte | char | short | int | long | float | double | any
     */
    
    private static Object push(final Object resizedArray, final int arrayLength, final Object values, final int valuesLength)
    {
        untypedCopy(values, 0, resizedArray, arrayLength, valuesLength);
        
        return resizedArray;
    }
    
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T> T      [] push(final T      [] array, final T      ... values) { return (T      []) push(resize(array, array.length + values.length), array.length, values, values.length); }
    public static     boolean[] push(final boolean[] array, final boolean... values) { return (boolean[]) push(resize(array, array.length + values.length), array.length, values, values.length); }
    public static     byte   [] push(final byte   [] array, final byte   ... values) { return (byte   []) push(resize(array, array.length + values.length), array.length, values, values.length); }
    public static     char   [] push(final char   [] array, final char   ... values) { return (char   []) push(resize(array, array.length + values.length), array.length, values, values.length); }
    public static     short  [] push(final short  [] array, final short  ... values) { return (short  []) push(resize(array, array.length + values.length), array.length, values, values.length); }
    public static     int    [] push(final int    [] array, final int    ... values) { return (int    []) push(resize(array, array.length + values.length), array.length, values, values.length); }
    public static     long   [] push(final long   [] array, final long   ... values) { return (long   []) push(resize(array, array.length + values.length), array.length, values, values.length); }
    public static     float  [] push(final float  [] array, final float  ... values) { return (float  []) push(resize(array, array.length + values.length), array.length, values, values.length); }
    public static     double [] push(final double [] array, final double ... values) { return (double []) push(resize(array, array.length + values.length), array.length, values, values.length); }

    private static Object pushAtIndex(final Object array, final int arrayLength, final int index, final Object values, final int valuesLength, final Object result)
    {
        untypedCopy(array, 0, result, 0, index);
        untypedCopy(values, 0, result, index, valuesLength);
        untypedCopy(array, index, result, index + valuesLength, arrayLength - index);
        
        return result;
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T> T      [] pushAtIndex(final T      [] array, final int index, final T      ... values) { return (T      []) pushAtIndex(array, array.length, index, values, values.length, array(array.length + values.length, values)); }
    public static     boolean[] pushAtIndex(final boolean[] array, final int index, final boolean... values) { return (boolean[]) pushAtIndex(array, array.length, index, values, values.length, new boolean[array.length + values.length]  ); }
    public static     byte   [] pushAtIndex(final byte   [] array, final int index, final byte   ... values) { return (byte   []) pushAtIndex(array, array.length, index, values, values.length, new byte   [array.length + values.length]  ); }
    public static     char   [] pushAtIndex(final char   [] array, final int index, final char   ... values) { return (char   []) pushAtIndex(array, array.length, index, values, values.length, new char   [array.length + values.length]  ); }
    public static     short  [] pushAtIndex(final short  [] array, final int index, final short  ... values) { return (short  []) pushAtIndex(array, array.length, index, values, values.length, new short  [array.length + values.length]  ); }
    public static     int    [] pushAtIndex(final int    [] array, final int index, final int    ... values) { return (int    []) pushAtIndex(array, array.length, index, values, values.length, new int    [array.length + values.length]  ); }
    public static     long   [] pushAtIndex(final long   [] array, final int index, final long   ... values) { return (long   []) pushAtIndex(array, array.length, index, values, values.length, new long   [array.length + values.length]  ); }
    public static     float  [] pushAtIndex(final float  [] array, final int index, final float  ... values) { return (float  []) pushAtIndex(array, array.length, index, values, values.length, new float  [array.length + values.length]  ); }
    public static     double [] pushAtIndex(final double [] array, final int index, final double ... values) { return (double []) pushAtIndex(array, array.length, index, values, values.length, new double [array.length + values.length]  ); }
    //public static     double [] pushAtIndex(final double [] array, final int index, final double ... values) { return combine(clone(array, 0, index), values, clone(array, index, array.length)); }

    @SafeVarargs
    public static <T> T      [] remove(final T      [] array, final T      ... values) { return removeIndecies(array, indeciesOf(array, values)); }
    public static     boolean[] remove(final boolean[] array, final boolean... values) { return removeIndecies(array, indeciesOf(array, values)); }
    public static     byte   [] remove(final byte   [] array, final byte   ... values) { return removeIndecies(array, indeciesOf(array, values)); }
    public static     char   [] remove(final char   [] array, final char   ... values) { return removeIndecies(array, indeciesOf(array, values)); }
    public static     short  [] remove(final short  [] array, final short  ... values) { return removeIndecies(array, indeciesOf(array, values)); }
    public static     int    [] remove(final int    [] array, final int    ... values) { return removeIndecies(array, indeciesOf(array, values)); }
    public static     long   [] remove(final long   [] array, final long   ... values) { return removeIndecies(array, indeciesOf(array, values)); }
    public static     float  [] remove(final float  [] array, final float  ... values) { return removeIndecies(array, indeciesOf(array, values)); }
    public static     double [] remove(final double [] array, final double ... values) { return removeIndecies(array, indeciesOf(array, values)); }

    private static Object removeRange(final Object array, final int arrayLength, final Object result, final int index, final int length)
    {
        untypedCopy(array, 0, result, 0, index);
        untypedCopy(array, index + length, result, index, arrayLength - length - index);

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T      [] removeRange(final T      [] array, final int index, final int length) { return (T      []) removeRange(array, array.length, array(array.length - length, array), index, length); }
    public static     boolean[] removeRange(final boolean[] array, final int index, final int length) { return (boolean[]) removeRange(array, array.length, new boolean[array.length - length] , index, length); }
    public static     byte   [] removeRange(final byte   [] array, final int index, final int length) { return (byte   []) removeRange(array, array.length, new byte   [array.length - length] , index, length); }
    public static     char   [] removeRange(final char   [] array, final int index, final int length) { return (char   []) removeRange(array, array.length, new char   [array.length - length] , index, length); }
    public static     short  [] removeRange(final short  [] array, final int index, final int length) { return (short  []) removeRange(array, array.length, new short  [array.length - length] , index, length); }
    public static     int    [] removeRange(final int    [] array, final int index, final int length) { return (int    []) removeRange(array, array.length, new int    [array.length - length] , index, length); }
    public static     long   [] removeRange(final long   [] array, final int index, final int length) { return (long   []) removeRange(array, array.length, new long   [array.length - length] , index, length); }
    public static     float  [] removeRange(final float  [] array, final int index, final int length) { return (float  []) removeRange(array, array.length, new float  [array.length - length] , index, length); }
    public static     double [] removeRange(final double [] array, final int index, final int length) { return (double []) removeRange(array, array.length, new double [array.length - length] , index, length); }

    private static Object removeIndecies(final Object array, final int arrayLength, final Object result, final int[] indecies)
    {
        Arrays.sort(indecies);

        int arrayIndex   = 0;
        int resultIndex  = 0;
        int removeLength = 1;

        for (int i = 0; i < indecies.length; i += removeLength)
        {
            removeLength = 1;
            while(i + removeLength < indecies.length && indecies[i] + removeLength == indecies[i + removeLength]) removeLength++;

            final int length = indecies[i] - arrayIndex;

            untypedCopy(array, arrayIndex, result, resultIndex, length);
            resultIndex += length;
            arrayIndex = indecies[i] + removeLength;
        }
        
        untypedCopy(array, arrayIndex, result, resultIndex, arrayLength - arrayIndex); // copy remaing into result

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T      [] removeIndecies(final T      [] array, final int... indecies) { return (T      []) removeIndecies(array, array.length, array(array.length - indecies.length, array), indecies); }
    public static     boolean[] removeIndecies(final boolean[] array, final int... indecies) { return (boolean[]) removeIndecies(array, array.length, new boolean[array.length - indecies.length] , indecies); }
    public static     byte   [] removeIndecies(final byte   [] array, final int... indecies) { return (byte   []) removeIndecies(array, array.length, new byte   [array.length - indecies.length] , indecies); }
    public static     char   [] removeIndecies(final char   [] array, final int... indecies) { return (char   []) removeIndecies(array, array.length, new char   [array.length - indecies.length] , indecies); }
    public static     short  [] removeIndecies(final short  [] array, final int... indecies) { return (short  []) removeIndecies(array, array.length, new short  [array.length - indecies.length] , indecies); }
    public static     int    [] removeIndecies(final int    [] array, final int... indecies) { return (int    []) removeIndecies(array, array.length, new int    [array.length - indecies.length] , indecies); }
    public static     long   [] removeIndecies(final long   [] array, final int... indecies) { return (long   []) removeIndecies(array, array.length, new long   [array.length - indecies.length] , indecies); }
    public static     float  [] removeIndecies(final float  [] array, final int... indecies) { return (float  []) removeIndecies(array, array.length, new float  [array.length - indecies.length] , indecies); }
    public static     double [] removeIndecies(final double [] array, final int... indecies) { return (double []) removeIndecies(array, array.length, new double [array.length - indecies.length] , indecies); }

    public static <T> void shuffle(final T[] array, final Random random)
    {
        //fisherman yates shuffle
        for (int i = array.length - 1; i > 0; i--)
        {
            final int index = random.randomInt(i + 1);

            final T a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }
    
    public static void shuffle(final boolean[] array, final Random random)
    {
        //fisherman yates shuffle
        for (int i = array.length - 1; i > 0; i--)
        {
            final int index = random.randomInt(i + 1);

            final boolean a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    public static void shuffle(final byte[] array, final Random random)
    {
        //fisherman yates shuffle
        for (int i = array.length - 1; i > 0; i--)
        {
            final int index = random.randomInt(i + 1);

            final byte a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    public static void shuffle(final char[] array, final Random random)
    {
        //fisherman yates shuffle
        for (int i = array.length - 1; i > 0; i--)
        {
            final int index = random.randomInt(i + 1);

            final char a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    public static void shuffle(final short[] array, final Random random)
    {
        //fisherman yates shuffle
        for (int i = array.length - 1; i > 0; i--)
        {
            final int index = random.randomInt(i + 1);

            final short a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    public static void shuffle(final int[] array, final Random random)
    {
        //fisherman yates shuffle
        for (int i = array.length - 1; i > 0; i--)
        {
            final int index = random.randomInt(i + 1);

            final int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    public static void shuffle(final long[] array, final Random random)
    {
        //fisherman yates shuffle
        for (int i = array.length - 1; i > 0; i--)
        {
            final int index = random.randomInt(i + 1);

            final long a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    public static void shuffle(final float[] array, final Random random)
    {
        //fisherman yates shuffle
        for (int i = array.length - 1; i > 0; i--)
        {
            final int index = random.randomInt(i + 1);

            final float a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    public static void shuffle(final double[] array, final Random random)
    {
        //fisherman yates shuffle
        for (int i = array.length - 1; i > 0; i--)
        {
            final int index = random.randomInt(i + 1);

            final double a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    public static <T> void shuffle(final T      [] array) { shuffle(array, Random.RANDOM); }
    public static     void shuffle(final boolean[] array) { shuffle(array, Random.RANDOM); }
    public static     void shuffle(final byte   [] array) { shuffle(array, Random.RANDOM); }
    public static     void shuffle(final char   [] array) { shuffle(array, Random.RANDOM); }
    public static     void shuffle(final short  [] array) { shuffle(array, Random.RANDOM); }
    public static     void shuffle(final int    [] array) { shuffle(array, Random.RANDOM); }
    public static     void shuffle(final long   [] array) { shuffle(array, Random.RANDOM); }
    public static     void shuffle(final float  [] array) { shuffle(array, Random.RANDOM); }
    public static     void shuffle(final double [] array) { shuffle(array, Random.RANDOM); }

    @SafeVarargs
    public static <T> T[] combine(final T[]... arrays)
    {
        T[] result = array(0, arrays);
    
        for (int i = 0; i < arrays.length; i++)
            result = push(result, arrays[i]);
    
        return result;
    }

    public static boolean[] combine(final boolean[]... arrays)
    {
        boolean[] result = new boolean[0];

        for (int i = 0; i < arrays.length; i++)
            result = push(result, arrays[i]);
    
        return result;
    }

    public static byte[] combine(final byte[]... arrays)
    {
        byte[] result = new byte[0];

        for (int i = 0; i < arrays.length; i++)
            result = push(result, arrays[i]);
    
        return result;
    }

    public static char[] combine(final char[]... arrays)
    {
        char[] result = new char[0];

        for (int i = 0; i < arrays.length; i++)
            result = push(result, arrays[i]);
    
        return result;
    }

    public static short[] combine(final short[]... arrays)
    {
        short[] result = new short[0];

        for (int i = 0; i < arrays.length; i++)
            result = push(result, arrays[i]);
    
        return result;
    }

    public static int[] combine(final int[]... arrays)
    {
        int[] result = new int[0];

        for (int i = 0; i < arrays.length; i++)
            result = push(result, arrays[i]);
    
        return result;
    }

    public static long[] combine(final long[]... arrays)
    {
        long[] result = new long[0];

        for (int i = 0; i < arrays.length; i++)
            result = push(result, arrays[i]);
    
        return result;
    }

    public static float[] combine(final float[]... arrays)
    {
        float[] result = new float[0];

        for (int i = 0; i < arrays.length; i++)
            result = push(result, arrays[i]);
    
        return result;
    }

    public static double[] combine(final double[]... arrays)
    {
        double[] result = new double[0];

        for (int i = 0; i < arrays.length; i++)
            result = push(result, arrays[i]);
    
        return result;
    }

    private static Object resize(final Object array, final int arrayLength, final Object result, final int resultLength)
    {
        untypedCopy(array, 0, result, 0, resultLength > arrayLength ? arrayLength : resultLength);

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T      [] resize(final T      [] array, final int newLength) { return (T      []) resize(array, array.length, array(newLength, array), newLength); }
    public static     boolean[] resize(final boolean[] array, final int newLength) { return (boolean[]) resize(array, array.length, new boolean[newLength] , newLength); }
    public static     byte   [] resize(final byte   [] array, final int newLength) { return (byte   []) resize(array, array.length, new byte   [newLength] , newLength); }
    public static     char   [] resize(final char   [] array, final int newLength) { return (char   []) resize(array, array.length, new char   [newLength] , newLength); }
    public static     short  [] resize(final short  [] array, final int newLength) { return (short  []) resize(array, array.length, new short  [newLength] , newLength); }
    public static     int    [] resize(final int    [] array, final int newLength) { return (int    []) resize(array, array.length, new int    [newLength] , newLength); }
    public static     long   [] resize(final long   [] array, final int newLength) { return (long   []) resize(array, array.length, new long   [newLength] , newLength); }
    public static     float  [] resize(final float  [] array, final int newLength) { return (float  []) resize(array, array.length, new float  [newLength] , newLength); }
    public static     double [] resize(final double [] array, final int newLength) { return (double []) resize(array, array.length, new double [newLength] , newLength); }

    public static <T> int indexOf(final T      [] array, final T       value) { for (int i = 0; i < array.length; i++) { if (array[i] == value) return i; } return -1; }
    public static     int indexOf(final boolean[] array, final boolean value) { for (int i = 0; i < array.length; i++) { if (array[i] == value) return i; } return -1; }
    public static     int indexOf(final byte   [] array, final byte    value) { for (int i = 0; i < array.length; i++) { if (array[i] == value) return i; } return -1; }
    public static     int indexOf(final char   [] array, final char    value) { for (int i = 0; i < array.length; i++) { if (array[i] == value) return i; } return -1; }
    public static     int indexOf(final short  [] array, final short   value) { for (int i = 0; i < array.length; i++) { if (array[i] == value) return i; } return -1; }
    public static     int indexOf(final int    [] array, final int     value) { for (int i = 0; i < array.length; i++) { if (array[i] == value) return i; } return -1; }
    public static     int indexOf(final long   [] array, final long    value) { for (int i = 0; i < array.length; i++) { if (array[i] == value) return i; } return -1; }
    public static     int indexOf(final float  [] array, final float   value) { for (int i = 0; i < array.length; i++) { if (array[i] == value) return i; } return -1; }
    public static     int indexOf(final double [] array, final double  value) { for (int i = 0; i < array.length; i++) { if (array[i] == value) return i; } return -1; }

    @SafeVarargs
    public static <T> int[] indeciesOf(final T[] array, final T... values)
    {
        final int[] indecies = new int[values.length];

        for (int i = 0; i < indecies.length; i++)
            indecies[i] = indexOf(array, values[i]);

        return indecies;
    }

    public static int[] indeciesOf(final boolean[] array, final boolean... values)
    {
        final int[] indecies = new int[values.length];

        for (int i = 0; i < indecies.length; i++)
            indecies[i] = indexOf(array, values[i]);

        return indecies;
    }

    public static int[] indeciesOf(final byte[] array, final byte... values)
    {
        final int[] indecies = new int[values.length];

        for (int i = 0; i < indecies.length; i++)
            indecies[i] = indexOf(array, values[i]);

        return indecies;
    }

    public static int[] indeciesOf(final char[] array, final char... values)
    {
        final int[] indecies = new int[values.length];

        for (int i = 0; i < indecies.length; i++)
            indecies[i] = indexOf(array, values[i]);

        return indecies;
    }

    public static int[] indeciesOf(final short[] array, final short... values)
    {
        final int[] indecies = new int[values.length];

        for (int i = 0; i < indecies.length; i++)
            indecies[i] = indexOf(array, values[i]);

        return indecies;
    }

    public static int[] indeciesOf(final int[] array, final int... values)
    {
        final int[] indecies = new int[values.length];

        for (int i = 0; i < indecies.length; i++)
            indecies[i] = indexOf(array, values[i]);

        return indecies;
    }

    public static int[] indeciesOf(final long[] array, final long... values)
    {
        final int[] indecies = new int[values.length];

        for (int i = 0; i < indecies.length; i++)
            indecies[i] = indexOf(array, values[i]);

        return indecies;
    }

    public static int[] indeciesOf(final float[] array, final float... values)
    {
        final int[] indecies = new int[values.length];

        for (int i = 0; i < indecies.length; i++)
            indecies[i] = indexOf(array, values[i]);

        return indecies;
    }

    public static int[] indeciesOf(final double[] array, final double... values)
    {
        final int[] indecies = new int[values.length];

        for (int i = 0; i < indecies.length; i++)
            indecies[i] = indexOf(array, values[i]);

        return indecies;
    }

    public static <T> boolean contains(final T      [] array, final T       value) { return indexOf(array, value) != -1; }
    public static     boolean contains(final boolean[] array, final boolean value) { return indexOf(array, value) != -1; }
    public static     boolean contains(final byte   [] array, final byte    value) { return indexOf(array, value) != -1; }
    public static     boolean contains(final char   [] array, final char    value) { return indexOf(array, value) != -1; }
    public static     boolean contains(final short  [] array, final short   value) { return indexOf(array, value) != -1; }
    public static     boolean contains(final int    [] array, final int     value) { return indexOf(array, value) != -1; }
    public static     boolean contains(final long   [] array, final long    value) { return indexOf(array, value) != -1; }
    public static     boolean contains(final float  [] array, final float   value) { return indexOf(array, value) != -1; }
    public static     boolean contains(final double [] array, final double  value) { return indexOf(array, value) != -1; }

    public static <T> T      [] sort(final T      [] array) { return sort(array, 0, array.length - 1); }
    //public static     boolean[] sort(final boolean[] array) { return sort(array, 0, array.length - 1); }
    public static     byte   [] sort(final byte   [] array) { return sort(array, 0, array.length - 1); }
    public static     char   [] sort(final char   [] array) { return sort(array, 0, array.length - 1); }
    public static     short  [] sort(final short  [] array) { return sort(array, 0, array.length - 1); }
    public static     int    [] sort(final int    [] array) { return sort(array, 0, array.length - 1); }
    public static     long   [] sort(final long   [] array) { return sort(array, 0, array.length - 1); }
    public static     float  [] sort(final float  [] array) { return sort(array, 0, array.length - 1); }
    public static     double [] sort(final double [] array) { return sort(array, 0, array.length - 1); }

    public static <T> T      [] sort(final T      [] array, final int start, final int end) { final T      [] result = clone(array); Arrays.sort(result, start, end); return result; }
    //public static     boolean[] sort(final boolean[] array, final int start, final int end) { final boolean[] result = clone(array); Arrays.sort(result, start, end); return result; }
    public static     byte   [] sort(final byte   [] array, final int start, final int end) { final byte   [] result = clone(array); Arrays.sort(result, start, end); return result; }
    public static     char   [] sort(final char   [] array, final int start, final int end) { final char   [] result = clone(array); Arrays.sort(result, start, end); return result; }
    public static     short  [] sort(final short  [] array, final int start, final int end) { final short  [] result = clone(array); Arrays.sort(result, start, end); return result; }
    public static     int    [] sort(final int    [] array, final int start, final int end) { final int    [] result = clone(array); Arrays.sort(result, start, end); return result; }
    public static     long   [] sort(final long   [] array, final int start, final int end) { final long   [] result = clone(array); Arrays.sort(result, start, end); return result; }
    public static     float  [] sort(final float  [] array, final int start, final int end) { final float  [] result = clone(array); Arrays.sort(result, start, end); return result; }
    public static     double [] sort(final double [] array, final int start, final int end) { final double [] result = clone(array); Arrays.sort(result, start, end); return result; }

    public static <T> void reverse(final T      [] array) { reverse(array, 0, array.length - 1); }
    public static     void reverse(final boolean[] array) { reverse(array, 0, array.length - 1); }
    public static     void reverse(final byte   [] array) { reverse(array, 0, array.length - 1); }
    public static     void reverse(final char   [] array) { reverse(array, 0, array.length - 1); }
    public static     void reverse(final short  [] array) { reverse(array, 0, array.length - 1); }
    public static     void reverse(final int    [] array) { reverse(array, 0, array.length - 1); }
    public static     void reverse(final long   [] array) { reverse(array, 0, array.length - 1); }
    public static     void reverse(final float  [] array) { reverse(array, 0, array.length - 1); }
    public static     void reverse(final double [] array) { reverse(array, 0, array.length - 1); }

    public static <T> void reverse(final T[] array, int start, int end)
    {
        for (; start < end; start++, end--)
        {
            final T temp = array[start];
            array[start] = array[end];
            array[end  ] = temp;
        }
    }

    public static void reverse(final boolean[] array, int start, int end)
    {
        for (; start < end; start++, end--)
        {
            final boolean temp = array[start];
            array[start] = array[end];
            array[end  ] = temp;
        }
    }

    public static void reverse(final byte[] array, int start, int end)
    {
        for (; start < end; start++, end--)
        {
            final byte temp = array[start];
            array[start] = array[end];
            array[end  ] = temp;
        }
    }

    public static void reverse(final char[] array, int start, int end)
    {
        for (; start < end; start++, end--)
        {
            final char temp = array[start];
            array[start] = array[end];
            array[end  ] = temp;
        }
    }

    public static void reverse(final short[] array, int start, int end)
    {
        for (; start < end; start++, end--)
        {
            final short temp = array[start];
            array[start] = array[end];
            array[end  ] = temp;
        }
    }

    public static void reverse(final int[] array, int start, int end)
    {
        for (; start < end; start++, end--)
        {
            final int temp = array[start];
            array[start] = array[end];
            array[end  ] = temp;
        }
    }

    public static void reverse(final long[] array, int start, int end)
    {
        for (; start < end; start++, end--)
        {
            final long temp = array[start];
            array[start] = array[end];
            array[end  ] = temp;
        }
    }

    public static void reverse(final float[] array, int start, int end)
    {
        for (; start < end; start++, end--)
        {
            final float temp = array[start];
            array[start] = array[end];
            array[end  ] = temp;
        }
    }

    public static void reverse(final double[] array, int start, int end)
    {
        for (; start < end; start++, end--)
        {
            final double temp = array[start];
            array[start] = array[end];
            array[end  ] = temp;
        }
    }

    private static final int SMALL_ARRAY_LENGTH = 64;

    public static <T> void fill(final T      [] array, final T       value) { fill(array, value, 0, array.length - 1); }
    public static     void fill(final boolean[] array, final boolean value) { fill(array, value, 0, array.length - 1); }
    public static     void fill(final byte   [] array, final byte    value) { fill(array, value, 0, array.length - 1); }
    public static     void fill(final char   [] array, final char    value) { fill(array, value, 0, array.length - 1); }
    public static     void fill(final short  [] array, final short   value) { fill(array, value, 0, array.length - 1); }
    public static     void fill(final int    [] array, final int     value) { fill(array, value, 0, array.length - 1); }
    public static     void fill(final long   [] array, final long    value) { fill(array, value, 0, array.length - 1); }
    public static     void fill(final float  [] array, final float   value) { fill(array, value, 0, array.length - 1); }
    public static     void fill(final double [] array, final double  value) { fill(array, value, 0, array.length - 1); }

    public static <T> void fill(final T[] array, final T value, final int start, final int end)
    {
        final int left    = start < end ? start : end,
                  right   = start > end ? start : end,
                  length  = right - left + 1,
                  length2 = length < SMALL_ARRAY_LENGTH ? length : SMALL_ARRAY_LENGTH;

        //This is faster than arrayCopy for small lengths
        for (int i = start; i < length2; i++)
            array[i] = value;

        //This is faster than for loop for big lengths
        for (int i = SMALL_ARRAY_LENGTH; i < length; i <<= 1)
            copy(array, left, array, i + left, length < i << 1 ? length - i : i);
    }

    public static void fill(final boolean[] array, final boolean value, final int start, final int end)
    {
        final int left    = start < end ? start : end,
                  right   = start > end ? start : end,
                  length  = right - left + 1,
                  length2 = length < SMALL_ARRAY_LENGTH ? length : SMALL_ARRAY_LENGTH;

        //This is faster than arrayCopy for small lengths
        for (int i = start; i < length2; i++)
            array[i] = value;

        //This is faster than for loop for big lengths
        for (int i = SMALL_ARRAY_LENGTH; i < length; i <<= 1)
            copy(array, left, array, i + left, length < i << 1 ? length - i : i);
    }
    
    public static void fill(final byte[] array, final byte value, final int start, final int end)
    {
        final int left    = start < end ? start : end,
                  right   = start > end ? start : end,
                  length  = right - left + 1,
                  length2 = length < SMALL_ARRAY_LENGTH ? length : SMALL_ARRAY_LENGTH;

        //This is faster than arrayCopy for small lengths
        for (int i = start; i < length2; i++)
            array[i] = value;

        //This is faster than for loop for big lengths
        for (int i = SMALL_ARRAY_LENGTH; i < length; i <<= 1)
            copy(array, left, array, i + left, length < i << 1 ? length - i : i);
    }

    public static void fill(final char[] array, final char value, final int start, final int end)
    {
        final int left    = start < end ? start : end,
                  right   = start > end ? start : end,
                  length  = right - left + 1,
                  length2 = length < SMALL_ARRAY_LENGTH ? length : SMALL_ARRAY_LENGTH;

        //This is faster than arrayCopy for small lengths
        for (int i = start; i < length2; i++)
            array[i] = value;

        //This is faster than for loop for big lengths
        for (int i = SMALL_ARRAY_LENGTH; i < length; i <<= 1)
            copy(array, left, array, i + left, length < i << 1 ? length - i : i);
    }

    public static void fill(final short[] array, final short value, final int start, final int end)
    {
        final int left    = start < end ? start : end,
                  right   = start > end ? start : end,
                  length  = right - left + 1,
                  length2 = length < SMALL_ARRAY_LENGTH ? length : SMALL_ARRAY_LENGTH;

        //This is faster than arrayCopy for small lengths
        for (int i = start; i < length2; i++)
            array[i] = value;

        //This is faster than for loop for big lengths
        for (int i = SMALL_ARRAY_LENGTH; i < length; i <<= 1)
            copy(array, left, array, i + left, length < i << 1 ? length - i : i);
    }

    public static void fill(final int[] array, final int value, final int start, final int end)
    {
        final int left    = start < end ? start : end,
                  right   = start > end ? start : end,
                  length  = right - left + 1,
                  length2 = length < SMALL_ARRAY_LENGTH ? length : SMALL_ARRAY_LENGTH;

        //This is faster than arrayCopy for small lengths
        for (int i = start; i < length2; i++)
            array[i] = value;

        //This is faster than for loop for big lengths
        for (int i = SMALL_ARRAY_LENGTH; i < length; i <<= 1)
            copy(array, left, array, i + left, length < i << 1 ? length - i : i);
    }

    public static void fill(final long[] array, final long value, final int start, final int end)
    {
        final int left    = start < end ? start : end,
                  right   = start > end ? start : end,
                  length  = right - left + 1,
                  length2 = length < SMALL_ARRAY_LENGTH ? length : SMALL_ARRAY_LENGTH;

        //This is faster than arrayCopy for small lengths
        for (int i = start; i < length2; i++)
            array[i] = value;

        //This is faster than for loop for big lengths
        for (int i = SMALL_ARRAY_LENGTH; i < length; i <<= 1)
            copy(array, left, array, i + left, length < i << 1 ? length - i : i);
    }

    public static void fill(final float[] array, final float value, final int start, final int end)
    {
        final int left    = start < end ? start : end,
                  right   = start > end ? start : end,
                  length  = right - left + 1,
                  length2 = length < SMALL_ARRAY_LENGTH ? length : SMALL_ARRAY_LENGTH;

        //This is faster than arrayCopy for small lengths
        for (int i = start; i < length2; i++)
            array[i] = value;

        //This is faster than for loop for big lengths
        for (int i = SMALL_ARRAY_LENGTH; i < length; i <<= 1)
            copy(array, left, array, i + left, length < i << 1 ? length - i : i);
    }

    public static void fill(final double[] array, final double value, final int start, final int end)
    {
        final int left    = start < end ? start : end,
                  right   = start > end ? start : end,
                  length  = right - left + 1,
                  length2 = length < SMALL_ARRAY_LENGTH ? length : SMALL_ARRAY_LENGTH;

        //This is faster than arrayCopy for small lengths
        for (int i = start; i < length2; i++)
            array[i] = value;

        //This is faster than for loop for big lengths
        for (int i = SMALL_ARRAY_LENGTH; i < length; i <<= 1)
            copy(array, left, array, i + left, length < i << 1 ? length - i : i);
    }

    public static void untypedCopy(final Object source, final int sourceOffset, final Object destination, final int destinationOffset, final int length) { System.arraycopy(source, sourceOffset, destination, destinationOffset, length); }
    public static void untypedCopy(final Object source, final Object destination, final int length) { untypedCopy(source, 0, destination, 0, length); }

    public static <T> void copy(final T      [] source, final int sourceOffset, final T      [] destination, final int destinationOffset, final int length) { System.arraycopy(source, sourceOffset, destination, destinationOffset, length); }
    public static     void copy(final boolean[] source, final int sourceOffset, final boolean[] destination, final int destinationOffset, final int length) { System.arraycopy(source, sourceOffset, destination, destinationOffset, length); }
    public static     void copy(final byte   [] source, final int sourceOffset, final byte   [] destination, final int destinationOffset, final int length) { System.arraycopy(source, sourceOffset, destination, destinationOffset, length); }
    public static     void copy(final char   [] source, final int sourceOffset, final char   [] destination, final int destinationOffset, final int length) { System.arraycopy(source, sourceOffset, destination, destinationOffset, length); }
    public static     void copy(final short  [] source, final int sourceOffset, final short  [] destination, final int destinationOffset, final int length) { System.arraycopy(source, sourceOffset, destination, destinationOffset, length); }
    public static     void copy(final int    [] source, final int sourceOffset, final int    [] destination, final int destinationOffset, final int length) { System.arraycopy(source, sourceOffset, destination, destinationOffset, length); }
    public static     void copy(final long   [] source, final int sourceOffset, final long   [] destination, final int destinationOffset, final int length) { System.arraycopy(source, sourceOffset, destination, destinationOffset, length); }
    public static     void copy(final float  [] source, final int sourceOffset, final float  [] destination, final int destinationOffset, final int length) { System.arraycopy(source, sourceOffset, destination, destinationOffset, length); }
    public static     void copy(final double [] source, final int sourceOffset, final double [] destination, final int destinationOffset, final int length) { System.arraycopy(source, sourceOffset, destination, destinationOffset, length); }
    
    public static <T> void copy(final T      [] source, final T      [] destination, final int length) { copy(source, 0, destination, 0, length); }
    public static     void copy(final boolean[] source, final boolean[] destination, final int length) { copy(source, 0, destination, 0, length); }
    public static     void copy(final byte   [] source, final byte   [] destination, final int length) { copy(source, 0, destination, 0, length); }
    public static     void copy(final char   [] source, final char   [] destination, final int length) { copy(source, 0, destination, 0, length); }
    public static     void copy(final short  [] source, final short  [] destination, final int length) { copy(source, 0, destination, 0, length); }
    public static     void copy(final int    [] source, final int    [] destination, final int length) { copy(source, 0, destination, 0, length); }
    public static     void copy(final long   [] source, final long   [] destination, final int length) { copy(source, 0, destination, 0, length); }
    public static     void copy(final float  [] source, final float  [] destination, final int length) { copy(source, 0, destination, 0, length); }
    public static     void copy(final double [] source, final double [] destination, final int length) { copy(source, 0, destination, 0, length); }

    public static <T> void copy(final T      [] source, final T      [] destination) { copy(source, 0, destination, 0, destination.length); }
    public static     void copy(final boolean[] source, final boolean[] destination) { copy(source, 0, destination, 0, destination.length); }
    public static     void copy(final byte   [] source, final byte   [] destination) { copy(source, 0, destination, 0, destination.length); }
    public static     void copy(final char   [] source, final char   [] destination) { copy(source, 0, destination, 0, destination.length); }
    public static     void copy(final short  [] source, final short  [] destination) { copy(source, 0, destination, 0, destination.length); }
    public static     void copy(final int    [] source, final int    [] destination) { copy(source, 0, destination, 0, destination.length); }
    public static     void copy(final long   [] source, final long   [] destination) { copy(source, 0, destination, 0, destination.length); }
    public static     void copy(final float  [] source, final float  [] destination) { copy(source, 0, destination, 0, destination.length); }
    public static     void copy(final double [] source, final double [] destination) { copy(source, 0, destination, 0, destination.length); }

    public static <T> T      [] clone(final T      [] array) { return clone(array, 0, array.length); }
    public static     boolean[] clone(final boolean[] array) { return clone(array, 0, array.length); }
    public static     byte   [] clone(final byte   [] array) { return clone(array, 0, array.length); }
    public static     char   [] clone(final char   [] array) { return clone(array, 0, array.length); }
    public static     short  [] clone(final short  [] array) { return clone(array, 0, array.length); }
    public static     int    [] clone(final int    [] array) { return clone(array, 0, array.length); }
    public static     long   [] clone(final long   [] array) { return clone(array, 0, array.length); }
    public static     float  [] clone(final float  [] array) { return clone(array, 0, array.length); }
    public static     double [] clone(final double [] array) { return clone(array, 0, array.length); }

    private static Object clone(final Object array, final Object result, final int index, final int length) { untypedCopy(array, index, result, 0, length); return result; }

    @SuppressWarnings("unchecked")
    public static <T> T      [] clone(final T      [] array, final int start, final int end) { final int length = end - start; return (T      []) clone(array, array(length, array), start, length); }
    public static     boolean[] clone(final boolean[] array, final int start, final int end) { final int length = end - start; return (boolean[]) clone(array, new boolean[length] , start, length); }
    public static     byte   [] clone(final byte   [] array, final int start, final int end) { final int length = end - start; return (byte   []) clone(array, new byte   [length] , start, length); }
    public static     char   [] clone(final char   [] array, final int start, final int end) { final int length = end - start; return (char   []) clone(array, new char   [length] , start, length); }
    public static     short  [] clone(final short  [] array, final int start, final int end) { final int length = end - start; return (short  []) clone(array, new short  [length] , start, length); }
    public static     int    [] clone(final int    [] array, final int start, final int end) { final int length = end - start; return (int    []) clone(array, new int    [length] , start, length); }
    public static     long   [] clone(final long   [] array, final int start, final int end) { final int length = end - start; return (long   []) clone(array, new long   [length] , start, length); }
    public static     float  [] clone(final float  [] array, final int start, final int end) { final int length = end - start; return (float  []) clone(array, new float  [length] , start, length); }
    public static     double [] clone(final double [] array, final int start, final int end) { final int length = end - start; return (double []) clone(array, new double [length] , start, length); }

    //Just a way to get and set values from Arrays, without reflection, perhaps faster than Array.get and Array.set
    public static Object get(Object array, int index)
    {
        final Object[] destination = { null };
    
        untypedCopy(array, index, destination, 0, 1);

        return destination[0];
    }
    
    public static void set(Object array, Object value, int index)
    {
        final Object[] source = { value };

        untypedCopy(source, 0, array, index, 1);
    }

    public static <T> void forEach(final T[] array, GenericAction<T> action)
    {
        for (int i = 0; i < array.length; i++) action.run(array[i]);
    }

    public static void forEach(final boolean[] array, BooleanAction action)
    {
        for (int i = 0; i < array.length; i++) action.run(array[i]);
    }

    public static void forEach(final byte[] array, ByteAction action)
    {
        for (int i = 0; i < array.length; i++) action.run(array[i]);
    }

    public static void forEach(final char[] array, CharAction action)
    {
        for (int i = 0; i < array.length; i++) action.run(array[i]);
    }

    public static void forEach(final short[] array, ShortAction action)
    {
        for (int i = 0; i < array.length; i++) action.run(array[i]);
    }

    public static void forEach(final int[] array, IntAction action)
    {
        for (int i = 0; i < array.length; i++) action.run(array[i]);
    }

    public static void forEach(final long[] array, LongAction action)
    {
        for (int i = 0; i < array.length; i++) action.run(array[i]);
    }

    public static void forEach(final float[] array, FloatAction action)
    {
        for (int i = 0; i < array.length; i++) action.run(array[i]);
    }

    public static void forEach(final double[] array, DoubleAction action)
    {
        for (int i = 0; i < array.length; i++) action.run(array[i]);
    }

    public static <T> String toString(final T      [] array) { return toString(array, array.length); }
    public static     String toString(final boolean[] array) { return toString(array, array.length); }
    public static     String toString(final byte   [] array) { return toString(array, array.length); }
    public static     String toString(final char   [] array) { return toString(array, array.length); }
    public static     String toString(final short  [] array) { return toString(array, array.length); }
    public static     String toString(final int    [] array) { return toString(array, array.length); }
    public static     String toString(final long   [] array) { return toString(array, array.length); }
    public static     String toString(final float  [] array) { return toString(array, array.length); }
    public static     String toString(final double [] array) { return toString(array, array.length); }

    private static String toString(final String innerPart, final int length)
    {
        return "[" + innerPart + " | " + "length: " + length + "]";
    }

    public static <T> String toString(final T[] array, int depth)
    {
        if (depth > array.length) return toString(array, array.length);
        if (depth == 0) return toString("", array.length);

        String innerPart = "";

        for (int i = 0; i < depth - 1; i++) innerPart += array[i].toString() + ", ";

        innerPart += array[depth - 1] + (depth < array.length ? ", ..." : "");

        return toString(innerPart, array.length);
    }

    public static String toString(final boolean[] array, int depth)
    {
        if (depth > array.length) return toString(array, array.length);
        if (depth == 0) return toString("", array.length);

        String innerPart = "";

        for (int i = 0; i < depth - 1; i++) innerPart += array[i] + ", ";

        innerPart += array[depth - 1] + (depth < array.length ? ", ..." : "");

        return toString(innerPart, array.length);
    }

    public static String toString(final byte[] array, int depth)
    {
        if (depth > array.length) return toString(array, array.length);
        if (depth == 0) return toString("", array.length);

        String innerPart = "";

        for (int i = 0; i < depth - 1; i++) innerPart += array[i] + ", ";

        innerPart += array[depth - 1] + (depth < array.length ? ", ..." : "");

        return toString(innerPart, array.length);
    }

    public static String toString(final char[] array, int depth)
    {
        if (depth > array.length) return toString(array, array.length);
        if (depth == 0) return toString("", array.length);

        String innerPart = "";

        for (int i = 0; i < depth - 1; i++) innerPart += array[i] + ", ";

        innerPart += array[depth - 1] + (depth < array.length ? ", ..." : "");

        return toString(innerPart, array.length);
    }
    
    public static String toString(final short[] array, int depth)
    {
        if (depth > array.length) return toString(array, array.length);
        if (depth == 0) return toString("", array.length);

        String innerPart = "";

        for (int i = 0; i < depth - 1; i++) innerPart += array[i] + ", ";

        innerPart += array[depth - 1] + (depth < array.length ? ", ..." : "");

        return toString(innerPart, array.length);
    }

    public static String toString(final int[] array, int depth)
    {
        if (depth > array.length) return toString(array, array.length);
        if (depth == 0) return toString("", array.length);

        String innerPart = "";

        for (int i = 0; i < depth - 1; i++) innerPart += array[i] + ", ";

        innerPart += array[depth - 1] + (depth < array.length ? ", ..." : "");

        return toString(innerPart, array.length);
    }

    public static String toString(final long[] array, int depth)
    {
        if (depth > array.length) return toString(array, array.length);
        if (depth == 0) return toString("", array.length);

        String innerPart = "";

        for (int i = 0; i < depth - 1; i++) innerPart += array[i] + ", ";

        innerPart += array[depth - 1] + (depth < array.length ? ", ..." : "");

        return toString(innerPart, array.length);
    }

    public static String toString(final float[] array, int depth)
    {
        if (depth > array.length) return toString(array, array.length);
        if (depth == 0) return toString("", array.length);

        String innerPart = "";

        for (int i = 0; i < depth - 1; i++) innerPart += array[i] + ", ";

        innerPart += array[depth - 1] + (depth < array.length ? ", ..." : "");

        return toString(innerPart, array.length);
    }

    public static String toString(final double[] array, int depth)
    {
        if (depth > array.length) return toString(array, array.length);
        if (depth == 0) return toString("", array.length);

        String innerPart = "";

        for (int i = 0; i < depth - 1; i++) innerPart += array[i] + ", ";

        innerPart += array[depth - 1] + (depth < array.length ? ", ..." : "");

        return toString(innerPart, array.length);
    }

    // FIXME: The method newInstance(Class<?>, int) from the type Array refers to the missing type NegativeArraySizeExceptionJava(67108984)
    @SuppressWarnings("unchecked") public static <T> T[] newInstance(final Class<T> type, final int length) { return (T[]) Array.newInstance(type, length); }

    @SuppressWarnings("unchecked") public static <T> Class<T> componentTypeOf(final T[] array) { return (Class<T>) array.getClass().getComponentType(); }
    @SuppressWarnings("unchecked") public static <T> Class<T> componentTypeOf(final T[][] array) { return (Class<T>) array.getClass().getComponentType().getComponentType(); }

    @SafeVarargs public static <T> T[] array(final int length, final T... junk) { return newInstance(componentTypeOf(junk), length); }
    @SafeVarargs public static <T> T[] array(final int length, final T[]... junk) { return newInstance(componentTypeOf(junk), length); }

    public static <T> T[] filter(final T[] array, final GenericFilter<T> filter)
    {
        final T[] filtered = array(array.length, array);

        int length = 0;

        for (int i = 0; i < array.length; i++)
            if (filter.filter(array[i]))
                filtered[length++] = array[i];

        return resize(filtered, length);
    }

    public static boolean[] filter(final boolean[] array, final BooleanFilter filter)
    {
        final boolean[] filtered = new boolean[array.length];

        int length = 0;

        for (int i = 0; i < array.length; i++)
            if (filter.filter(array[i]))
                filtered[length++] = array[i];

        return resize(filtered, length);
    }

    public static byte[] filter(final byte[] array, final ByteFilter filter)
    {
        final byte[] filtered = new byte[array.length];

        int length = 0;

        for (int i = 0; i < array.length; i++)
            if (filter.filter(array[i]))
                filtered[length++] = array[i];

        return resize(filtered, length);
    }
    
    public static char[] filter(final char[] array, final CharFilter filter)
    {
        final char[] filtered = new char[array.length];

        int length = 0;

        for (int i = 0; i < array.length; i++)
            if (filter.filter(array[i]))
                filtered[length++] = array[i];

        return resize(filtered, length);
    }

    public static short[] filter(final short[] array, final ShortFilter filter)
    {
        final short[] filtered = new short[array.length];

        int length = 0;

        for (int i = 0; i < array.length; i++)
            if (filter.filter(array[i]))
                filtered[length++] = array[i];

        return resize(filtered, length);
    }

    public static int[] filter(final int[] array, final IntFilter filter)
    {
        final int[] filtered = new int[array.length];

        int length = 0;

        for (int i = 0; i < array.length; i++)
            if (filter.filter(array[i]))
                filtered[length++] = array[i];

        return resize(filtered, length);
    }

    public static long[] filter(final long[] array, final LongFilter filter)
    {
        final long[] filtered = new long[array.length];

        int length = 0;

        for (int i = 0; i < array.length; i++)
            if (filter.filter(array[i]))
                filtered[length++] = array[i];

        return resize(filtered, length);
    }

    public static float[] filter(final float[] array, final FloatFilter filter)
    {
        final float[] filtered = new float[array.length];

        int length = 0;

        for (int i = 0; i < array.length; i++)
            if (filter.filter(array[i]))
                filtered[length++] = array[i];

        return resize(filtered, length);
    }

    public static double[] filter(final double[] array, final DoubleFilter filter)
    {
        final double[] filtered = new double[array.length];

        int length = 0;

        for (int i = 0; i < array.length; i++)
            if (filter.filter(array[i]))
                filtered[length++] = array[i];

        return resize(filtered, length);
    }
    
    @SafeVarargs
    public static <T, U> U[] map(final T[] array, final GenericMapper<T, U> mapper, final U... junk)
    {
        final U[] result = ArrayUtils.newInstance(ArrayUtils.componentTypeOf(junk), array.length);

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static <T> boolean[] map(final T[] array, final GenericToBooleanMapper<T> mapper)
    {
        final boolean[] result = new boolean[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static <T> byte[] map(final T[] array, final GenericToByteMapper<T> mapper)
    {
        final byte[] result = new byte[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static <T> char[] map(final T[] array, final GenericToCharMapper<T> mapper)
    {
        final char[] result = new char[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static <T> short[] map(final T[] array, final GenericToShortMapper<T> mapper)
    {
        final short[] result = new short[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static <T> int[] map(final T[] array, final GenericToIntMapper<T> mapper)
    {
        final int[] result = new int[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static <T> long[] map(final T[] array, final GenericToLongMapper<T> mapper)
    {
        final long[] result = new long[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static <T> float[] map(final T[] array, final GenericToFloatMapper<T> mapper)
    {
        final float[] result = new float[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static <T> double[] map(final T[] array, final GenericToDoubleMapper<T> mapper)
    {
        final double[] result = new double[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    @SafeVarargs
    public static <T> T[] map(final boolean[] array, final BooleanToGenericMapper<T> mapper, T... junk)
    {
        final T[] result = ArrayUtils.newInstance(ArrayUtils.componentTypeOf(junk), array.length);

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    @SafeVarargs
    public static <T> T[] map(final byte[] array, final ByteToGenericMapper<T> mapper, T... junk)
    {
        final T[] result = ArrayUtils.newInstance(ArrayUtils.componentTypeOf(junk), array.length);

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    @SafeVarargs
    public static <T> T[] map(final char[] array, final CharToGenericMapper<T> mapper, T... junk)
    {
        final T[] result = ArrayUtils.newInstance(ArrayUtils.componentTypeOf(junk), array.length);

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    @SafeVarargs
    public static <T> T[] map(final short[] array, final ShortToGenericMapper<T> mapper, T... junk)
    {
        final T[] result = ArrayUtils.newInstance(ArrayUtils.componentTypeOf(junk), array.length);

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    @SafeVarargs
    public static <T> T[] map(final int[] array, final IntToGenericMapper<T> mapper, T... junk)
    {
        final T[] result = ArrayUtils.newInstance(ArrayUtils.componentTypeOf(junk), array.length);

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    @SafeVarargs
    public static <T> T[] map(final long[] array, final LongToGenericMapper<T> mapper, T... junk)
    {
        final T[] result = ArrayUtils.newInstance(ArrayUtils.componentTypeOf(junk), array.length);

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    @SafeVarargs
    public static <T> T[] map(final float[] array, final FlaotToGenericMapper<T> mapper, T... junk)
    {
        final T[] result = ArrayUtils.newInstance(ArrayUtils.componentTypeOf(junk), array.length);

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    @SafeVarargs
    public static <T> T[] map(final double[] array, final DoubleToGenericMapper<T> mapper, T... junk)
    {
        final T[] result = ArrayUtils.newInstance(ArrayUtils.componentTypeOf(junk), array.length);

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static boolean[] map(final boolean[] array, final BooleanToBooleanMapper mapper)
    {
        final boolean[] result = new boolean[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static byte[] map(final boolean[] array, final BooleanToByteMapper mapper)
    {
        final byte[] result = new byte[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static char[] map(final boolean[] array, final BooleanToCharMapper mapper)
    {
        final char[] result = new char[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static short[] map(final boolean[] array, final BooleanToShortMapper mapper)
    {
        final short[] result = new short[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static int[] map(final boolean[] array, final BooleanToIntMapper mapper)
    {
        final int[] result = new int[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static long[] map(final boolean[] array, final BooleanToLongMapper mapper)
    {
        final long[] result = new long[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static float[] map(final boolean[] array, final BooleanToFloatMapper mapper)
    {
        final float[] result = new float[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static double[] map(final boolean[] array, final BooleanToDoubleMapper mapper)
    {
        final double[] result = new double[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static boolean[] map(final byte[] array, final ByteToBooleanMapper mapper)
    {
        final boolean[] result = new boolean[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static byte[] map(final byte[] array, final ByteToByteMapper mapper)
    {
        final byte[] result = new byte[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static char[] map(final byte[] array, final ByteToCharMapper mapper)
    {
        final char[] result = new char[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static short[] map(final byte[] array, final ByteToShortMapper mapper)
    {
        final short[] result = new short[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static int[] map(final byte[] array, final ByteToIntMapper mapper)
    {
        final int[] result = new int[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static long[] map(final byte[] array, final ByteToLongMapper mapper)
    {
        final long[] result = new long[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static float[] map(final byte[] array, final ByteToFloatMapper mapper)
    {
        final float[] result = new float[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static double[] map(final byte[] array, final ByteToDoubleMapper mapper)
    {
        final double[] result = new double[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static boolean[] map(final char[] array, final CharToBooleanMapper mapper)
    {
        final boolean[] result = new boolean[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static byte[] map(final char[] array, final CharToByteMapper mapper)
    {
        final byte[] result = new byte[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static char[] map(final char[] array, final CharToCharMapper mapper)
    {
        final char[] result = new char[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static short[] map(final char[] array, final CharToShortMapper mapper)
    {
        final short[] result = new short[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static int[] map(final char[] array, final CharToIntMapper mapper)
    {
        final int[] result = new int[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static long[] map(final char[] array, final CharToLongMapper mapper)
    {
        final long[] result = new long[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static float[] map(final char[] array, final CharToFloatMapper mapper)
    {
        final float[] result = new float[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static double[] map(final char[] array, final CharToDoubleMapper mapper)
    {
        final double[] result = new double[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static boolean[] map(final short[] array, final ShortToBooleanMapper mapper)
    {
        final boolean[] result = new boolean[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static byte[] map(final short[] array, final ShortToByteMapper mapper)
    {
        final byte[] result = new byte[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static char[] map(final short[] array, final ShortToCharMapper mapper)
    {
        final char[] result = new char[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static short[] map(final short[] array, final ShortToShortMapper mapper)
    {
        final short[] result = new short[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static int[] map(final short[] array, final ShortToIntMapper mapper)
    {
        final int[] result = new int[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static long[] map(final short[] array, final ShortToLongMapper mapper)
    {
        final long[] result = new long[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static float[] map(final short[] array, final ShortToFloatMapper mapper)
    {
        final float[] result = new float[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static double[] map(final short[] array, final ShortToDoubleMapper mapper)
    {
        final double[] result = new double[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static boolean[] map(final int[] array, final IntToBooleanMapper mapper)
    {
        final boolean[] result = new boolean[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static byte[] map(final int[] array, final IntToByteMapper mapper)
    {
        final byte[] result = new byte[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static char[] map(final int[] array, final IntToCharMapper mapper)
    {
        final char[] result = new char[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static short[] map(final int[] array, final IntToShortMapper mapper)
    {
        final short[] result = new short[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static int[] map(final int[] array, final IntToIntMapper mapper)
    {
        final int[] result = new int[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static long[] map(final int[] array, final IntToLongMapper mapper)
    {
        final long[] result = new long[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static float[] map(final int[] array, final IntToFloatMapper mapper)
    {
        final float[] result = new float[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static double[] map(final int[] array, final IntToDoubleMapper mapper)
    {
        final double[] result = new double[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static boolean[] map(final long[] array, final LongToBooleanMapper mapper)
    {
        final boolean[] result = new boolean[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static byte[] map(final long[] array, final LongToByteMapper mapper)
    {
        final byte[] result = new byte[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static char[] map(final long[] array, final LongToCharMapper mapper)
    {
        final char[] result = new char[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static short[] map(final long[] array, final LongToShortMapper mapper)
    {
        final short[] result = new short[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static int[] map(final long[] array, final LongToIntMapper mapper)
    {
        final int[] result = new int[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static long[] map(final long[] array, final LongToLongMapper mapper)
    {
        final long[] result = new long[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static float[] map(final long[] array, final LongToFloatMapper mapper)
    {
        final float[] result = new float[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static double[] map(final long[] array, final LongToDoubleMapper mapper)
    {
        final double[] result = new double[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static boolean[] map(final float[] array, final FloatToBooleanMapper mapper)
    {
        final boolean[] result = new boolean[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static byte[] map(final float[] array, final FloatToByteMapper mapper)
    {
        final byte[] result = new byte[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static char[] map(final float[] array, final FloatToCharMapper mapper)
    {
        final char[] result = new char[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static short[] map(final float[] array, final FloatToShortMapper mapper)
    {
        final short[] result = new short[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static int[] map(final float[] array, final FloatToIntMapper mapper)
    {
        final int[] result = new int[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static long[] map(final float[] array, final FloatToLongMapper mapper)
    {
        final long[] result = new long[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static float[] map(final float[] array, final FloatToFloatMapper mapper)
    {
        final float[] result = new float[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static double[] map(final float[] array, final FloatToDoubleMapper mapper)
    {
        final double[] result = new double[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static boolean[] map(final double[] array, final DoubleToBooleanMapper mapper)
    {
        final boolean[] result = new boolean[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static byte[] map(final double[] array, final DoubleToByteMapper mapper)
    {
        final byte[] result = new byte[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static char[] map(final double[] array, final DoubleToCharMapper mapper)
    {
        final char[] result = new char[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static short[] map(final double[] array, final DoubleToShortMapper mapper)
    {
        final short[] result = new short[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static int[] map(final double[] array, final DoubleToIntMapper mapper)
    {
        final int[] result = new int[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static long[] map(final double[] array, final DoubleToLongMapper mapper)
    {
        final long[] result = new long[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static float[] map(final double[] array, final DoubleToFloatMapper mapper)
    {
        final float[] result = new float[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static double[] map(final double[] array, final DoubleToDoubleMapper mapper)
    {
        final double[] result = new double[array.length];

        for (int i = 0; i < array.length; i++)
            result[i] = mapper.map(array[i]);
        
        return result;
    }

    public static final class ArrayFunctions
    {
        private ArrayFunctions() { }

        @FunctionalInterface public static interface GenericFilter<T> { boolean filter(T value); }

        @FunctionalInterface public static interface BooleanFilter { boolean filter(boolean value); }
        @FunctionalInterface public static interface ByteFilter    { boolean filter(byte    value); }
        @FunctionalInterface public static interface CharFilter    { boolean filter(char    value); }
        @FunctionalInterface public static interface ShortFilter   { boolean filter(short   value); }
        @FunctionalInterface public static interface IntFilter     { boolean filter(int     value); }
        @FunctionalInterface public static interface LongFilter    { boolean filter(long    value); }
        @FunctionalInterface public static interface FloatFilter   { boolean filter(float   value); }
        @FunctionalInterface public static interface DoubleFilter  { boolean filter(double  value); }

        @FunctionalInterface public static interface GenericMapper<T, U>   { U map(T value); }

        @FunctionalInterface public static interface GenericToBooleanMapper<T> { boolean map(T value); }
        @FunctionalInterface public static interface GenericToByteMapper   <T> { byte    map(T value); }
        @FunctionalInterface public static interface GenericToCharMapper   <T> { char    map(T value); }
        @FunctionalInterface public static interface GenericToShortMapper  <T> { short   map(T value); }
        @FunctionalInterface public static interface GenericToIntMapper    <T> { int     map(T value); }
        @FunctionalInterface public static interface GenericToLongMapper   <T> { long    map(T value); }
        @FunctionalInterface public static interface GenericToFloatMapper  <T> { float   map(T value); }
        @FunctionalInterface public static interface GenericToDoubleMapper <T> { double  map(T value); }

        @FunctionalInterface public static interface BooleanToGenericMapper<T> { T map(boolean value); }
        @FunctionalInterface public static interface ByteToGenericMapper   <T> { T map(byte    value); }
        @FunctionalInterface public static interface CharToGenericMapper   <T> { T map(char    value); }
        @FunctionalInterface public static interface ShortToGenericMapper  <T> { T map(short   value); }
        @FunctionalInterface public static interface IntToGenericMapper    <T> { T map(int     value); }
        @FunctionalInterface public static interface LongToGenericMapper   <T> { T map(long    value); }
        @FunctionalInterface public static interface FlaotToGenericMapper  <T> { T map(float   value); }
        @FunctionalInterface public static interface DoubleToGenericMapper <T> { T map(double  value); }

        @FunctionalInterface public static interface BooleanToBooleanMapper { boolean map(boolean value); }
        @FunctionalInterface public static interface BooleanToByteMapper    { byte    map(boolean value); }
        @FunctionalInterface public static interface BooleanToCharMapper    { char    map(boolean value); }
        @FunctionalInterface public static interface BooleanToShortMapper   { short   map(boolean value); }
        @FunctionalInterface public static interface BooleanToIntMapper     { int     map(boolean value); }
        @FunctionalInterface public static interface BooleanToLongMapper    { long    map(boolean value); }
        @FunctionalInterface public static interface BooleanToFloatMapper   { float   map(boolean value); }
        @FunctionalInterface public static interface BooleanToDoubleMapper  { double  map(boolean value); }

        @FunctionalInterface public static interface ByteToBooleanMapper { boolean map(byte value); }
        @FunctionalInterface public static interface ByteToByteMapper    { byte    map(byte value); }
        @FunctionalInterface public static interface ByteToCharMapper    { char    map(byte value); }
        @FunctionalInterface public static interface ByteToShortMapper   { short   map(byte value); }
        @FunctionalInterface public static interface ByteToIntMapper     { int     map(byte value); }
        @FunctionalInterface public static interface ByteToLongMapper    { long    map(byte value); }
        @FunctionalInterface public static interface ByteToFloatMapper   { float   map(byte value); }
        @FunctionalInterface public static interface ByteToDoubleMapper  { double  map(byte value); }

        @FunctionalInterface public static interface CharToBooleanMapper { boolean map(char value); }
        @FunctionalInterface public static interface CharToByteMapper    { byte    map(char value); }
        @FunctionalInterface public static interface CharToCharMapper    { char    map(char value); }
        @FunctionalInterface public static interface CharToShortMapper   { short   map(char value); }
        @FunctionalInterface public static interface CharToIntMapper     { int     map(char value); }
        @FunctionalInterface public static interface CharToLongMapper    { long    map(char value); }
        @FunctionalInterface public static interface CharToFloatMapper   { float   map(char value); }
        @FunctionalInterface public static interface CharToDoubleMapper  { double  map(char value); }

        @FunctionalInterface public static interface ShortToBooleanMapper { boolean map(short value); }
        @FunctionalInterface public static interface ShortToByteMapper    { byte    map(short value); }
        @FunctionalInterface public static interface ShortToCharMapper    { char    map(short value); }
        @FunctionalInterface public static interface ShortToShortMapper   { short   map(short value); }
        @FunctionalInterface public static interface ShortToIntMapper     { int     map(short value); }
        @FunctionalInterface public static interface ShortToLongMapper    { long    map(short value); }
        @FunctionalInterface public static interface ShortToFloatMapper   { float   map(short value); }
        @FunctionalInterface public static interface ShortToDoubleMapper  { double  map(short value); }

        @FunctionalInterface public static interface IntToBooleanMapper  { boolean map(int value); }
        @FunctionalInterface public static interface IntToByteMapper     { byte    map(int value); }
        @FunctionalInterface public static interface IntToCharMapper     { char    map(int value); }
        @FunctionalInterface public static interface IntToShortMapper    { short   map(int value); }
        @FunctionalInterface public static interface IntToIntMapper      { int     map(int value); }
        @FunctionalInterface public static interface IntToLongMapper     { long    map(int value); }
        @FunctionalInterface public static interface IntToFloatMapper    { float   map(int value); }
        @FunctionalInterface public static interface IntToDoubleMapper   { double  map(int value); }

        @FunctionalInterface public static interface LongToBooleanMapper { boolean map(long value); }
        @FunctionalInterface public static interface LongToByteMapper    { byte    map(long value); }
        @FunctionalInterface public static interface LongToCharMapper    { char   map(long value); }
        @FunctionalInterface public static interface LongToShortMapper   { short  map(long value); }
        @FunctionalInterface public static interface LongToIntMapper     { int    map(long value); }
        @FunctionalInterface public static interface LongToLongMapper    { long   map(long value); }
        @FunctionalInterface public static interface LongToFloatMapper   { float  map(long value); }
        @FunctionalInterface public static interface LongToDoubleMapper  { double map(long value); }
        
        @FunctionalInterface public static interface FloatToBooleanMapper { boolean map(float value); }
        @FunctionalInterface public static interface FloatToByteMapper    { byte    map(float value); }
        @FunctionalInterface public static interface FloatToCharMapper    { char    map(float value); }
        @FunctionalInterface public static interface FloatToShortMapper   { short   map(float value); }
        @FunctionalInterface public static interface FloatToIntMapper     { int     map(float value); }
        @FunctionalInterface public static interface FloatToLongMapper    { long    map(float value); }
        @FunctionalInterface public static interface FloatToFloatMapper   { float   map(float value); }
        @FunctionalInterface public static interface FloatToDoubleMapper  { double  map(float value); }

        @FunctionalInterface public static interface DoubleToBooleanMapper { boolean map(double value); }
        @FunctionalInterface public static interface DoubleToByteMapper    { byte    map(double value); }
        @FunctionalInterface public static interface DoubleToCharMapper    { char    map(double value); }
        @FunctionalInterface public static interface DoubleToShortMapper   { short   map(double value); }
        @FunctionalInterface public static interface DoubleToIntMapper     { int     map(double value); }
        @FunctionalInterface public static interface DoubleToLongMapper    { long    map(double value); }
        @FunctionalInterface public static interface DoubleToFloatMapper   { float   map(double value); }
        @FunctionalInterface public static interface DoubleToDoubleMapper  { double  map(double value); }
        
        @FunctionalInterface public static interface GenericAction<T> { void run(T       value); }
        @FunctionalInterface public static interface BooleanAction    { void run(boolean value); }
        @FunctionalInterface public static interface ByteAction       { void run(byte    value); }
        @FunctionalInterface public static interface CharAction       { void run(char    value); }
        @FunctionalInterface public static interface ShortAction      { void run(short   value); }
        @FunctionalInterface public static interface IntAction        { void run(int     value); }
        @FunctionalInterface public static interface LongAction       { void run(long    value); }
        @FunctionalInterface public static interface FloatAction      { void run(float   value); }
        @FunctionalInterface public static interface DoubleAction     { void run(double  value); }
    }
}