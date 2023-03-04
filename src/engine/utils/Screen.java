package engine.utils;

import java.awt.Component;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Window;

import engine.math.FinalVector;
import engine.utils.Lambda.Action0;

/**
 * This class represents a screen.
 * It contains information about the screen like the size and the GraphicsDevice.
 * It also contains methods for setting a window to fullscreen.
 * 
 * @author NextLegacy
 * @version 1.0
 */
public final class Screen 
{
    private static final GraphicsEnvironment GRAPHICS_ENVIRONMENT;
    private static final GraphicsDevice[] GRAPHICS_DEVICES;

    public static final Screen[] SCREENS;

    static 
    {
        GRAPHICS_ENVIRONMENT = GraphicsEnvironment.getLocalGraphicsEnvironment();
    
        GRAPHICS_DEVICES = GRAPHICS_ENVIRONMENT.getScreenDevices();
        
        SCREENS = new Screen[GRAPHICS_DEVICES.length];

        for (int i = 0; i < GRAPHICS_DEVICES.length; i++)
        {
            SCREENS[i] = new Screen(GRAPHICS_DEVICES[i]);
        }
    }

    public final GraphicsDevice GRAPHICS_DEVICE;
    public final GraphicsConfiguration GRAPHICS_CONFIGURATION; //their are multiple GraphicsConfigurations per GraphicsDevice, but this should be fine (I hope so atleast)
    public final FinalVector SCREEN_SIZE;

    private Screen(GraphicsDevice graphicsDevice)
    {
        GRAPHICS_DEVICE = graphicsDevice;

        GRAPHICS_CONFIGURATION = graphicsDevice.getDefaultConfiguration();

        Rectangle bounds = GRAPHICS_CONFIGURATION.getBounds();

        SCREEN_SIZE = new FinalVector(bounds.getWidth(), bounds.getHeight());
    }

    private Action0 onFullScreenEnd;

    public void setFullScreen(Window window, Action0 onFullScreenEnd)
    {
        GRAPHICS_DEVICE.setFullScreenWindow(null);

        if (this.onFullScreenEnd != null) onFullScreenEnd.run();

        this.onFullScreenEnd = onFullScreenEnd;

        if (window == null) return;

        GRAPHICS_DEVICE.setFullScreenWindow(window);
    }

    public String toString() 
    {
        return "Screen(id: " + GRAPHICS_DEVICE.getIDstring() + " | size: " + SCREEN_SIZE.toSizeString() + ")";    
    }

    public static Screen get(final int i)
    {
        return SCREENS[i];
    }

    public static Screen get(final String id)
    {
        for (int i = 0; i < SCREENS.length; i++)
        {
            if (SCREENS[i].GRAPHICS_DEVICE.getIDstring() == id)
            {
                return SCREENS[i];
            }
        }
        
        return null;
    }

    public static Screen get(Component component)
    {
        component.getGraphicsConfiguration().getDevice();

        for (int i = 0; i < SCREENS.length; i++)
        {
            if (SCREENS[i].GRAPHICS_DEVICE == component.getGraphicsConfiguration().getDevice())
            {
                return SCREENS[i];
            }
        }

        return null;
    }
}
