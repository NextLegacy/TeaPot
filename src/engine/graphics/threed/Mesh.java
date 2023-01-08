package engine.graphics.threed;

public final class Mesh 
{
    public final Triangle[] polygons;
    
    public Mesh(Triangle[] polygons)
    {
        this.polygons = polygons;
    }

    public String toString()
    {
        String str = "";

        for (Triangle polygon : polygons)
            str += polygon + "\n";

        return str;
    }
}
