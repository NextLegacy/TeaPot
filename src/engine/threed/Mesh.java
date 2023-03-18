package engine.threed;

/**
 * A mesh is a collection of triangles.
 * 
 * @author NextLegacy
 * @version 1.0
 */
public final class Mesh 
{
    // TODO: optimize this by using a Vertex array and storing the indices for the triangles, way less pointer needed
    public final Triangle[] triangles;
    
    public Mesh(Triangle[] triangles)
    {
        this.triangles = triangles;
    }

    public String toString()
    {
        String result = "";

        for (Triangle triangle : triangles)
            result += triangle + "\n";

        return result;
    }
}
