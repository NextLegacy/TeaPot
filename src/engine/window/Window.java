package engine.window;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import engine.math.FinalVector;
import engine.math.Vector4;
import engine.utils.ImageUtils;
import engine.utils.Screen;
import engine.utils.time.Time;
import engine.graphics.DrawableImage;
import engine.window.Input.Input;

/**
 * This class is used to create a window. <p>
 * 
 * It wraps a {@link Frame} and recreates it when needed. <p>
 * 
 * It has a {@link Input} object that is used to get input from the user. <p>
 * 
 * It can easily be asoociated with a {@link Screen} object. <p>
 * 
 * It has multiple {@link WindowLayer} objects that can be used to draw on. <p>
 * 
 * It has a proper rendering system that can be used to draw on the screen. <p>
 * 
 * You have to implement the {@link #renderImage(WindowLayer)} which will be called
 * for every {@link WindowLayer} objects.
 * 
 * @author NextLegacy
 * @version 1.0
 */
public abstract class Window
{
    private Frame frame;

    private Input input;

    private Screen screen;

    private BufferStrategy strategy;

    private String[] layers;
    
    private BufferedImage windowBufferedImage;
    private DrawableImage windowBuffer;
    private WindowLayer[] windowLayers;

    private FinalVector position;
    private FinalVector size;

    private FinalVector windowSize;

    private boolean isInFullScreenMode;

    public Window(Screen screen, Vector4 size, String... layers)
    {
        if (screen == null) throw new NullPointerException("Screen is null!");

        this.screen = screen;
        this.layers = layers;
        this.size   = size.toFinalVector();

        initializeBuffers();
    }

    private final void initializeFrame()
    {
        destroyFrame();

        frame = new Frame(screen.GRAPHICS_CONFIGURATION);
        
        if (input == null) input = new Input();

        input.bindToFrame(frame);

        //frame.setUndecorated(true);

        frame.setResizable(false);
        
        frame.setIgnoreRepaint(true);
        frame.setFocusable(true);
        frame.requestFocus();

        frame.setLayout(null);

        setFrameSize();
        setPositionToCenter();

        if (isInFullScreenMode) setFullScreen(true);
    }

    private final void destroyFrame()
    {
        if (frame == null) return;

        frame.dispose();
        frame = null;
    }

    private final void initializeBuffers()
    {
        windowBufferedImage = ImageUtils.createCompatibleBufferedImage(size);
        
        windowBuffer = new DrawableImage(windowBufferedImage);

        WindowLayer[] _windowLayers = new WindowLayer[layers.length];

        for (int i = 0; i < _windowLayers.length; i++)
            _windowLayers[i] = new WindowLayer(layers[i], size);

        windowLayers = _windowLayers;
    }

    public final Window setTitle(String newTitle) { frame.setTitle(newTitle); return this; }

    // TODO: this seems wrongs, why set size to windowSize and updateFrameSize()?
    public final void setFullScreen(boolean fullScreen)
    {
        if   (fullScreen) screen.setFullScreen(frame, () -> { isInFullScreenMode = false; updateFrameSize(); });
        else              screen.setFullScreen(null , null);

        isInFullScreenMode = fullScreen;
        
        size = windowSize;

        updateFrameSize();
    }

    public final void setScreen(final Screen screen)
    {
        if (screen == null) throw new NullPointerException("Screen is null!");
        
        this.screen = screen;

        initializeFrame();
    }

    public final void setSize(Vector4 size)
    {
        windowSize = size.toFinalVector();

        updateFrameSize();
    }

    private final void updateFrameSize()
    {
        if (frame != null) setFrameSize();
        
        initializeBuffers();
    }

    private final void setFrameSize()
    {
        frame.setSize(width(), height());
        frame.setPreferredSize(new Dimension(width(), height()));
    }

    public final void setDecorated(boolean decorated)
    {
        frame.setUndecorated(!decorated);
        frame.setCursor(new Cursor(Cursor.CUSTOM_CURSOR));
    }

    public final Window setPosition(Vector4 position)
    {
        this.position = position.toFinalVector();

        frame.setLocation((int) position.x(), (int) position.y());

        return this;
    }

    public final Window setPositionToCenter()
    {
        return setPosition(screen.SCREEN_SIZE.dividedBy(2).minus(size.dividedBy(2)));
    }
    
    public final Frame frame                 () { return frame         ; } // TODO: Remove this method later
    public final FinalVector   size          () { return size          ; }
    public final FinalVector   position      () { return position      ; }
    public final int           width         () { return (int) size.x(); }
    public final int           height        () { return (int) size.y(); }
    public final int           verticalBorder() { return frame.getInsets().top; }
    public final Input         input         () { return input   ; }
    public final String        title         () { return frame.getTitle(); }
    
    public final FinalVector size  (double widthRatio, double heightRatio) { return size.times(widthRatio, heightRatio).toFinalVector(); }
    public final double      width (double ratio) { return width () * ratio; }
    public final double      height(double ratio) { return height() * ratio; }

    public final WindowLayer[] windowLayers() { return windowLayers; }
    public final WindowLayer windowLayer(int index) { return windowLayers[index]; }

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

    private static final long MINIMUM_TIME_TO_LOAD = 10_000_000_000L;

    public final void start()
    {
        if (frame != null)
            return; 

        initializeFrame();

        frame.setVisible(true);

        ensureFrameIsLoadedAfterCreating();
    }

    /**
     * Ensures that the frame is loaded after creating it.
     * Is used in start() methods and ensures the frame is loaded and active after function call.
     * 
     * @throws RuntimeException if the frame is not loaded after a certain time.
     */
    private final void ensureFrameIsLoadedAfterCreating()
    {
        final long start = Time.nanos();

        while (!input.isActive())
        {
            if (Time.nanos() - start > MINIMUM_TIME_TO_LOAD)
                throw new RuntimeException("Frame exceeded minimum time to load (" + MINIMUM_TIME_TO_LOAD + " ns)!"); // TODO: Create a custom exception
        }
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
        renderImagesOntoWindowBuffer();
        renderWindowBufferOntoFrame();
    }

    public abstract void renderImage(WindowLayer image);

    public final void renderImages()
    {
        for (int i = 0; i < windowLayers.length; i++)
            renderImage(windowLayers[i]);
    }

    public final void renderImagesOntoWindowBuffer()
    {   
        windowBuffer.clear();

        for (int i = 0; i < windowLayers.length; i++)
            windowBuffer.drawImage(windowLayers[i]);
    }

    public final void renderWindowBufferOntoFrame()
    {
        if (frame == null || !input.isFocused()) return;

        if (strategy == null)
        {        
            frame.createBufferStrategy(2);
            
            strategy = frame.getBufferStrategy();
        }

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

    public String toString()
    {
        return "Window(" + title() + ")";
    }
}
