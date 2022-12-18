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
        final int dx =  abs(x1 - x0);
        final int dy =  abs(y1 - y0);
         
        final int dxHalf = dx >> 1;
        final int dyHalf = dy >> 1;

        final int[] pixels = new int[abs(dy) + 1];
        
        int error = 0;
        int pixelsInTotal = 0;
        int pixelsInRow = 0;
        int row = 0;

        while (pixelsInTotal <= dx)
        {
            error = (pixelsInTotal * -dy + row * dx - dy + dx);
            
            error = (row + 1) * dx + (pixelsInTotal + 1) * -dy;

            if (error > dyHalf) { pixelsInRow++; pixelsInTotal++; }
            if (error < dxHalf) { pixels[row] = pixelsInRow; pixelsInRow = 0; row++; }
        }
        
        pixels[row] = pixelsInRow;

        System.out.println(Arrays.toString(pixels));

        return pixels;
    }    
}
