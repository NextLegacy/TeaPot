package engine.utils.color;

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
}
