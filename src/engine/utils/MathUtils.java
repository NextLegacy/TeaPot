package engine.utils;

import engine.math.FinalVector;
import engine.math.Vector;
import engine.math.Vector4;

public final class MathUtils
{
    private MathUtils() { }

    public static final double EPSILON = 1e-16;
    public static final double PI      = 3.14159265358979323846d;
    public static final double E       = 2.7182818284590452354d;

    public static final double DEGREE  = 180 / PI;
    public static final double RADIANT = PI / 180;

    public static int    toInt   (String string) { return Integer.parseInt   (string); }
    public static double toDouble(String string) { return Double .parseDouble(string); }

    public static int max(int... values)
    {
        int result = values[0];

        for (int i = 1; i < values.length; i++)
            if (values[i] > result)
                result = values[i];

        return result;
    }

    public static double max(double... values)
    {
        double result = values[0];

        for (int i = 1; i < values.length; i++)
            if (values[i] > result)
                result = values[i];

        return result;
    }

    public static int min(int... values)
    {
        int result = values[0];

        for (int i = 1; i < values.length; i++)
            if (values[i] < result)
                result = values[i];

        return result;
    }

    public static double min(double... values)
    {
        double result = values[0];

        for (int i = 1; i < values.length; i++)
            if (values[i] < result)
                result = values[i];

        return result;
    }

    public static double sign(double n) { return n >= 0 ? 1 : -1; }

    public static double abs(double n) { return n < 0 ? -n : n; }

    public static int floor(double n) { return (int) n; }
    public static int round(double n) { return floor(n + sign(n) * 0.5); }
    
    public static boolean isInRange(double n, double min, double max) { return n >= min && n <= max; }
    public static boolean IsNotInRange(double n, double min, double max) { return !isInRange(n, min, max); }
    
    public static double clamp(double n, double min, double max) { return n <= min ? min : n >= max ? max : n; }

    public static double lerp(double a, double b, double t) { return t * (b - a) + a; }
    public static Vector lerp(Vector4 from, Vector4 to, double t) { return Vector.lerp(from, to, t); }

    public static Vector inverseOf  (Vector4 vector) { return vector.inverted  (); }   
    public static Vector normalOf   (Vector4 vector) { return vector.normalized(); }
    public static double magnitudeOf(Vector4 vector) { return vector.magnitude (); }  
    public static Vector angleOf    (Vector4 vector) { return vector.angle     (); }

    public static Vector vec()                                       { return vec(0, 0, 0, 1); }
    public static Vector vec(double x)                               { return vec(x, 0, 0, 1); }
    public static Vector vec(double x, double y)                     { return vec(x, y, 0, 1); }
    public static Vector vec(double x, double y, double z)           { return vec(x, y, z, 1); }
    public static Vector vec(double x, double y, double z, double w) { return new Vector(x, y, z, w); }
    
    public static FinalVector fvec()                                       { return fvec(0, 0, 0, 1); }
    public static FinalVector fvec(double x)                               { return fvec(x, 0, 0, 1); }
    public static FinalVector fvec(double x, double y)                     { return fvec(x, y, 0, 1); }
    public static FinalVector fvec(double x, double y, double z)           { return fvec(x, y, z, 1); }
    public static FinalVector fvec(double x, double y, double z, double w) { return new FinalVector(x, y, z, w); }
    
    public static Vector      vec (Vector4 vector) { return vector.toVector     (); }
    public static FinalVector fvec(Vector4 vector) { return vector.toFinalVector(); }
}