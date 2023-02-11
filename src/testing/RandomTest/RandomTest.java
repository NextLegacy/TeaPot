package testing.RandomTest;

import engine.graphics.DrawableImage;
import engine.math.Vector;
import engine.utils.logger.Logger;
import engine.utils.random.Random;

public class RandomTest 
{
    public static void main(String[] args) 
    {
        Random random = new Random(new Random.splitmix64(0));

        //Logger logger = new Logger(System.out::print);

        DrawableImage image = new DrawableImage(new Vector(1080, 720));

        for (int x = 0; x < 1080; x++)
        {
            for (int y = 0; y < 720; y++)
            {
                int color = random.randomInt(0, 0xffffff);

                image.drawPixel(x, y, 0, color | 0xff000000);
            }
        }

        image.export("random.png", "png");
    }
}
