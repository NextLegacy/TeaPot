package engine.math;

import static engine.utils.MathUtils.*;

import java.util.Locale;

public final class Matrix
{
    private static final int    NUMBER_STRING_DECIMALS      = 5;
    private static final int    NUMBER_STRING_DIGITS        = 5;
    private static final double NUMBER_STRING_MAX_VALUE     = (Math.pow(10.0d, NUMBER_STRING_DIGITS) - 1) + (1 - Math.pow(0.1d, NUMBER_STRING_DECIMALS));
    private static final String NUMBER_STRING_FORMAT        = "%." + NUMBER_STRING_DECIMALS + "f";
    private static final char   NUMBER_STRING_POSITIVE_CHAR = ' ';
    private static final char   NUMBER_STRING_NEGATIVE_CHAR = '-';
    private static final String MATRIX_STRING_BORDER_CHAR   = "-";

    private static final String NUMBER_STRING_MAX_VALUE_AS_STRING = String.format(NUMBER_STRING_FORMAT, NUMBER_STRING_MAX_VALUE);
    private static final int    NUMBER_STRING_MAX_VALUE_LENGTH = NUMBER_STRING_MAX_VALUE_AS_STRING.length();

    private static final int    MATRIX_STRING_ROW_MAX_LENGTH = (NUMBER_STRING_MAX_VALUE_LENGTH + 4) * 4 + 5;
    private static final String MATRIX_STRING_BORDER_STRING  = MATRIX_STRING_BORDER_CHAR.repeat(MATRIX_STRING_ROW_MAX_LENGTH);
    
    private static final int NUMBER_STRING_MAX_LENGTH = 1 + NUMBER_STRING_MAX_VALUE_LENGTH; // SIGN("-" | " ") + NUMBER

    public static final int ARRAY_LENGTH = 16;

    private String str_m;

    private final double[] m;

    public Matrix(double _0, double _1, double _2, double _3, double _4, double _5, double _6, double _7,double _8, double _9, double _10, double _11,double _12, double _13, double _14, double _15)
    {
        this(new double[] {
             _0,  _1,  _2,  _3, 
             _4,  _5,  _6,  _7,
             _8,  _9, _10, _11,
            _12, _13, _14, _15,
        });
    }
    
    public Matrix(double... content)
    {
        if (content.length != Matrix.ARRAY_LENGTH)
        {
            new RuntimeMatrixArrayFormatException(content).printStackTrace();
            content = Matrix.IDENTITY.m.clone();
        }
        
        str_m = "";
        m = content;
    }

    public Matrix()
    {
        this(
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        );
    }

