package engine.threed;

import engine.math.FinalVector;
import engine.math.Matrix;
import engine.math.Quaternion;
import engine.math.Vector;
import engine.math.Vector4;

public class Camera 
{
    public Vector4 position;
    public Quaternion rotation;

    public double fov;
    public double aspectRatio;
    public double near;
    public double far;

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

        updateProjectionMatrix();
        updateViewMatrix(FinalVector.forward);
    }

    public Matrix viewMatrix      () { return viewMatrix      ; }
    public Matrix projectionMatrix() { return projectionMatrix; }
 
    public void updateProjectionMatrix()
    {
        projectionMatrix = Matrix.MakeProjection(fov, aspectRatio, near, far);
    }

    public void updateViewMatrix(Vector4 target)
    {
        target = position.plus(rotation.rotateVector(target));
        
        viewMatrix = Matrix.MakeView(position, target, FinalVector.up).quickInverse();
    }

    public String toString()
    {
        return getClass().getSimpleName() + "(position: " + position + ", rotation: " + rotation + ")";
    }
}
