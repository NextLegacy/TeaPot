package engine.utils.array;

public final class ArrayFunctions
{
    private ArrayFunctions() { }

    //@FunctionalInterface public static interface BooleanComparator    { public int compare(final boolean a, final boolean b); }
    //@FunctionalInterface public static interface ByteComparator       { public int compare(final byte    a, final byte    b); }
    //@FunctionalInterface public static interface CharComparator       { public int compare(final char    a, final char    b); }
    //@FunctionalInterface public static interface ShortComparator      { public int compare(final short   a, final short   b); }
    //@FunctionalInterface public static interface IntComparator        { public int compare(final int     a, final int     b); }
    //@FunctionalInterface public static interface LongComparator       { public int compare(final long    a, final long    b); }
    //@FunctionalInterface public static interface FloatComparator      { public int compare(final float   a, final float   b); }
    //@FunctionalInterface public static interface DoubleComparator     { public int compare(final double  a, final double  b); }
    //@FunctionalInterface public static interface GenericComparator<T> { public int compare(final T       a, final T       b); }

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
}
