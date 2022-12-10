package engine.utils;

import java.awt.Component;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;

import engine.math.FinalVector;

public final class ScreenUtils 
{
    private ScreenUtils() { }
 
    private static final GraphicsEnvironment GRAPHICS_ENVIRONMENT;
    private static final GraphicsDevice[] GRAPHICS_DEVICES;

    public static final Screen[] SCREENS;

    static 
    {
        GRAPHICS_ENVIRONMENT = GraphicsEnvironment.getLocalGraphicsEnvironment();
    
        GRAPHICS_DEVICES = GRAPHICS_ENVIRONMENT.getScreenDevices();
        
        SCREENS = new Screen[GRAPHICS_DEVICES.length];

        System.out.println(GRAPHICS_DEVICES[0].getConfigurations());

        for (int i = 0; i < GRAPHICS_DEVICES.length; i++)
        {
            SCREENS[i] = new Screen(GRAPHICS_DEVICES[i]);
        }
    }

    public static Screen getScreen(Component component)
    {
        component.getGraphicsConfiguration().getDevice();

        return null;
    }

    public static final class Screen
    {
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

        public void setFullScreen(Window window)
        {
            GRAPHICS_DEVICE.setFullScreenWindow(window);
        }
    }
}
