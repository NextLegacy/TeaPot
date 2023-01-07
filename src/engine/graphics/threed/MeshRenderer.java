package engine.graphics.threed;

import engine.Script;

public class MeshRenderer extends Script
{
    private String layer;
    private Mesh mesh;
    
    public MeshRenderer()
    {
        setLayer(null); setMesh(null);
    }

    public Mesh   mesh () { return mesh ; }
    public String layer() { return layer; }

    public MeshRenderer setLayer(String layer) { this.layer = layer; return this; }
    public MeshRenderer setMesh (Mesh   mesh ) { this.mesh  = mesh ; return this; }

    @Override
    protected void render() 
    {
        if (image().isNot(layer)) return;
        if (mesh == null) return;
    }
}
