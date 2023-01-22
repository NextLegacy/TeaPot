package engine.threed;

import engine.math.FinalVector;
import engine.math.Matrix;
import engine.math.Quaternion;
import engine.math.Vector;

public class Camera 
{
    public Vector position;
    public Quaternion rotation;

    public double fov;
    public double aspectRatio;
    public double near;
    public double far;

    private Matrix lookAtMatrix;
    private Matrix viewMatrix;
    private Matrix projectionMatrix;
    
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
        viewMatrix = lookAtMatrix.quickInverse();
    }

    public Matrix lookAtMatrix    () { return lookAtMatrix    ; }
    public Matrix viewMatrix      () { return viewMatrix      ; }
    public Matrix projectionMatrix() { return projectionMatrix; }
 
    public void updateProjectionMatrix()
    {
        projectionMatrix = Matrix.MakeProjection(fov, aspectRatio, near, far);
    }

    public String toString()
    {
        return getClass().getSimpleName() + "(position: " + position + ", rotation: " + rotation + ")";
    }
}
