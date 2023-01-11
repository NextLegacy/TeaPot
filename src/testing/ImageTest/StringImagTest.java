package testing.ImageTest;

import java.awt.Font;

import engine.graphics.StringImage;
import engine.utils.time.Time;

public class StringImagTest 
{
    public static void main(String[] args)
    {
        System.out.println(Time.measureTime(StringImagTest::testStringImage) * Time.NANOS_TO_SECONDS);
    }

    public static void testStringImage()
    {
        StringImage stringImage = new StringImage(
            "Hello World\nTest Test Test\n42\n\nTestTest",
            new Font("Cascadia Code", Font.BOLD, 500)
        );

        stringImage.export("rsc/out.png", "png");
    }
}