    public Matrix times(Matrix matrix)
    {
        final double[] _m = matrix.m;

        return new Matrix(
            m[ 0] * _m[0] + m[ 1] * _m[4] + m[ 2] * _m[8] + m[ 3] * _m[12]  ,  m[ 0] * _m[1] + m[ 1] * _m[5] + m[ 2] * _m[9] + m[ 3] * _m[13]  ,  m[ 0] * _m[2] + m[ 1] * _m[6] + m[ 2] * _m[10] + m[ 3] * _m[14]  ,  m[ 0] * _m[3] + m[ 1] * _m[7] + m[ 2] * _m[11] + m[ 3] * _m[15],  
            m[ 4] * _m[0] + m[ 5] * _m[4] + m[ 6] * _m[8] + m[ 7] * _m[12]  ,  m[ 4] * _m[1] + m[ 5] * _m[5] + m[ 6] * _m[9] + m[ 7] * _m[13]  ,  m[ 4] * _m[2] + m[ 5] * _m[6] + m[ 6] * _m[10] + m[ 7] * _m[14]  ,  m[ 4] * _m[3] + m[ 5] * _m[7] + m[ 6] * _m[11] + m[ 7] * _m[15],  
            m[ 8] * _m[0] + m[ 9] * _m[4] + m[10] * _m[8] + m[11] * _m[12]  ,  m[ 8] * _m[1] + m[ 9] * _m[5] + m[10] * _m[9] + m[11] * _m[13]  ,  m[ 8] * _m[2] + m[ 9] * _m[6] + m[10] * _m[10] + m[11] * _m[14]  ,  m[ 8] * _m[3] + m[ 9] * _m[7] + m[10] * _m[11] + m[11] * _m[15],  
            m[12] * _m[0] + m[13] * _m[4] + m[14] * _m[8] + m[15] * _m[12]  ,  m[12] * _m[1] + m[13] * _m[5] + m[14] * _m[9] + m[15] * _m[13]  ,  m[12] * _m[2] + m[13] * _m[6] + m[14] * _m[10] + m[15] * _m[14]  ,  m[12] * _m[3] + m[13] * _m[7] + m[14] * _m[11] + m[15] * _m[15]
        );

        // return new Matrix(new double[] {
        //     m[0 * 4] * matrix.m[0] + m[0 * 4 + 1] * matrix.m[4] + m[0 * 4 + 2] * matrix.m[8] + m[0 * 4 + 3] * matrix.m[12]  ,  m[0 * 4] * matrix.m[1] + m[0 * 4 + 1] * matrix.m[4 + 1] + m[0 * 4 + 2] * matrix.m[8 + 1] + m[0 * 4 + 3] * matrix.m[12 + 1]  ,  m[0 * 4] * matrix.m[2] + m[0 * 4 + 1] * matrix.m[4 + 2] + m[0 * 4 + 2] * matrix.m[8 + 2] + m[0 * 4 + 3] * matrix.m[12 + 2]  ,  m[0 * 4] * matrix.m[3] + m[0 * 4 + 1] * matrix.m[4 + 3] + m[0 * 4 + 2] * matrix.m[8 + 3] + m[0 * 4 + 3] * matrix.m[12 + 3],  
        //     m[1 * 4] * matrix.m[0] + m[1 * 4 + 1] * matrix.m[4] + m[1 * 4 + 2] * matrix.m[8] + m[1 * 4 + 3] * matrix.m[12]  ,  m[1 * 4] * matrix.m[1] + m[1 * 4 + 1] * matrix.m[4 + 1] + m[1 * 4 + 2] * matrix.m[8 + 1] + m[1 * 4 + 3] * matrix.m[12 + 1]  ,  m[1 * 4] * matrix.m[2] + m[1 * 4 + 1] * matrix.m[4 + 2] + m[1 * 4 + 2] * matrix.m[8 + 2] + m[1 * 4 + 3] * matrix.m[12 + 2]  ,  m[1 * 4] * matrix.m[3] + m[1 * 4 + 1] * matrix.m[4 + 3] + m[1 * 4 + 2] * matrix.m[8 + 3] + m[1 * 4 + 3] * matrix.m[12 + 3],  
        //     m[2 * 4] * matrix.m[0] + m[2 * 4 + 1] * matrix.m[4] + m[2 * 4 + 2] * matrix.m[8] + m[2 * 4 + 3] * matrix.m[12]  ,  m[2 * 4] * matrix.m[1] + m[2 * 4 + 1] * matrix.m[4 + 1] + m[2 * 4 + 2] * matrix.m[8 + 1] + m[2 * 4 + 3] * matrix.m[12 + 1]  ,  m[2 * 4] * matrix.m[2] + m[2 * 4 + 1] * matrix.m[4 + 2] + m[2 * 4 + 2] * matrix.m[8 + 2] + m[2 * 4 + 3] * matrix.m[12 + 2]  ,  m[2 * 4] * matrix.m[3] + m[2 * 4 + 1] * matrix.m[4 + 3] + m[2 * 4 + 2] * matrix.m[8 + 3] + m[2 * 4 + 3] * matrix.m[12 + 3],  
        //     m[3 * 4] * matrix.m[0] + m[3 * 4 + 1] * matrix.m[4] + m[3 * 4 + 2] * matrix.m[8] + m[3 * 4 + 3] * matrix.m[12]  ,  m[3 * 4] * matrix.m[1] + m[3 * 4 + 1] * matrix.m[4 + 1] + m[3 * 4 + 2] * matrix.m[8 + 1] + m[3 * 4 + 3] * matrix.m[12 + 1]  ,  m[3 * 4] * matrix.m[2] + m[3 * 4 + 1] * matrix.m[4 + 2] + m[3 * 4 + 2] * matrix.m[8 + 2] + m[3 * 4 + 3] * matrix.m[12 + 2]  ,  m[3 * 4] * matrix.m[3] + m[3 * 4 + 1] * matrix.m[4 + 3] + m[3 * 4 + 2] * matrix.m[8 + 3] + m[3 * 4 + 3] * matrix.m[12 + 3]
        // });
    }

