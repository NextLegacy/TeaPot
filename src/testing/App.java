package testing;

import static engine.utils.MathUtils.*;

import java.awt.event.KeyEvent;

import engine.Engine;
import engine.Script;
import engine.math.FinalVector;
import engine.math.Vector;
import engine.utils.graphics.Image;

public class App 
{       
    public static void main(String[] args)
    {
        new App().run();
    }

    public void run()
    {
        Engine engine = Engine.fastSetup(new Script() 
        {
            Image image;
            Image result;
            Image logo;

            Vector position;

            @Override
            protected void awake()
            {
                window().setPosition(fvec(10, 10));

                position = vec(100, 200);
                image = result = Image.fromFile("./rsc/images/A.png").scaled(vec(50, 50));
                logo = Image.fromFile("./rsc/images/B.png");
            }
            
            @Override
            protected void update() 
            {
                result = image;//.scaled(input().mouse().position());

                if (input().key(KeyEvent.VK_W).isDown())
                    position.add(FinalVector.up.times(engine().deltaTime() * 50));
                
                if (input().key(KeyEvent.VK_A).isDown())
                    position.add(FinalVector.left.times(engine().deltaTime() * 50));
                    
                if (input().key(KeyEvent.VK_S).isDown())
                    position.add(FinalVector.down.times(engine().deltaTime() * 50));
                
                if (input().key(KeyEvent.VK_D).isDown())
                    position.add(FinalVector.right.times(engine().deltaTime() * 50));

                if (input().key(KeyEvent.VK_ESCAPE).isDown())
                    engine().deactivate();
            }

            @Override
            protected void render() 
            {
                if (image().isNot("main")) return;
                
                image().drawImage(result, position.int_x(), position.int_y());
                image().drawImage(logo, 0, 0);

                image().rect(vec(250, 250), vec(500, 500), -4, (x, y, z) -> 0x4bff00ff);
            }
        });

        engine.activate();
    }
}