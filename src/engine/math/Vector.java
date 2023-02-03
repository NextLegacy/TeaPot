package engine.math;

import static engine.utils.MathUtils.*;

import engine.utils.MathUtils;

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
        return new Vector(x(), y(), z(), w()); 
    }

    public final double x() { return x; }
    public final double y() { return y; }
    public final double z() { return z; }
    public final double w() { return w; }

    public Vector x(double x) { this.x = x; return this; }
    public Vector y(double y) { this.y = y; return this; }
    public Vector z(double z) { this.z = z; return this; }
    public Vector w(double w) { this.w = w; return this; }

    public Vector set(Vector4 vec) { return set(vec.x(), vec.y(), vec.z(), vec.w()); }

    public Vector set(double x)                               { return x(x);                }
    public Vector set(double x, double y)                     { return x(x).y(y);           }
    public Vector set(double x, double y, double z)           { return x(x).y(y).z(z);      }
    public Vector set(double x, double y, double z, double w) { return x(x).y(y).z(z).w(w); }

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

    public Vector clampX(double min, double max) { x = x < min ? min : x > max ? max : x; return this; }
    public Vector clampY(double min, double max) { y = y < min ? min : y > max ? max : y; return this; }
    public Vector clampZ(double min, double max) { z = z < min ? min : z > max ? max : z; return this; }
    public Vector clamp(Vector4 min, Vector4 max)
    {
        return  clampX(min.x(), max.x())
               .clampY(min.y(), max.y())
               .clampZ(min.z(), max.z());
    }

    public Vector floorX() { x = MathUtils.floor(x); return this; }
    public Vector floorY() { y = MathUtils.floor(y); return this; }
    public Vector floorZ() { z = MathUtils.floor(z); return this; }
    public Vector floor()
    {
        return  floorX()
               .floorY()
               .floorZ();
    }

    public Vector roundX() { x = MathUtils.round(x); return this; }
    public Vector roundY() { y = MathUtils.round(y); return this; }
    public Vector roundZ() { z = MathUtils.round(z); return this; }
    public Vector round()
    {
        return  roundX()
               .roundY()
               .roundZ();
    }

    public Vector absX() { x = MathUtils.abs(x); return this; }
    public Vector absY() { y = MathUtils.abs(y); return this; }
    public Vector absZ() { z = MathUtils.abs(z); return this; }
    public Vector abs()
    {
        return  absX()
               .absY()
               .absZ();
    }

    public Vector lerpX(double to, double t) { return x(MathUtils.lerp(x(), to, t)); }
    public Vector lerpY(double to, double t) { return y(MathUtils.lerp(y(), to, t)); }
    public Vector lerpZ(double to, double t) { return z(MathUtils.lerp(z(), to, t)); }
    public Vector lerpTo(double x, double y, double z, double t) { return lerpX(x, t).lerpY(y, t).lerpZ(z, t); }
    public Vector lerpTo(Vector4 to, double t) { return lerpTo(to.x(), to.y(), to.z(), t); }

    public static Vector lerp(Vector4 from, Vector4 to, double t) { return vec(from).lerpTo(to, t); }

    public static final Vector random() { return new Vector(Math.random(), Math.random(), Math.random()); }

    public static Vector fromAngle(double rotation)
    {
        return new Vector(Math.cos(rotation), Math.sin(rotation));
    }

    static final Vector zero     = new Vector( 0,  0,  0,  1);
    static final Vector one      = new Vector( 1,  1,  1,  1);
    static final Vector right    = new Vector( 1,  0,  0,  1);
    static final Vector left     = new Vector(-1,  0,  0,  1);
    static final Vector up       = new Vector( 0,  1,  0,  1);
    static final Vector down     = new Vector( 0, -1,  0,  1);
    static final Vector forward  = new Vector( 0,  0,  1,  1);
    static final Vector backward = new Vector( 0,  0, -1,  1);
    
    public static final Vector zero() { return new Vector(zero); }
    public static final Vector one () { return new Vector(one ); }

    public static final Vector right    () { return new Vector(right   ); }
    public static final Vector left     () { return new Vector(left    ); }
    public static final Vector up       () { return new Vector(down    ); }
    public static final Vector down     () { return new Vector(up      ); }
    public static final Vector forward  () { return new Vector(forward ); }
    public static final Vector backward () { return new Vector(backward); }
}
