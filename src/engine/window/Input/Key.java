package engine.window.Input;

public class Key extends Button
{
    public static final Key NULL_KEY = new Key(-1);

    final int keyEvent;
    //boolean isDown;
    //double timeOnStart;

    Key(final int keyEvent)
    {
        super();
        this.keyEvent = keyEvent;
        //isDown        = false;
        //timeOnStart   = 0.0d;
    }

    public int keyEvent       () { return keyEvent                                    ; }
    //public boolean isDown     () { return isDown                                      ; }
    //public boolean isUp       () { return !isDown                                     ; }
    //public double  timeOnStart() { return timeOnStart                                 ; }
    //public double  downTime   () { return isDown ? Time.nanos() - timeOnStart : 0     ; }
}