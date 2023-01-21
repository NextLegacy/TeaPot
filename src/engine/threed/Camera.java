package engine.threed;

import engine.math.FinalVector;
import engine.math.Matrix;
import engine.math.Quaternion;
import engine.math.Vector;

public class Camera 
{
    public Vector position;
    public Quaternion rotation;
    
    private double fov;
    private double aspectRatio;
    private double near;
    private double far;

    private Matrix lookAtMatrix;
    
    public Camera(double fov, double aspectRatio, double near, double far)
    {
        position = Vector.zero();
        rotation = Quaternion.IDENTITY;

        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.near = near;
        this.far = far;
    }

    public void lookAt(Vector target)
    {
        target = position.plus(rotation.rotateVector(target));

        lookAtMatrix = Matrix.MakeLookAt(position, target, FinalVector.up);
    }

    public Matrix getProjectionMatrix()
    {
        return Matrix.MakeProjection(fov, aspectRatio, near, far);
    }


    public String toString()
    {
        return getClass().getSimpleName() + "(position: " + position + ", rotation: " + rotation + ")";
    }
}
