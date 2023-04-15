package engine.threed;

import engine.math.Vector4;
import engine.utils.FileUtils;

import static engine.utils.MathUtils.*;
import static engine.utils.ArrayUtils.*;

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
    
    // TODO: optimize this
    /**
     * Creates a mesh from an obj file
     * 
     * @param file The file to load
     * @return The mesh
     */
    public static Mesh FromObjFile(String file)
    {
        String[] lines = FileUtils.getLines(file);

        Triangle[] polygons = new Triangle[0];

        Vector4[] vertices = new Vector4[0];
        Vector4[] textures = new Vector4[0];
        Vector4[] normals  = new Vector4[0];

        for (int i = 0; i < lines.length; i++)
        {
            String line = lines[i];
            String lineArgs = line.substring(2).trim();
            char[] type = line.substring(0, 2).toCharArray();

            String[] args = lineArgs.split(" ");

            if (type[0] == 'v')
                pushVertexData
                (
                    type[1] == ' ' ? vertices : 
                    type[1] == 't' ? textures : 
                    type[1] == 'n' ? normals  : null, 
                    args
                );

            if (type[0] == 't')
            {
                Vertex[] polygonVertices = new Vertex[3];

                for (int j = 0; j < 3; j++)
                {
                    String[] faceArgs = args[j].split("/");
                    int[] indices = { toInt(faceArgs[0]), toInt(faceArgs[1]), toInt(faceArgs[2]) };

                    polygonVertices[j] = new Vertex(
                        vertices [indices[0]-1], 
                        textures [indices[1]-1],
                        normals  [indices[2]-1] 
                    );
                }

                polygons = push(polygons, new Triangle(polygonVertices[0], polygonVertices[1], polygonVertices[2]));
            }
        }

        return new Mesh(polygons);
    }

    private static Vector4[] pushVertexData(final Vector4[] array, final String[] args)
    {
        return push(array, fvec(toDouble(args[0]), toDouble(args[1]), toDouble(args[2])));
    }
}
