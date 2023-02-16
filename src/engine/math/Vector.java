package engine.math;

import static engine.utils.MathUtils.*;

import engine.utils.MathUtils;

/**
 * A mutable version of the {@link Vector4} class. <p>
 * 
 * @author NextLegacy
 * @version 1.0
 * @since 1.0
 * @see Vector4
 * @see FinalVector
 */
public final class Vector extends Vector4
{
    public double x; //TODO: use everywhere the fields instead of the getters and setter
    public double y;
    public double z;
    public double w;
    
    public Vector(Vector4 v) { this(v.x(), v.y(), v.z(), v.w()); }
    public Vector()                             { this(0, 0, 0, 1);  }
    public Vector(double x)                     { this(x, 0, 0, 1);  }
    public Vector(double x, double y)           { this(x, y, 0, 1);  }
    public Vector(double x, double y, double z) { this(x, y, z, 1);  }
    public Vector(double x, double y, double z, double w) 
    { 
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override public Vector inverted        () { return vec(this).inverse();   }
    @Override public Vector normalized      () { return vec(this).normalize(); }
    @Override public double magnitudeSquared() { return dot(this); }
    @Override public double magnitude       () { return Math.sqrt(magnitudeSquared()); }
    @Override public Vector angle           () { return vec(angleTo(Vector.right), angleTo(Vector.up)); }

    @Override 
    public Vector clone() 
    {
        return new Vector(x, y, z, w); 
    }

    public final double x() { return x; }
    public final double y() { return y; }
    public final double z() { return z; }
    public final double w() { return w; }

    public Vector set(Vector4 vec) { return set(vec.x(), vec.y(), vec.z(), vec.w()); }

    public Vector set(double x)                               { this.x = x;                                     return this; }
    public Vector set(double x, double y)                     { this.x = x; this.y = y;                         return this; }
    public Vector set(double x, double y, double z)           { this.x = x; this.y = y; this.z = z;             return this; }
    public Vector set(double x, double y, double z, double w) { this.x = x; this.y = y; this.z = z; this.w = w; return this; }

    public Vector setAll(double n) { return set(n, n, n); }

    public Vector multiplyBy(Vector4 vec) { x *= vec.x(); y *= vec.y(); z *= vec.z(); return this; }
    public Vector divideBy  (Vector4 vec) { x /= vec.x(); y /= vec.y(); z /= vec.z(); return this; }
    public Vector add       (Vector4 vec) { x += vec.x(); y += vec.y(); z += vec.z(); return this; }
    public Vector subtract  (Vector4 vec) { x -= vec.x(); y -= vec.y(); z -= vec.z(); return this; }
    
    public Vector multiplyAllBy(double n) { x *= n; y *= n; z *= n; return this; }
    public Vector divideAllBy  (double n) { x /= n; y /= n; z /= n; return this; }
    public Vector addAll       (double n) { x += n; y += n; z += n; return this; }
    public Vector subtractAll  (double n) { x -= n; y -= n; z -= n; return this; }

    public Vector multiplyBy(double x) { x *= x; return this; }
    public Vector divideBy  (double x) { x /= x; return this; }
    public Vector add       (double x) { x += x; return this; }
    public Vector subtract  (double x) { x -= x; return this; }

    public Vector multiplyBy(double x, double y) { this.x *= x; this.y *= y; return this; }
    public Vector divideBy  (double x, double y) { this.x /= x; this.y /= y; return this; }
    public Vector add       (double x, double y) { this.x += x; this.y += y; return this; }
    public Vector subtract  (double x, double y) { this.x -= x; this.y -= y; return this; }

    public Vector multiplyBy(double x, double y, double z) { this.x *= x; this.y *= y;  this.z *= z; return this; }
    public Vector divideBy  (double x, double y, double z) { this.x /= x; this.y /= y;  this.z /= z; return this; }
    public Vector add       (double x, double y, double z) { this.x += x; this.y += y;  this.z += z; return this; }
    public Vector subtract  (double x, double y, double z) { this.x -= x; this.y -= y;  this.z -= z; return this; }

    public Vector inverse  () { return multiplyBy(-1); }
    public Vector normalize() { return divideBy(magnitude()); }

    public Vector clamp(Vector4 min, Vector4 max)
    {
        return new Vector
        (
            MathUtils.clamp(x, min.x(), max.x()),
            MathUtils.clamp(y, min.y(), max.y()),
            MathUtils.clamp(z, min.z(), max.z())
        );
    }

    public Vector floor()
    {
        return new Vector
        (
            MathUtils.floor(x),
            MathUtils.floor(y),
            MathUtils.floor(z)
        );
    }

    public Vector round()
    {
        return new Vector
        (
            MathUtils.round(x),
            MathUtils.round(y),
            MathUtils.round(z)
        );
    }

    public Vector abs()
    {
        return new Vector
        (
            MathUtils.abs(x),
            MathUtils.abs(y),
            MathUtils.abs(z)
        );
    }

    public Vector lerpTo(Vector4 to, double t) 
    { 
        return new Vector
        (
            MathUtils.lerp(x, to.x(), t),
            MathUtils.lerp(y, to.y(), t),
            MathUtils.lerp(z, to.z(), t)
        );
    }

    public static Vector lerp(Vector4 from, Vector4 to, double t) { return vec(from).lerpTo(to, t); }

    public static final Vector random() { return new Vector(Math.random(), Math.random(), Math.random()); }

    public static Vector fromAngle(double rotation)
    {
        return new Vector(Math.cos(rotation), Math.sin(rotation));
    }

    static final Vector zero     = new Vector( 0,  0,  0);
    static final Vector one      = new Vector( 1,  1,  1);
    static final Vector right    = new Vector( 1,  0,  0);
    static final Vector left     = new Vector(-1,  0,  0);
    static final Vector up       = new Vector( 0,  1,  0);
    static final Vector down     = new Vector( 0, -1,  0);
    static final Vector forward  = new Vector( 0,  0,  1);
    static final Vector backward = new Vector( 0,  0, -1);
    
    public static final Vector zero() { return new Vector(zero); }
    public static final Vector one () { return new Vector(one ); }

    public static final Vector right    () { return new Vector(right   ); }
    public static final Vector left     () { return new Vector(left    ); }
    public static final Vector up       () { return new Vector(down    ); }
    public static final Vector down     () { return new Vector(up      ); }
    public static final Vector forward  () { return new Vector(forward ); }
    public static final Vector backward () { return new Vector(backward); }
}
