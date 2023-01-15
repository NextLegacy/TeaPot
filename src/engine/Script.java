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
    private boolean isActive;
    private boolean isDestroyed;

    private GameObject gameObject;
   
    private boolean startedOnce;

    public Script() { }

    //does get used in GameObject
    /*package_private*/ void setGameObject(GameObject gameObject) { this.gameObject = gameObject; }
    
    protected final GameObject  gameObject() { return gameObject         ; }

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

    final void tryStartOnce()
    {
        if (startedOnce) return;
        if (!isActive()) return;

        start();
        startedOnce = true;
    }

    public final void destroy()
    {
        if (isDestroyed) return;

        if (gameObject != null)
            gameObject.removeScript(this);

        deactivate();

        isDestroyed = true;
        gameObject  = null;
        
        onDestroy();
    }

    @Override
    public final void setActive(boolean state) 
    {
        if (isActive == state) return;

        isActive = state;
        
        if (isActive) onActivate  ();
        else          onDeactivate();
    }

    @Override public final boolean isDestroyed() { return isDestroyed; }

    @Override public final boolean isActive() { return gameObject != null && gameObject.isActive() && isActive && isNotDestroyed(); }

    @Override public String toString() { return getClass().getName() + "(attached to: " + gameObject + ")"; }
}
