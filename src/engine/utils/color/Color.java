package engine.utils.color;

import static engine.utils.MathUtils.*;

import engine.utils.MathUtils;

public final class Color 
{
    //https://learnopengl.com/Advanced-OpenGL/Blending
    public static int mix(int argbB, int argbF)
    {
        if (argbF >>> 24 == 0xff) return argbF;

        final float t  = (argbF >>> 24) / 255f;
        final float t_ = 1f - t;

        return (int) (((argbF >>> 24)       ) * t + ((argbB >>> 24)       ) * t_) << 24 |
               (int) (((argbF >>> 16) & 0xFF) * t + ((argbB >>> 16) & 0xFF) * t_) << 16 |
               (int) (((argbF >>>  8) & 0xFF) * t + ((argbB >>>  8) & 0xFF) * t_) <<  8 |
               (int) (((argbF       ) & 0xFF) * t + ((argbB       ) & 0xFF) * t_)       ;

        //int a = (int) (((argbF >>> 24)       ) * t + ((argbB >>> 24)       ) * t_);
        //int r = (int) (((argbF >>> 16) & 0xFF) * t + ((argbB >>> 16) & 0xFF) * t_);
        //int g = (int) (((argbF >>>  8) & 0xFF) * t + ((argbB >>>  8) & 0xFF) * t_);
        //int b = (int) (((argbF       ) & 0xFF) * t + ((argbB       ) & 0xFF) * t_);
        //
        //return (a << 24) | (r << 16) | (g << 8) | (b);
    }

    public static int lerp(int[] argbs, double t)
    {
        //if (t == 0) return argbs[0];
        if (t == 1) t = 0.9999;// return argbs[1];

        double scaledTime = t * (double) (argbs.length - 1);

        int oldColor = argbs[(int) scaledTime];
        int newColor = argbs[(int) (scaledTime + 1)];

        double newT = scaledTime - MathUtils.floor(scaledTime);

        return Color.lerp(newColor, oldColor, newT);
    }

    public static int lerp(int argbA, int argbB, double t)
    {
        final int aA = (argbA >>> 24) & 0xff;
        final int rA = (argbA >>> 16) & 0xff;
        final int gA = (argbA >>>  8) & 0xff;
        final int bA = (argbA       ) & 0xff;

        final int aB = (argbB >>> 24) & 0xff;
        final int rB = (argbB >>> 16) & 0xff;
        final int gB = (argbB >>>  8) & 0xff;
        final int bB = (argbB       ) & 0xff;

        return (((int) MathUtils.lerp(aA, aB, t)) << 24) +
               (((int) MathUtils.lerp(rA, rB, t)) << 16) +
               (((int) MathUtils.lerp(gA, gB, t)) <<  8) +
               (((int) MathUtils.lerp(bA, bB, t))      );
    }
}
