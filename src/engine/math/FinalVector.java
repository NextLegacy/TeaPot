package engine.math;

import static engine.utils.MathUtils.*;

public class FinalVector extends Vector4
{
    public final double x;
    public final double y;
    public final double z;
    public final double w;

    private final Vector inverted;
    private final Vector normalized;
    private final double normalSquared;
    private final double magnitude;
    private final Vector angle;

    public FinalVector(Vector4 v) { this(v.x(), v.y(), v.z(), v.w()); }
    public FinalVector()                                       { this(0, 0, 0, 1);  }
    public FinalVector(double x)                               { this(x, 0, 0, 1);  }
    public FinalVector(double x, double y)                     { this(x, y, 0, 1);  }
    public FinalVector(double x, double y, double z)           { this(x, y, z, 1);  }
    public FinalVector(double x, double y, double z, double w) 
    { 
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;

        Vector self = vec(this);

        inverted      = self.inverted        ();             
        magnitude     = self.magnitude       ();
        normalized    = self.normalized      ();
        normalSquared = self.magnitudeSquared();
        angle         = self.angle           ();
    }

    public final double x() { return x; }
    public final double y() { return y; }
    public final double z() { return z; }
    public final double w() { return w; }

    @Override public Vector inverted        () { return inverted     ; }
    @Override public Vector normalized      () { return normalized   ; }
    @Override public double magnitudeSquared() { return normalSquared; }
    @Override public double magnitude       () { return magnitude    ; }
    @Override public Vector angle           () { return angle        ; }

    @Override
    public FinalVector clone() 
    {
        return this; 
    }

    public static final FinalVector zero     = fvec(Vector.zero    );
    public static final FinalVector one      = fvec(Vector.one     );
    public static final FinalVector right    = fvec(Vector.right   );
    public static final FinalVector left     = fvec(Vector.left    );
    public static final FinalVector down     = fvec(Vector.down    );
    public static final FinalVector up       = fvec(Vector.up      );
    public static final FinalVector forward  = fvec(Vector.forward );
    public static final FinalVector backward = fvec(Vector.backward);
}
