package engine.threed;

public final class Mesh 
{
    public final Polygon[] polygons;
    
    public Mesh(Polygon[] polygons)
    {
        this.polygons = polygons;
    }

    public String toString()
    {
        String str = "";

        for (Polygon polygon : polygons)
            str += polygon + "\n";

        return str;
    }
}
