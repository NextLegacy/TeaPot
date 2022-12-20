package engine.utils;

public final class ArrayUtils
{
    private ArrayUtils() { }

    public static void copy(Object source, int sourcePosition, Object destination, int destinationPosition, int length)
    {
        System.arraycopy(source, sourcePosition, destination, destinationPosition, length);
    }

    public static void copy(Object source, Object destination, int length) { copy(source, 0, destination, 0, length); }

    public static void copy(Object source, Object destination, int x1, int y1, int x2, int y2, int width, int height)
    {
        final int topY    = y1 < y2 ? y1 : y2,
                  bottomY = topY == y1 ? y2 : y1,
                  leftX   = x1 < x2 ? x1 : x2,
                  rightX  = leftX == x1 ? x2 : x1,
                  dx      = rightX - leftX + 1;

        for (int cy = topY, index = leftX + cy * width; cy <= bottomY; index = leftX + ++cy * width)
            copy(source, index, destination, index, dx);
    }

    @SuppressWarnings("unused")
    private static void fill(Object array, int start, int end)
    {
        final int left  = start < end ? start : end,
                  right = start > end ? start : end;

        int length = right - left;

        for (int i = 1; i < length; i <<= 1)
        {
            System.arraycopy(array, left, array, i + left, length < i << 1 ? length - i : i);
        }
    }

    private static final int SMALL_ARRAY_LENGTH = 64;

    private static void fastFillForTypedArrays(Object array, final int left, final int right, final int length)
    {
        for (int i = SMALL_ARRAY_LENGTH; i < length; i <<= 1)
            System.arraycopy(array, left, array, i + left, length < i << 1 ? length - i : i);
    }

    public static void fill(int[] array, int value, int start, int end)
    {
        final int left    = start < end ? start : end,
                  right   = start > end ? start : end,
                  length  = right - left,
                  length2 = length < SMALL_ARRAY_LENGTH ? length : SMALL_ARRAY_LENGTH;

        for (int i = start; i < length2; i++) 
            array[i] = value;

        fastFillForTypedArrays(array, left, right, length);
    }

    public static void fill(double[] array, double value, int start, int end)
    {
        final int left    = start < end ? start : end,
                  right   = start > end ? start : end,
                  length  = right - left,
                  length2 = length < SMALL_ARRAY_LENGTH ? length : SMALL_ARRAY_LENGTH;

        for (int i = start; i <= length2; i++) 
            array[i] = value;

        fastFillForTypedArrays(array, left, right, length);
    }

    public static <T> void fill(T[] array, T value, int start, int end)
    {
        final int left    = start < end ? start : end,
                  right   = start > end ? start : end,
                  length  = right - left + 1,
                  length2 = length < SMALL_ARRAY_LENGTH ? length : SMALL_ARRAY_LENGTH;

        for (int i = start; i <= length2; i++) 
            array[i] = value;

        fastFillForTypedArrays(array, left, right, length);
    }

    public static void     fill(int[]    array, int    value) { fill(array, value, 0, array.length); }
    public static void     fill(double[] array, double value) { fill(array, value, 0, array.length); }
    public static <T> void fill(T[]      array, T      value) { fill(array, value, 0, array.length); }
}