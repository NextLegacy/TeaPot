package engine.math;

public final class Transform
{
    private Matrix transformationMatrix;

    private Matrix translationMatrix;
    private Matrix rotationMatrix;
    private Matrix scaleMatrix;

    private Vector     lastPosition;
    private Quaternion lastRotation;
    private Vector     lastScale;

    public final Vector     position;
    public final Quaternion rotation;
    public final Vector     scale;

    public Transform()
    {
        position = Vector.zero();
        rotation = Quaternion.zero;
        scale    = Vector.zero();
    }

    public final Matrix transformationMatrix()
    {
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
}