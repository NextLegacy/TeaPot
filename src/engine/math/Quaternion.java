package engine.math;

import static engine.utils.MathUtils.*;

public class Quaternion
{
    FinalVector q;
    
    Quaternion(double x, double y, double z, double w) { q = fvec(x, y, z, w); }

    public Quaternion plus(Quaternion quaternion)
    {
        return new Quaternion(
            q.x + quaternion.q.x,
            q.y + quaternion.q.y,
            q.z + quaternion.q.z,
            q.w + quaternion.q.w
        );
    }

    
    public Quaternion minus(Quaternion quaternion)
    {
        return new Quaternion(
            q.x - quaternion.q.x,
            q.y - quaternion.q.y,
            q.z - quaternion.q.z,
            q.w - quaternion.q.w
        );
    }

    public Quaternion times(Quaternion quaternion)
    {
        return new Quaternion(
            q.x * quaternion.q.x,
            q.y * quaternion.q.y,
            q.z * quaternion.q.z,
            q.w * quaternion.q.w
        );
    }
    public Quaternion scaledBy(double n) { return new Quaternion(q.x * n, q.y * n, q.z * n, q.w * n); }
    public Vector rotateVector(Vector4 vector)  
    { 
        //https://gamedev.stackexchange.com/a/50545
        /*
         * result = 2.0f * dot(u, v) * u
         *          + (s*s - dot(u, u)) * v
         *          + 2.0f * s * cross(u, v)
         * 
         * result = q * (2.0d * q.dot(vector))    +
         *          vector * (q.w*q.w - q.dot(q)) +
         *          q.cross(v) * (2.0d * q.w);
         */

        return q.times(2.0d * q.dot(vector))
                .plus(vector.times(q.w * q.w - q.dot(q)))
                .plus(q.cross(vector).times(2.0d * q.w));
        
        //return matrix().times(vector);

    }

    public Vector rotateVector2(Vector4 vector)
    {
        return matrix().times(vector);
    }
        
    public Quaternion dividedBy(Quaternion quaternion)
    {
        return new Quaternion(
            q.x / quaternion.q.x,
            q.y / quaternion.q.y,
            q.z / quaternion.q.z,
            q.w / quaternion.q.w
        );
    }

    public double norm()
    {
        return q.magnitude();
    }

    public double normSq()
    {
        return q.dot(q);
    }

    public Quaternion normalized()
    {
        double norm = norm();

        if (norm < EPSILON) 
            return Quaternion.zero;

        return scaledBy(norm);
    }

    public Quaternion inverse()
    {
        double normSq = normSq();

        if (normSq < EPSILON || normSq == 0) 
            return Quaternion.zero;
        
        normSq = 1 / normSq;

        return new Quaternion(-q.x * normSq, -q.y * normSq, -q.z * normSq, q.w * normSq);
    }

    public Matrix matrix()
    {
        double n = q.w * q.w + q.x * q.x + q.y * q.y + q.z * q.z;
        double s = n == 0 ? 0 : 2 / n;
        
        double wx = s * q.w * q.x, wy = s * q.w * q.y, wz = s * q.w * q.z;
        double xx = s * q.x * q.x, xy = s * q.x * q.y, xz = s * q.x * q.z;
        double yy = s * q.y * q.y, yz = s * q.y * q.z, zz = s * q.z * q.z;
    
        return new Matrix(
            1 - yy - zz,      xy - wz,      xz + wy,          0,
                xy + wz,  1 - xx - zz,      yz - wx,          0,
                xz - wy,      yz + wx,  1 - xx - yy,          0,
                      0,            0,            0,          1
        );
    }

    public Vector toEuler()
    {
        double y2 = q.y*q.y;

        return new Vector
        (
            Math.atan2(2 * (q.w * q.x + q.y * q.z), 1 - 2 * (q.x*q.x + y2)),
            Math.asin (2 * (q.w * q.y - q.z * q.x)),
            Math.atan2(2 * (q.w * q.z + q.x * q.y), 1 - 2 * (y2 + q.z*q.z))
        );
    }

    public final boolean equals(Quaternion quaternion)
    {
        return quaternion != null && q.x == quaternion.q.x && q.y == quaternion.q.y && q.z == quaternion.q.z;
    }

    public final boolean equals(Object object)
    {
        if (object == null) return false;

        return object == null ? false :
               this == object || 
               (object instanceof Quaternion quaternion) && 
               equals(quaternion);
    }

    @Override
    public String toString() 
    {
        return String.format("%s(%s)", getClass().getSimpleName(), q.toCompactString());
    }

    public static Quaternion FromEuler(Vector4 vector)
    {
        vector = vector.times(0.5d * RADIANT);
    
        double cosX = Math.cos(vector.x);
        double cosY = Math.cos(vector.y);
        double cosZ = Math.cos(vector.z);
    
        double sinX = Math.sin(vector.x);
        double sinY = Math.sin(vector.y);
        double sinZ = Math.sin(vector.z);

        return new Quaternion
        (
            cosY * cosZ * sinX + cosX * sinY * sinZ,
            cosX * cosZ * sinY - cosY * sinX * sinZ,
            cosX * cosY * sinZ + cosZ * sinX * sinY,
            cosX * cosY * cosZ - sinX * sinY * sinZ
        );
    }

    public static Quaternion FromBetweenVector(Vector4 a, Vector4 b)
    {
        double dot = a.dot(b);
        Vector c   = a.cross(b);
    
        return new Quaternion
        (
            c.x,
            c.y,
            c.z,
            dot + Math.sqrt(dot * dot + c.dot(c))
        ).normalized();
    }

    public static Quaternion FromAxis(Vector4 axis, double angle)
    {
        double halfAngle = angle * 0.5;

        double a = axis.x;
        double b = axis.y;
        double c = axis.z;
    
        double sin_2 = Math.sin(halfAngle);
        double cos_2 = Math.cos(halfAngle);
    
        double sin_norm = sin_2 / Math.sqrt(a * a + b * b + c * c);

        return new Quaternion
        (
            a * sin_norm,
            b * sin_norm,
            c * sin_norm, 
            cos_2
        );
    }

    public static Quaternion zero = new Quaternion(0, 0, 0, 1);
}
