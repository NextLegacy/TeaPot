package engine.utils;

import static engine.utils.MathUtils.*;

import java.util.Arrays;

import engine.math.Vector;

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
     * |slope| = 0 than => [0, dx] or [0, dy] or [dx] or [dy]
     * |slope| = 1 than => [1]
     * |slope| < 1 than => [2, pir1, pir2, pir3] (pir = pixels in row   ) 
     * |slope| > 1 than => [3, pic1, pic2, pic3] (pir = pixels in column)
     * 
     * [type, pixels]
     */
    public static int[] bresenham(int x0, int y0, int x1, int y1)
    { 
        int dx = abs(x1 - x0);
        int dy = abs(y1 - y0);

        // [slope, pixelsInRow_1, pixelsInRow_2, ...]
        final int[] result = new int[(dx > dy ? abs(dy) : abs(dx)) + 1 + 1]; //plus extra one for slope

        //handle straight lines
        if (dx == 0 || dy == 0)
        {
            if      (dx == 0 && dy == 0) result[1] = 1 ;
            else if (dx == 0           ) result[1] = dy;
            else /*  dy == 0          */ result[1] = dx;

            result[0] = 0;

            return result;
        }

        if (dx == dy)
        {
            return new int[] { 0 };
        }

        final double slope = dx / dy;
 
        result[0] = (int) slope;

        //final int sx = x0 < x1 ? 1 : -1;
        //final int sy = y0 < y1 ? 1 : -1;

        int err = dx + dy;
        int e2 = 0;

        int pixels = 0;
        int index = 1; // 1 because of the extra index for the slope

        
        if (slope < 1 && slope > -1)
        {   
            while (true) 
            {
                result[index] = ++pixels;
    
                if (dx > dy ? pixels >= dx : pixels >= dy) break;
    
                e2 = 2 * err;
    
                if (e2 > -dy) 
                { 
                    err -= dy; 
                }
    
                if (e2 < dx)
                { 
                    err += dx;
                    index++;
                }
            }
        } else {
            while (true) 
            {
                result[index] = ++pixels;
    
                if (dx > dy ? pixels >= dx : pixels >= dy) break;
    
                e2 = 2 * err;
    
                if (e2 > -dy) 
                { 
                    err -= dy; 
                }
    
                if (e2 < dx)
                { 
                    index++;
                    err += dx;
                }
            }
        }

        //System.out.println(Arrays.toString(result));

        return result;
    }   
}
