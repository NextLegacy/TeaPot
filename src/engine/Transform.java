package engine;

import engine.math.Matrix;
import engine.math.Quaternion;
import engine.math.Vector;
import engine.utils.destroyable.IDestroyable;

public final class Transform implements IDestroyable
{
    private boolean destroyed;

    private Matrix transformationMatrix;

    private Matrix translationMatrix;
    private Matrix rotationMatrix;
    private Matrix scaleMatrix;

    private Vector     lastPosition;
    private Quaternion lastRotation;
    private Vector     lastScale;

    public Vector     position;
    public Quaternion rotation;
    public Vector     scale;

    public Transform()
    {
        init();
    }
    
    public final Matrix transformationMatrix()
    {
        TransformIsDestroyedException.throwIfIsDestroyed(this);

        final boolean positionChanged = position != lastPosition;
        final boolean rotationChanged = rotation != lastRotation;
        final boolean scaleChanged    = scale    != lastScale   ;
 
        final boolean changed = positionChanged || rotationChanged || scaleChanged;

        if (positionChanged) { lastPosition = position; translationMatrix = Matrix.MakeTranslation(position); } 
        if (rotationChanged) { lastRotation = rotation; rotationMatrix = Matrix.MakeRotation(rotation); }
        if (scaleChanged   ) { lastScale    = scale   ; scaleMatrix = Matrix.MakeScale(scale); }

        if (changed) transformationMatrix = Matrix.MakeTransformation(translationMatrix, rotationMatrix, scaleMatrix);

        return transformationMatrix;
    }

    void init()
    {
        destroyed = false;
        position = Vector.zero();
        rotation = Quaternion.zero;
    }

    @Override
    public void destroy()
    {
        destroyed = true;

        transformationMatrix = translationMatrix = rotationMatrix = scaleMatrix = null;

        lastPosition = lastScale = position = scale = null;

        lastRotation = rotation = null;
    }

    @Override
    public boolean isDestroyed() { return destroyed; }

    private static final class TransformIsDestroyedException extends RuntimeException
    {
        TransformIsDestroyedException(Transform transform)
        {
            super(transform + " is destroyed.");
        }

        private static void throwIfIsDestroyed(Transform transform)
        {
            if (transform.isDestroyed())
                throw new TransformIsDestroyedException(transform);
        }
    }
}