package engine.graphics;

final class ImageAlgorithms 
{
    private ImageAlgorithms() { }
    
    static void line(final DrawableImage image,
        final int x0, final int y0, final int x1, final int y1, final double z, final int color)
    {
        final double dx =  Math.abs(x1 - x0);
        final double dy = -Math.abs(y1 - y0);
     
        final double sx = x0 < x1 ? 1 : -1;
        final double sy = y0 < y1 ? 1 : -1;

        double err = dx + dy;
        double e2 = 0;
        
        int x = x0;
        int y = y0;

        while (true)
        {
            image.drawPixel(x, y, z, color);

            if (x == x1 && y == y1) break;

            e2 = 2 * err;
            
            if (e2 > dy) { err += dy; x += sx; }
            if (e2 < dx) { err += dx; y += sy; }
        }
    }

    static void rect(final DrawableImage image,
        final int x0, final int y0, final int x1, final int y1, final double z, final int color)
    {
        if (x0 == x1 && y0 == y1) { image.drawPixel(x0, y0, z, color); return; }

        final int imageWidth  = image.width ();
        final int imageHeight = image.height();

        final int biggerX  = x0 > x1 ? x0 : x1;
        final int biggerY  = y0 > y1 ? y0 : y1;
        final int smallerX = x0 < x1 ? x0 : x1;
        final int smallerY = y0 < y1 ? y0 : y1;

        int x = smallerX;
        int y = smallerY;

        //w and h are just the width and height of the Rectangle, width and height are the clipped version of this
        int w = biggerX - smallerX;
        int h = biggerY - smallerY;        

        //Not a single Pixel is inside the Bounds of this
        if (x >= imageWidth || y >= imageHeight || x <= -w || y <= -h) 
            return;

        //width and height, that lies inside the Bounds of this
        int width  = (x + w > imageWidth  ? imageWidth  - x : w) + (x < 0 ? x : 0);
        int height = (y + h > imageHeight ? imageHeight - y : h) + (y < 0 ? y : 0);

        //Start Index
        int thisStartIndex = (x < 0 ? 0 : x) + (y < 0 ? 0 : y) * imageWidth;

        //Needed in the While-Loop, to avoid usage of repetitive multiplication (y * any_width = any_height). 
        //It may be faster, for that reason its here.
        int thisHeight = 0;

        //Last Index in current line
        int thisMaxWidthIndex = thisStartIndex + width;
        //Last Index that has to be filled
        int thisMaxIndex = thisStartIndex + height * imageWidth;

        //Index of this that is gonna be drawn on
        int thisIndex = thisStartIndex;

        while(true)
        {
            //draw Pixel
            image.drawPixel(thisIndex, z, color);
            
            thisIndex++;

            if (thisIndex == thisMaxWidthIndex) 
            {
                //Increase Height by one (width of Rectangle)
                thisHeight += imageWidth; 

                //Reset Indices to most left point shifted down by current Height
                thisIndex = thisStartIndex  + thisHeight;

                //Set new last point in line
                thisMaxWidthIndex = thisIndex + width;

                //If reached last Index, step out of loop
                if (thisIndex == thisMaxIndex) break;
            }
        }
    }

    public static void drawImage(final DrawableImage graphicsImage, 
        final Image image, final int x, final int y)
    {
        final int graphicsImageWidth  = image.width ();
        final int graphicsImageHeight = image.height();

        //Not a single Pixel is inside the Bounds of this
        if (x >= graphicsImageWidth || y >= graphicsImageHeight || x <= -image.width() || y <= -image.height()) 
            return;

        //width and height, that lies inside the Bounds of this
        int width  = (x + image.width()  > graphicsImageWidth  ? graphicsImageWidth  - x : image.width() ) + (x < 0 ? x : 0);
        int height = (y + image.height() > graphicsImageHeight ? graphicsImageHeight - y : image.height()) + (y < 0 ? y : 0);

        //Start Index, for image and this
        int imageStartIndex = (x < 0 ? -x : 0) + (y < 0 ? -y : 0) * image.width();
        int thisStartIndex  = (x < 0 ?  0 : x) + (y < 0 ?  0 : y) * graphicsImageWidth;

        //Needed in the While-Loop, to avoid usage of repetitive multiplication (y * any_width = any_height (in which any means image or this)). 
        //It may be faster, for that reason its here.
        int imageHeight = 0;
        int thisHeight  = 0;

        //Last Index in current line
        int imageMaxWidthIndex = imageStartIndex + width;
        //Last Index that has to be filled
        int imageMaxIndex = imageStartIndex + height * image.width();
        
        //Index of Image that is gonna be used to get Color and ZValue
        int imageIndex = imageStartIndex;
        //Index of this that is gonna be drawn on
        int thisIndex  = thisStartIndex;

        while(true)
        {
            //draw Pixel
            graphicsImage.drawPixel(thisIndex, image.zBuffer[imageIndex], image.colorBuffer[imageIndex]);

            //Increase Index (going one step right)
            thisIndex++;
            imageIndex++;

            //If reached last Point in Line, execute following
            if (imageIndex == imageMaxWidthIndex) 
            {
                //Increase Height by one (width of corresponding Image)
                thisHeight  += graphicsImageWidth; 
                imageHeight += image.width();
                
                //Reset Indices to most left point shifted down by current Height
                thisIndex  = thisStartIndex  + thisHeight;
                imageIndex = imageStartIndex + imageHeight;
                
                //Set new last point in line
                imageMaxWidthIndex = imageIndex + width;

                //If reached last Index, step out of loop
                if (imageIndex == imageMaxIndex) break;
            }
        }
    }
}