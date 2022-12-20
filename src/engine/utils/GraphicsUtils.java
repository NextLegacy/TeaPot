package engine.utils;

import static engine.utils.MathUtils.*;

import java.util.Arrays;

import engine.math.Vector;

public class GraphicsUtils 
{
    /*
     * TODO:
     * Return a two dimensional array that only contains the y values,
     * the x value will be determined through the index 
     * 
     * example:
     * Given is the following line: 
 
     * [][]
     *     [][]
     *         [][] 
     *
     * This would be the array for it:
     * 
     * [
     *     [1, 2] // x = 0
     *     [3, 4] // x = 1
     *     [5, 6] // x = 2
     * ]
     * 
     * otherwise the array does look like this:
     * 
     * [
     *     [0, 1], // p = (0, 1)
     *     [0, 2], // p = (0, 2)
     *     [1, 3], // p = (1, 3)
     *     [1, 4], // p = (1, 4)
     *     [2, 5], // p = (2, 5 )
     *     [2, 6], // p = (2, 6)
     * ] 
     * 
     * Which does take up twice as much memory
     * 
     */
    public static int[] bresenham(int x0, int y0, int x1, int y1)
    { 
        int dx =  abs(x1 - x0);
        int dy = -abs(y1 - y0);

        final int[] result = new int[abs(dy) + 1];

        //handle straight lines
        if (dx == 0 || dy == 0)
        {
            if      (dx == 0 && dy == 0) result[0] = 1;
            else if (dx == 0           ) ArrayUtils.fill(result, 1);
            else /*  dy == 0          */ result[0] = dx;

            return result;
        }

        final int sx = x0 < x1 ? 1 : -1;
        final int sy = y0 < y1 ? 1 : -1;
        
        int err = dx + dy;
        int e2 = 0;

        int row = 0;
        int pixelsInRow = 0;

        while (true) 
        {
            pixelsInRow++;

            if (x0 == x1 && y0 == y1) break;

            e2 = 2 * err;

            if (e2 > dy) 
            { 
                err += dy; 
                x0 += sx;  
            }

            if (e2 < dx)
            { 
                err += dx;
                y0 += sy;
                result[row] = pixelsInRow;
                pixelsInRow = 0;
                row++; 
            }
        }

        result[row] = pixelsInRow;

        System.out.println(Arrays.toString(result));

        return result;
    }   
}
