package engine.utils;

import java.awt.Toolkit;

import engine.math.FinalVector;

public final class ScreenUtils 
{
    private ScreenUtils() { }
    
    private static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();
    
    public static final FinalVector SCREEN_SIZE = new FinalVector(TOOLKIT.getScreenSize().getWidth(), TOOLKIT.getScreenSize().getHeight());
}
