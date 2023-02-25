package engine.utils;

import static engine.utils.MathUtils.*;

public class GraphicsUtils 
{
    /*
     *
     * result equlas different arrays in different situations
     * situations are dependent on the slope of the array
     * if it is straigt than it is equal to => [0, dx] or [0, dy]
     * if it is a point it is equal to      => [0] (or prehaps even [] or [1])
     * if its slope is exactly 1            => [1] (or something different) no need for more data here
     * if it is a line with the property |slope| < 1 than => [2, pir1, pir2, pir3] (pir = pixels in row   ) (pir unprecice, its more like pixels till this row (row inclusive))
     * if it is a line with the property |slope| > 1 than => [3, pic1, pic2, pic3] (pir = pixels in column)  
     * 
     * |slope| = 0 than => if dy && dx == 0 than [0] if dy == 0 than [1, dx] else [2, dy]
     * |slope| < 1 than => [1, pir1, pir2, pir3] (pir = pixels in row   ) 
     * |slope| > 1 than => [2, pic1, pic2, pic3] (pir = pixels in column)
     * |slope| = 1 than => [3, dx] or [3, dy] (no difference)
     * 
     * [type, pixels...]
     */
    public static int[] bresenham(int x0, int y0, int x1, int y1)
    { 
        final int dx = abs(x1 - x0);
        final int dy = abs(y1 - y0);

        //handle lines with the property slope = 1
        if (dx == dy) 
            if   (  dx      == 0  ) return new int[] { 0     }; // if line is just a pixel
            else /* |slope| == 1 */ return new int[] { 3, dx };

        if (dx == 0) return new int[] { 2, dy }; // if line is vertical
        if (dy == 0) return new int[] { 1, dx }; // if line is horizontal

        //slightly modified bresenham algorithm

        final int longerSide  = dx > dy ? dx : dy;
        final int shorterSide = dx < dy ? dx : dy;

        // [type, pixels...]
        final int[] result = new int[shorterSide + 1 + 1 + 1 + 1]; //plus extra one for slope, and the others, idk realy

        final float slope = (float) dx / (float) dy;
        final float abs_slope = abs(slope);

        int err    = dx - dy;
        int e2     = 0      ;
        int pixels = 0      ;
        int index  = 1      ; // 1 because of the extra index for the slope

        result[0] = abs_slope <= 1 ? 1 : 2;

        if (abs_slope <= 1)
        {   
            while (true) 
            {
                e2 = 2 * err;
    
                if (e2 > -dy) 
                { 
                    err -= dy;
                    result[index] = pixels;                 
                    index++;
                }
    
                if (e2 < dx)
                { 
                    err += dx;
                    pixels++;
                    if (pixels >= longerSide) break;
                }
            }
        } 
        else 
        {
            while (true) 
            {
                e2 = 2 * err;
    
                if (e2 > -dy) 
                { 
                    err -= dy; 
                    pixels++;
                    if (pixels >= longerSide) break;
                }
    
                if (e2 < dx)
                { 
                    err += dx;
                    result[index] = pixels; 
                    index++;
                }
            }
        }

        result[index] = pixels;

        return result;
    }
}
