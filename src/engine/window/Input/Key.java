package engine.window.Input;

public class Key extends Button
{
    static final Key NULL_KEY = new Key(-1);

    final int keyEvent;

    Key(final int keyEvent)
    {
        super();
        this.keyEvent = keyEvent;
    }

    public int keyEvent() { return keyEvent; }
}