package engine.graphics.threed;

public final class Polygon 
{
    public final Vertex a;
    public final Vertex b;
    public final Vertex c;

    public Polygon(Vertex a, Vertex b, Vertex c)
    {
        this.a = a; this.b = b; this.c = c;
    }

    public String toString()
    {
        return "Polygon(" + a + " \n        " + b + " \n        " + c + ")";
    }
}
