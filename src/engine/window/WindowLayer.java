package engine.window;

import engine.math.Vector4;
import engine.utils.graphics.DrawableImage;

public final class WindowLayer extends DrawableImage
{
    private final String name;

    WindowLayer(String name, Vector4 size)
    {
        super(size);
        this.name = name; 
    }
    
    public String name() { return name; }

    public boolean is   (String name) { return this.name == name; }
    public boolean isNot(String name) { return this.name != name; }
}
