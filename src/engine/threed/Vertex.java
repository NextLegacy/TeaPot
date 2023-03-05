package engine.threed;

import engine.math.FinalVector;
import engine.math.Matrix;
import engine.math.Vector4;

/**
 * A vertex is a point in 3D space, with a texture and a normal.
 * 
 * @author NextLegacy
 * @version 1.0
 */
public final class Vertex extends FinalVector
{
    public final FinalVector texture; //vertex texture
    public final FinalVector normal ; //vertex normal 

    public Vertex(                                           ) { this(new FinalVector(), null   , null); }
    public Vertex(final Vector4 vertex                       ) { this(vertex           , null   , null); }
    public Vertex(final Vector4 vertex, final Vector4 texture) { this(vertex           , texture, null); }

    public Vertex(final Vector4 vertex, final Vector4 texture, final Vector4 normal)
    {
        super(vertex);

        this.texture = texture.toFinalVector();
        this.normal  = normal .toFinalVector();
    }

    public Vertex transformed(Matrix transformation)
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
