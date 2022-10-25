package testing;

import engine.utils.Lambda.Action1;
import engine.utils.Lambda.Func0;

public class Peter 
{
    public void sagPeter() { System.out.println("Peter"); }

    public static void main(String[] args)
    {
        ((Action1<Peter>)Peter::sagPeter).run(((Func0<Peter>)Peter::new).get());
    }
}
