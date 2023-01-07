package engine;

import engine.utils.activatable.IActivatable;
import engine.utils.destroyable.IDestroyable;
import engine.window.Window;
import engine.window.WindowLayer;
import engine.window.Input.Button;
import engine.window.Input.Input;
import engine.window.Input.Key;
import engine.window.Input.Mouse;
import engine.window.Input.Wheel;

public abstract class Script implements IActivatable, IDestroyable
{
    private boolean active;

    boolean startedOnce;
    boolean awakedOnce;

    private GameObject gameObject;
   
    public Script() { }

    void setGameObject(GameObject gameObject) { this.gameObject = gameObject; }
    
    protected final GameObject  gameObject() { return gameObject            ; }

    protected final Engine      engine    () { return gameObject.engine(); }
    protected final Window      window    () { return engine().window  (); }

    protected final Input       input     () { return engine().input   (); }
    protected final Mouse       mouse     () { return input().mouse    (); }
    protected final Button      left      () { return input().left     (); }
    protected final Button      right     () { return input().right    (); }
    protected final Wheel       wheel     () { return input().wheel    (); }
    protected final Key         key(int keyEvent) { return input().key(keyEvent); }

    protected final Scene       scene    () { return engine().activeScene(); }
    protected final WindowLayer image    () { return engine().image      (); }

    protected final double deltaTime     () { return engine().deltaTime(); }
    protected final double frameDeltaTime() { return engine().deltaTime(); }
    protected final double tickDeltaTime () { return engine().deltaTime(); }

    protected void awake() { }

    protected void start () { }
    protected void update() { }
    protected void render() { }

    protected void onActivate  () { }
    protected void onDeactivate() { }

    protected void onGameObjectActivate  () { }
    protected void onGameObjectDeactivate() { }
    protected void onGameObjectDestroy   () { }
    protected void onDestroy    () { }
    protected void onSceneChange() { }

    public final void destroy()
    {
        gameObject.removeScript(this);
        deactivate();
        onDestroy();
    }

    @Override
    public final void setActive(boolean state) 
    {
        if (active == state) return;

        active = state;
        
        if (active) onActivate  ();
        else        onDeactivate();
    }

    @Override
    public final boolean isActive() { return active; }

    @Override public String toString() { return getClass().getName() + "(attached to: " + gameObject + ")"; }

    @Override public final boolean equals(Object obj) { return super.equals(obj); }
    @Override protected final Object clone() throws CloneNotSupportedException { return super.clone(); }
    @Override @SuppressWarnings("deprecation") protected final void finalize() throws Throwable { super.finalize(); }
    @Override public final int hashCode() { return super.hashCode(); }
}
