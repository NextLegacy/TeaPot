package engine.utils.color;

import engine.utils.MathUtils;

/**
 * Static utility methods for working with colors in the ARGB format.
 * 
 * @author NextLegacy
 */
public final class Color 
{
    private Color() {}  

    //https://learnopengl.com/Advanced-OpenGL/Blending
    /**
     * This method blends two argb colors using a linear interpolation algorithm. <p>
     * If the foreground color is fully opaque, it will be returned. <p>
     * 
     * @param argbB The background color.
     * @param argbF The foreground color.
     * 
     * @return the resulting argb color value after blending the two colors.
     */
    public static int mix(int argbB, int argbF)
    {
        if (argbF >>> 24 == 0xff) return argbF;

        final float t  = (argbF >>> 24) / 255f;
        final float t_ = 1f - t;

        return (int) (((argbF >>> 24)       ) * t + ((argbB >>> 24)       ) * t_) << 24 |
               (int) (((argbF >>> 16) & 0xFF) * t + ((argbB >>> 16) & 0xFF) * t_) << 16 |
               (int) (((argbF >>>  8) & 0xFF) * t + ((argbB >>>  8) & 0xFF) * t_) <<  8 |
               (int) (((argbF       ) & 0xFF) * t + ((argbB       ) & 0xFF) * t_)       ;
    }

    /**
     * This method blends two argb colors using a linear interpolation algorithm. <p>
     * @param argbB The background color.
     * @param argbF The foreground color.
     * @param t The interpolation factor.
     * @return the resulting argb color value after blending the two colors.
     */
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

    /**
     * This method blends two argb colors using a linear interpolation algorithm. <p>
     * 
     * @param argbA The first color.
     * @param argbB The second color.
     * @param t The interpolation factor.
     * @return the resulting argb color value after blending the two colors.
     */
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