    public Vector times(Vector4 vector)
    {
        return vec(
            vector.x() * m[ 0] + vector.y() * m[ 4] + vector.z() * m[ 8] + vector.w() * m[12],
            vector.x() * m[ 1] + vector.y() * m[ 5] + vector.z() * m[ 9] + vector.w() * m[13],
            vector.x() * m[ 2] + vector.y() * m[ 6] + vector.z() * m[10] + vector.w() * m[14],
            vector.x() * m[ 3] + vector.y() * m[ 7] + vector.z() * m[11] + vector.w() * m[15]
        );
    }

    public Matrix quickInverse()
    {
        return new Matrix(
            m[ 0]                                           , m[ 4]                                           , m[ 8]                                           , 0,
            m[ 1]                                           , m[ 5]                                           , m[ 9]                                           , 0,
            m[ 2]                                           , m[ 6]                                           , m[10]                                           , 0,
            -(m[12] * m[ 0] + m[13] * m[ 1] + m[14] * m[ 2]), -(m[12] * m[ 4] + m[13] * m[ 5] + m[14] * m[ 6]), -(m[12] * m[ 8] + m[13] * m[ 9] + m[14] * m[10]), 1
        );
    }

    public static Matrix makeLookAt(Vector4 pos, Vector4 target, Vector4 up)
    {
        Vector newForward = target.minus(pos).normalize();

        Vector newUp = up.minus(newForward.times(up.dot(newForward))).normalize();

        Vector newRight = newUp.cross(newForward);

        Matrix lookAtMatrix = new Matrix(
            newRight.x  (), newRight.y  (), newRight.z  (), 0 ,
            newUp.x     (), newUp.y     (), newUp.z     (), 0 ,
            newForward.x(), newForward.y(), newForward.z(), 0 ,
            pos.x       (), pos.y       (), pos.z       (), 1 
        );

        return lookAtMatrix.quickInverse();
    }

    public static Matrix makeProjection(double fovDeg, double aspectRatio, double near, double far)
    {
        double fovRad = 1 / Math.tan(fovDeg * 0.5 / DEGREE);
        
        return new Matrix(
            aspectRatio * fovRad , 0      , 0                            , 0 ,
            0                    , fovRad , 0                            , 0 ,
            0                    , 0      , far / (far - near)           , 1 ,
            0                    , 0      , (-far * near) / (far - near) , 0
        );
    }

    public static Matrix MakeRotation(Vector4 euler)
    {
        return Matrix.MakeRotation(Quaternion.FromEuler(euler));
    }

    public static Matrix MakeRotation(Quaternion quaternion)
    {
        return quaternion.matrix();
    }

    public static Matrix MakeTranslation(Vector4 v)
    {
        return new Matrix(
            1    , 0    , 0    , 0,
            0    , 1    , 0    , 0, 
            0    , 0    , 1    , 0,
            v.x(), v.y(), v.z(), 1
        );
    }

    public static Matrix MakeScale(Vector4 v)
    {
        return new Matrix(
            v.x(), 0    , 0    , 0,
            0    , v.y(), 0    , 0, 
            0    , 0    , v.z(), 0,
            0    , 0    , 0    , 1
        );
    }

    public static Matrix MakeTransformation(Matrix translation, Matrix rotation, Matrix scale)
    {
        return scale.times(rotation).times(translation);
    }

    public static Matrix MakeTransformation(Vector4 position, Vector4 scale, Quaternion rotation)
    {
        Matrix T = Matrix.MakeTranslation(position);
        Matrix S = Matrix.MakeScale(scale);
        Matrix R = Matrix.MakeRotation(rotation);

        return S.times(R).times(T);
    }

    public static Matrix MakeTransformation(Vector4 position, Vector4 scale, Vector4 euler)
    {
        Matrix T = Matrix.MakeTranslation(position);
        Matrix S = Matrix.MakeScale(scale);
        Matrix R = Matrix.MakeRotation(euler);

        return S.times(R).times(T);
    }

