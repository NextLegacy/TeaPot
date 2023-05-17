package engine;

import engine.math.Transform;
import engine.utils.activatable.IActivatable;
import engine.utils.destroyable.IDestroyable;
import engine.window.Window;
import engine.window.WindowLayer;
import engine.window.Input.Button;
import engine.window.Input.Input;
import engine.window.Input.Key;
import engine.window.Input.Mouse;
import engine.window.Input.Wheel;

/**
 * A script that can be attached to a GameObject.
 * 
 * @author NextLegacy
 */
public abstract class Script implements IActivatable, IDestroyable
{
    private boolean isActive;
    private boolean isDestroyed;

    private GameObject gameObject;
   
    private boolean startedOnce;

    public Script() { activate(); }

    void setGameObject(GameObject gameObject) { this.gameObject = gameObject; }
    
    protected final GameObject  gameObject() { return gameObject          ; }
    protected final Transform   transform () { return gameObject.transform; }

    protected final Transform   setTransform(Transform transform) { return gameObject.transform = transform; }

    protected final GameObject  parent    () { return gameObject.parent   (); }

    protected final Engine      engine    () { return gameObject.engine(); }
    protected final Window      window    () { return engine().window  (); }

    protected final Input       input     () { return engine().input   (); }
    protected final Mouse       mouse     () { return input().mouse    (); }
    protected final Button      left      () { return input().left     (); }
    protected final Button      right     () { return input().right    (); }
    protected final Wheel       wheel     () { return input().wheel    (); }
    protected final Key         key(int keyEvent) { return input().key(keyEvent); }

    protected final <T extends Scene> T           scene    () { return engine().activeScene(); }
    protected final                   WindowLayer image    () { return engine().image      (); }

    protected final double deltaTime     () { return engine().deltaTime(); }
    protected final double frameDeltaTime() { return engine().deltaTime(); }
    protected final double tickDeltaTime () { return engine().deltaTime(); }

    final void tryStartOnce() { if (startedOnce || isNotActive()) return; startedOnce = true; start(); }

    /** Called once in the beginning of the game or when the script is attached to a GameObject. */
    protected void start () { }
    
    /** the update method is called every tick. */
    protected void update() { }

    /** the render method is called every frame. */
    protected void render() { }

    protected void onActivate() { }
    protected void onDeactivate() { }
    protected void onGameObjectActivate  () { }
    protected void onGameObjectDeactivate() { }
    protected void onGameObjectDestroy   () { }
    protected void onDestroy    () { }
    protected void onSceneChange() { }

    public final void destroy()
    {
        if (isDestroyed) return;

        deactivate();

        if (gameObject != null)
            gameObject.removeScript(this);

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
