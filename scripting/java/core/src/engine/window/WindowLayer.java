package engine.window;

import engine.math.Vector4;
import engine.graphics.DrawableImage;

/**
 * This class represents a layer in a window. <p>
 * 
 * It extends {@link DrawableImage} with the addition of a name for 
 * proper identification. <p>
 * 
 * @author NextLegacy
 * @version 1.0
 */
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
