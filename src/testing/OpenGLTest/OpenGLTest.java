package testing.OpenGLTest;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;

import engine.utils.Screen;

public class OpenGLTest 
{
    public static void main(String[] args) 
    {
        new OpenGLTest();
    }
    
    public OpenGLTest()
    {
        GLProfile glProfile = GLProfile.get(GLProfile.GL4);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);

        GLWindow window = GLWindow.create(glCapabilities);
        
        window.setTitle("Hello Triangle!");
        window.setSize(1080, 720);

        Screen screen = Screen.get(0);

        window.setPosition((int)(screen.SCREEN_SIZE.x/2 - window.getWidth()/2), (int)(screen.SCREEN_SIZE.y/2 - window.getHeight()/2));

        window.setUndecorated(false);

        window.setContextCreationFlags(GLContext.CTX_OPTION_DEBUG);
        window.setVisible(true);

        Animator animator = new Animator(window);
        animator.start();

        window.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowDestroyed(WindowEvent e) 
            {
                animator.stop();
                System.exit(1);
            }
        });
    }
}
