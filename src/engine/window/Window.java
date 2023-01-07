package engine.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import engine.math.FinalVector;
import engine.math.Vector4;
import engine.utils.ImageUtils;
import engine.utils.ScreenUtils;
import engine.graphics.DrawableImage;
import engine.window.Input.Input;

public abstract class Window
{
    private Frame frame;

    private Input input;

    private BufferStrategy strategy;

    private String[] layers;
    
    private BufferedImage windowBufferedImage;
    private DrawableImage windowImage;
    private WindowLayer[] windowLayers;

    private FinalVector position;
    private FinalVector size;

    protected Window(final Vector4 size, final String[] layers)
    {
        this.layers = layers;

        setSize(size);
    } 

    private final void initializeFrame()
    {
        frame = new Frame();
        input = new Input()
            .bindToFrame(frame);

        frame.setUndecorated(true);

        frame.setResizable(false);
        
        frame.setIgnoreRepaint(true);
        frame.setFocusable(true);
        frame.requestFocus();

        frame.setLayout(null);

        setFrameSize();
        setPositionToCenter();
    }

    private final void initializeBuffers()
    {
        windowBufferedImage = ImageUtils.createCompatibleBufferedImage(size);
        
        windowImage = new DrawableImage(windowBufferedImage);

        WindowLayer[] _windowLayers = new WindowLayer[layers.length];

        for (int i = 0; i < _windowLayers.length; i++)
            _windowLayers[i] = new WindowLayer(layers[i], size);

        windowLayers = _windowLayers;
    }

    public final Window setTitle(String newTitle) { frame.setTitle(newTitle); return this; }

    public final Window setSize(Vector4 size)
    {
        this.size = size.toFinalVector();

        if (frame != null) setFrameSize();
        
        initializeBuffers();
    
        return this;
    }

    private final void setFrameSize()
    {
        frame.setSize(width(), height());
        frame.setPreferredSize(new Dimension(width(), height()));
    }

    public final Window setPosition(Vector4 position)
    {
        this.position = position.toFinalVector();

        frame.setLocation(position.int_x(), position.int_y());

        return this;
    }

    public final Window setPositionToCenter()
    {
        return this;
        //return setPosition(ScreenUtils.SCREEN_SIZE.dividedBy(2).minus(size.dividedBy(2)));
    }
    
    public final FinalVector   size          () { return size            ; }
    public final FinalVector   position      () { return position        ; }
    public final int           width         () { return size.int_x()    ; }
    public final int           height        () { return size.int_y()    ; }
    public final int           verticalBorder() { return frame.getInsets().top + frame.getInsets().bottom; }
    public final Input         input         () { return input   ; }
    public final String        title         () { return frame.getTitle(); }
    
    public final FinalVector size  (double widthRatio, double heightRatio) { return size.times(widthRatio, heightRatio).toFinalVector(); }
    public final double      width (double ratio) { return width () * ratio; }
    public final double      height(double ratio) { return height() * ratio; }

    public final WindowLayer windowLayer(String name)
    {
        for (int i = 0; i < windowLayers.length; i++)
        {
            WindowLayer windowLayer = windowLayers[i];
            
            if (windowLayer.is(name))
                return windowLayer;
        }
        
        return null;
    }

    public final void start()
    {
        if (frame != null)
            return;

        initializeFrame();

        frame.setVisible(true);    
    }

    public final void forceStart()
    {
        if (frame != null)
            dispose();

        start();
    }

    public final void dispose()
    {
        if (frame == null)
            return;
    
        if (strategy != null) 
            strategy.dispose();
            
        frame.setVisible(false);
        frame.dispose();

        frame = null;
        input = null;

        strategy = null;
    }

    public void render()
    {
        renderImages();
        renderImagesOntoWindow();
    }

    public abstract void renderImage(WindowLayer image);

    public final void renderImages()
    {
        for (int i = 0; i < windowLayers.length; i++)
            renderImage(windowLayers[i]);
    }

    public final void renderImagesOntoWindow()
    {   
        if (strategy == null)
        {        
            frame.createBufferStrategy(2);
            
            strategy = frame.getBufferStrategy();
        }

        windowImage.clear();

        for (int i = 0; i < windowLayers.length; i++)
            windowImage.pasteImage(windowLayers[i]);

        do 
        {
            do 
            {
                Graphics graphics = strategy.getDrawGraphics();

                //RENDER START

                    graphics.setColor(Color.BLACK);
                    graphics.fillRect(0, 0, width(), height()); 

                    graphics.drawImage(windowBufferedImage, 0, verticalBorder(), null);
                
                //RENDER END

                graphics.dispose();
    
            } while (strategy.contentsRestored());
    
            strategy.show();

        } while (strategy.contentsLost());
    }
}
