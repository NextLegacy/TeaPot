package testing;

import static engine.utils.MathUtils.*;

import java.awt.event.KeyEvent;

import engine.Engine;
import engine.Script;
import engine.math.FinalVector;
import engine.math.Vector;
import engine.utils.ImageUtils;
import engine.graphics.Image;

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
            protected void start()
            {
                window().setPosition(fvec(1366, 760).minus(window().size()).dividedBy(2));

                position = vec(100, 200);

                image = result = ImageUtils.scaled(Image.fromFile("./rsc/images/A.png"), vec(50, 50));

                //image = result = ImageUtils.scaled(image, vec(50, 50));
                logo = Image.fromFile("./rsc/images/B.png");

                System.out.println("sadf");
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

                n+= input().wheel().direction()*30;    
            }

            double n = 0;

            @Override
            protected void render() 
            {
                if (image().isNot("main")) return;
                
                //ImageAlgorithms3D.renderTestMeshWithTestImage(image());

                image().drawImage(result, position.int_x(), position.int_y());
                image().drawImage(logo, 0, 0);

                //Error when n is smaller than -250
                image().drawRect(vec(250, 250), vec(500, 500).plus(n), 5, 0x4bff00ff);
            }
        });

        engine.activate();
    }
}