package engine;

import java.util.ArrayList;

import engine.utils.ArrayUtils;
import engine.utils.Lambda.Action1;
import engine.utils.activatable.IActivatable;
import engine.utils.destroyable.IDestroyable;
import engine.utils.destroyable.ObjectIsDestroyedException;

public final class GameObject implements IActivatable, IDestroyable
{
    private static final GameObject NULL_GAMEOBJECT = new GameObject("NULL GAMEOBJECT");

    static { NULL_GAMEOBJECT.destroy(); }

    private String name;

    private boolean active   ;
    private boolean destroyed;

    private GameObject   parent       ;

    private GameObject[] children     ;
    private GameObject[] childrenArray;

    private Script[] scripts       ;
    private Script[] currentScripts;

    private Scene scene;
    
    public GameObject() { this(""); }

    public GameObject(String name)
    {
        this.name = name;

        childrenArray  = new GameObject[0];
        scripts        = new Script    [0];
        currentScripts = new Script    [0];
    }

    //does get used in Scene
    /*package_private*/ void setScene(Scene newScene) 
    { 
        scene = newScene; 
    }

    @Override
    public boolean      isDestroyed() { return destroyed                 ; }
    public String       name       () { return name                      ; }
    public GameObject   parent     () { return parent                    ; }
    public GameObject[] children   () { return ArrayUtils.clone(children); }
    public Script    [] scripts    () { return ArrayUtils.clone(scripts ); }
    public Engine       engine     () { return scene.engine()            ; }
    public Scene        scene      () { return scene                     ; }

    public boolean hasChildren(final GameObject child ) { return ArrayUtils.contains(children, child ); }
    public boolean hasScript  (final Script     script) { return ArrayUtils.contains(scripts , script); }

    private final void addChildrenInternal(GameObject gameObject)
    {
        children = ArrayUtils.push(children, gameObject);
    }

    private final void removeChildrenInternel(GameObject gameObject)
    {
        children = ArrayUtils.remove(children, gameObject);
    }

    private final void setParentInternal(GameObject gameObject)
    {
        parent = gameObject;
    }

    public void setParent(GameObject newParent)
    {
        ObjectIsDestroyedException.throwIfIsDestroyed(this);

        if (newParent == null) newParent = NULL_GAMEOBJECT;

        if (parent    == newParent    || // don't set if already set
            newParent == this         || // don't set if is self
            newParent.hasChildren(this)) // don't set if new parent already has this as child
            return;
        
        if (parent    != null) parent   .removeChildrenInternel(this);
        if (newParent != null) newParent.addChildrenInternal   (this);

        setParentInternal(newParent);
    }
    
    public void addChildren(GameObject child)
    {
        ObjectIsDestroyedException.throwIfIsDestroyed(this);

        if (hasChildren(child)     || // don't add if already has child
            child          == null || // don't add if null
            child          == this || // don't add if is self
            child.parent() == this)   // don't add if already has this as parent
            return;

        child.setParent(this);
    }

    public void removeChildren(GameObject child)
    {
        ObjectIsDestroyedException.throwIfIsDestroyed(this);

        if (!hasChildren(child)    || // don't remove if doesn't have child
            child          == null || // don't remove if null
            child          == this || // don't remove if is self
            child.parent() != this)   // don't remove if doesn't have this as parent
            return;

        child.setParent(null);
    }

    public void addScript(Script script)
    {
        ObjectIsDestroyedException.throwIfIsDestroyed(this);

        if (script.gameObject() != null || ArrayUtils.contains(scripts, script))
            return;

        script.setGameObject(this);

        scripts = ArrayUtils.push(scripts, script);
    }

    public void removeScript(Script script)
    {
        ObjectIsDestroyedException.throwIfIsDestroyed(this);

        if (!ArrayUtils.contains(scripts, script))
            return;

        script.setGameObject(null);

        scripts = ArrayUtils.remove(scripts, script);
    }
    
    @Override
    public void destroy()
    {
        if (destroyed) return;

        destroyed = true;

        deactivate();

        if (scene != null) 
        {
            scene.removeGameObject(this);
            
            for (GameObject children : childrenArray)
                children.destroy();
        }

        onDestroy();
    }
    
    public void update() 
    { 
        for (Script script : scripts)
        { 
            if (script.isActive() && !script.startedOnce)
            { 
                script.startedOnce = true; script.start(); 
            }
        }

        for (GameObject children : childrenArray)
        {
            if (children.scene() == null)
            {
                children.setScene(scene());

                if (children.isActive())
                    children.update();
            }
        }

        runForAllScripts(Script::update); 
    }

    public void render() { runForAllScripts(Script::render); }

    void onActivate   () { runForAllScripts(Script::onGameObjectActivate  ); }
    void onDeactivate () { runForAllScripts(Script::onGameObjectDeactivate); }
    void onDestroy    () { runForAllScripts(Script::onGameObjectDestroy   ); }
    void onSceneChange() { runForAllScripts(Script::onSceneChange         ); }

    @Override
    public void setActive(boolean state) 
    {
        if (active == state) return;

        active = state;
        
        if (active) onActivate  ();
        else        onDeactivate();
    }

    @Override
    public boolean isActive() { return active; }

    private void runForAllScripts(Action1<Script> action)
    {
        for (Script script : scripts)
            if (script.isActive())
                action.run(script);
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "(" + name + ")";
    }
}