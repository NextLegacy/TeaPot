package engine.threed;

import engine.math.Matrix;
import engine.math.Vector4;

public final class Vertex 
{
    public Vector4 v; //vertex position
    public Vector4 t; //vertex texture
    public Vector4 n; //vertex normal

    public Vertex(Vector4 v, Vector4 t, Vector4 n)
    {
        this.v = v;
        this.t = t;
        this.n = n;
    }

    public Vertex transform(Matrix transformation)
    {
        return new Vertex(
            transformation.times(v),
            t,
            n
        );
    }

    public String toString()
    {
        return "Vertex(v: " + v.toCompactString() + " | t: " + t.toCompactString() + " | n: " + n.toCompactString() + ")";
    }
}
