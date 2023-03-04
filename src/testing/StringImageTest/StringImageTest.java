package testing.StringImageTest;

import java.awt.Font;

import engine.graphics.string.StringImage;

public class StringImageTest
{
    public static void main(String[] args) 
    { 
        StringImage stringImage = new StringImage("Hello World!", new Font("Arial", Font.PLAIN, 12));

        stringImage.export("./test.png", ".png");
    }
}
