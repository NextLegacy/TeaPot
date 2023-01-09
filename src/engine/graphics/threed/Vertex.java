package engine.graphics.threed;

import engine.math.FinalVector;
import engine.math.Matrix;
import engine.math.Vector4;

public final class Vertex extends FinalVector
{
    public final FinalVector texture; //vertex texture
    public final FinalVector normal ; //vertex normal

    public Vertex(final Vector4 vertex, final Vector4 texture, final Vector4 normal)
    {
        super(vertex);

        this.texture = texture.toFinalVector();
        this.normal  = normal .toFinalVector();
    }

    public Vertex(final double x, final double y, final double z, final double w, final Vector4 texture, final Vector4 normal)
    {
        super(x, y, z, w);

        this.texture = texture.toFinalVector();
        this.normal  = normal .toFinalVector();
    }

    public Vertex transform(Matrix transformation)
    {
        return new Vertex
        (
            transformation.times(this),
            texture,
            normal
        );
    }

    public String toString()
    {
        return "Vertex(v: " + toCompactString() + " | t: " + texture.toCompactString() + " | n: " + normal.toCompactString() + ")";
    }

    public Vertex clone()
    {
        return new Vertex
        (
            clone(),
            texture.clone(),
            normal .clone()
        );
    }
}
