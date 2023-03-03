package engine.utils.raycast;

import engine.math.Vector4;

/**
 * This class represents a ray in 3D space.
 * 
 * @author NextLegacy
 */
public class Ray 
{
    private Vector4 origin;
    private Vector4 direction;

    public Ray(Vector4 origin, Vector4 direction)
    {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector4 origin    () { return origin    ; }
    public Vector4 direction () { return direction ; }
}