    public static Matrix MakeProjection(double fovDeg, double aspectRatio, double near, double far)
    {
        double fovRad = 1 / Math.tan(fovDeg * 0.5 / DEGREE);
        
        return new Matrix(
            aspectRatio * fovRad , 0      , 0                            , 0 ,
            0                    , fovRad , 0                            , 0 ,
            0                    , 0      , far / (far - near)           , 1 ,
            0                    , 0      , (-far * near) / (far - near) , 0
        );
    }

    public static Matrix MakeOrthographic(double left, double right, double bottom, double top, double near, double far)
    {
        return new Matrix(
            2 / (right - left)             , 0                              , 0                          , 0 ,
            0                              , 2 / (top - bottom)             , 0                          , 0 ,
            0                              , 0                              , 2 / (near - far)           , 0 ,
            (left + right) / (left - right), (bottom + top) / (bottom - top), (far + near) / (far - near), 1
        );
    }

    public static Matrix MakeView(Vector4 pos, Vector4 target, Vector4 up)
    {
        return MakeLookAt(pos, target, up).quickInverse();
    }

    public static Matrix MakeLookAt(Vector4 pos, Vector4 target, Vector4 up)
    {
        Vector newForward = target.minus(pos).normalize();

        Vector newUp = up.minus(newForward.times(up.dot(newForward))).normalize();

        Vector newRight = newUp.cross(newForward);

        Matrix lookAtMatrix = new Matrix(
            newRight.x  (), newRight.y  (), newRight.z  (), 0 ,
            newUp.x     (), newUp.y     (), newUp.z     (), 0 ,
            newForward.x(), newForward.y(), newForward.z(), 0 ,
            pos.x       (), pos.y       (), pos.z       (), 1 
        );

        return lookAtMatrix;
    }

    public boolean equals(double[] m)
    {
        for (int i = 0; i < Matrix.ARRAY_LENGTH; i++) if (m[i] != m[i]) 
            return false;

        return true;
    }

    public boolean equals(Matrix matrix) { return equals(matrix.m); }

    @Override
    public boolean equals(Object object) 
    {
        return object == null ? false : 
               object == this                                          ||
               ((object instanceof Matrix   matrix) && equals(matrix)) ||
               ((object instanceof double[] array ) && equals(array ));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private String createMatrixString()
    {
        String str = "";

        str += MATRIX_STRING_BORDER_STRING + "";

        for (int i = 0; i < Matrix.ARRAY_LENGTH; i++)
        {
            boolean negative = m[i] < -EPSILON;

            System.out.println(negative + "  -  " + m[i]);

            //Set value to Number in Intervale [0 | Matrix.NUMBER_STRING_MAX_VALUE]
            String value = String.format(Locale.ROOT, "%." + Matrix.NUMBER_STRING_DECIMALS + "f",
                m[i] > Matrix.NUMBER_STRING_MAX_VALUE || m[i] < -Matrix.NUMBER_STRING_MAX_VALUE ? Matrix.NUMBER_STRING_MAX_VALUE : m[i]
            );

            value = value.replaceAll("-", "");
            
            value = (negative ? Matrix.NUMBER_STRING_NEGATIVE_CHAR : Matrix.NUMBER_STRING_POSITIVE_CHAR) + " ".repeat(NUMBER_STRING_MAX_LENGTH - value.length()) + value;

            if (i % 4 == 0) str += "\n| ";
            str += value + " | ";
        }

        str += "\n" + MATRIX_STRING_BORDER_STRING;

        return str;
    }

    @Override 
    public String toString() 
    {
        return str_m == "" ? str_m = createMatrixString() : str_m; 
    }

    public static final Matrix IDENTITY = new Matrix( 
        1, 0, 0, 0,
        0, 1, 0, 0,
        0, 0, 1, 0,
        0, 0, 0, 1
    );

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private final class RuntimeMatrixArrayFormatException extends RuntimeException
    {
        RuntimeMatrixArrayFormatException(final double[] matrix) 
        { 
            super("Length of matrix array is " + matrix.length + ". But must be equal to " + Matrix.ARRAY_LENGTH + ".");
        }
    }
}
