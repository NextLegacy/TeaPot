package engine.utils;

import static engine.utils.MathUtils.*;

import engine.math.Vector;

public class GraphicsUtils 
{
    public static int[][] bresenham(int x0, int y0, int x1, int y1)  
    {
        double dx = Math.abs(x1 - x0);
        double dy = -Math.abs(y1 - y0);

        final double sx = x0 < x1 ? 1 : -1;
        final double sy = y0 < y1 ? 1 : -1;

        double err = dx + dy;
        double e2 = 0;
        
        // this will always be more or equal to the actuall count
        // + 1 for the special case of a line form (0, 0) - (0, 0) and (0, 0) - (1, 1)
        int countOfPixels = round(Math.sqrt(dx*dx + dy*dy)) + 1;

        int count = 0;
    
        int[][] pixels = new int[countOfPixels][2];

        while (true) 
        {
            pixels[count][0] = x0;
            pixels[count][1] = y0;

            count++;
            
            if (x0 == x1 && y0 == y1) break;

            e2 = 2 * err;
            
            if (e2 > dy) { err += dy; x0 += sx; }
            if (e2 < dx) { err += dx; y0 += sy; }
        }

        int[][] result = new int[count][2];

        System.arraycopy(pixels, 0, result, 0, count);

        return result;
    }    
}
