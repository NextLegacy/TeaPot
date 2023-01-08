package engine.graphics.threed;

import engine.math.FinalVector;
import engine.math.Matrix;
import engine.math.Vector4;

public final class Vertex
{
    public final FinalVector vertex ; //vertex position
    public final FinalVector texture; //vertex texture
    public final FinalVector normal ; //vertex normal

    public Vertex(Vector4 vertex, Vector4 texture, Vector4 normal)
    {
        this.vertex  = vertex .toFinalVector();
        this.texture = texture.toFinalVector();
        this.normal  = normal .toFinalVector();
    }

    public Vertex transform(Matrix transformation)
    {
        return new Vertex
        (
            transformation.times(vertex),
            texture,
            normal
        );
    }

    public String toString()
    {
        return "Vertex(v: " + vertex.toCompactString() + " | t: " + texture.toCompactString() + " | n: " + normal.toCompactString() + ")";
    }

    public Vertex clone()
    {
        return new Vertex
        (
            vertex .clone(),
            texture.clone(),
            normal .clone()
        );
    }
}
