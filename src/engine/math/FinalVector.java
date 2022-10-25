package engine.math;

import static engine.utils.MathUtils.*;

public final class FinalVector extends Vector4
{
    private Vector inverted;
    private Vector normalized;
    private double magnitude;
    private Vector angle;

    public FinalVector()                                       { this(0, 0, 0, 1);  }
    public FinalVector(double x)                               { this(x, 0, 0, 1);  }
    public FinalVector(double x, double y)                     { this(x, y, 0, 1);  }
    public FinalVector(double x, double y, double z)           { this(x, y, z, 1);  }
    public FinalVector(double x, double y, double z, double w) { super(x, y, z, w); compute(); }

    private FinalVector compute()
    {
        Vector self = vec(this);

        inverted   = self.inverted  ();             
        magnitude  = self.magnitude ();
        normalized = self.normalized();
        angle      = self.angle     ();

        return this;
    }

    @Override public Vector inverted  () { return inverted;   }
    @Override public Vector normalized() { return normalized; }
    @Override public double magnitude () { return magnitude;  }
    @Override public Vector angle     () { return angle;      }

    public static final FinalVector zero = new FinalVector(0, 0, 0, 1);
    public static final FinalVector one  = new FinalVector(1, 1, 1, 1);

    public static final FinalVector up    = new FinalVector( 0, -1);
    public static final FinalVector right = new FinalVector( 1,  0);
    public static final FinalVector down  = new FinalVector( 0,  1);
    public static final FinalVector left  = new FinalVector(-1,  0);
    
    public static Vector fromAngle(double rotation)
    {
        return new Vector(Math.cos(rotation), Math.sin(rotation));
    }
}
