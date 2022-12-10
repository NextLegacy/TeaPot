package engine.utils;

import static engine.utils.MathUtils.*;

import java.util.ArrayList;

import engine.math.Vector4;
import engine.threed.Mesh;
import engine.threed.Polygon;
import engine.threed.Vertex;

public final class ThreedUtils 
{
    private ThreedUtils() { }

    public static Mesh MeshFromObjFile(String file)
    {
        String[] lines = FileUtils.getLines(file);

        ArrayList<Polygon> polygons = new ArrayList<>();

        ArrayList<Vector4> vertices = new ArrayList<>();
        ArrayList<Vector4> textures  = new ArrayList<>();
        ArrayList<Vector4> normals   = new ArrayList<>();

        for (int i = 0; i < lines.length; i++)
        {
            String line = lines[i];
            String lineArgs = line.substring(2).trim();
            String type = line.substring(0, 2);

            String[] args = lineArgs.split(" ");

            switch(type)
            {
                case "v ": vertices.add(fvec(toDouble(args[0]), toDouble(args[1]), toDouble(args[2])));  break;
                case "vt": textures .add(fvec(toDouble(args[0]), toDouble(args[1])                   )); break;
                case "vn": normals  .add(fvec(toDouble(args[0]), toDouble(args[1]), toDouble(args[2]))); break;

                case "f ":
                    Vertex[] polygonVertices = new Vertex[3];

                    for (int j = 0; j < 3; j++)
                    {
                        String[] faceArgs = args[j].split("/");
                        int[] indices = { toInt(faceArgs[0]), toInt(faceArgs[1]), toInt(faceArgs[2]) };

                        polygonVertices[j] = new Vertex(
                            vertices.get(indices[0]-1), 
                            textures .get(indices[1]-1),
                            normals  .get(indices[2]-1) 
                        );
                    }

                    polygons.add(new Polygon(polygonVertices[0], polygonVertices[1], polygonVertices[2]));

                    break;
            }
        }

        return new Mesh(polygons.toArray(Polygon[]::new));
    }
}
