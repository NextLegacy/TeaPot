package engine;

import engine.utils.activatable.IActivatable;
import engine.utils.destroyable.IDestroyable;
import engine.window.Window;
import engine.window.WindowLayer;
import engine.window.Input.InputListener;

public abstract class Script implements IActivatable, IDestroyable
{
    private boolean active;

    boolean startedOnce;
    boolean awakedOnce;

    private GameObject gameObject;
   
    public Script() { }

    void setGameObject(GameObject gameObject) { this.gameObject = gameObject; }
    
    protected final GameObject gameObject() { return gameObject; }

    protected final Engine        engine() { return gameObject.engine     (); }
    protected final Window        window() { return engine().window       (); }
    protected final InputListener input () { return engine().inputListener(); }
    protected final Scene         scene () { return engine().activeScene  (); }
    protected final WindowLayer   image () { return engine().image        (); }

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
