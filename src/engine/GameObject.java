package engine;

import java.util.ArrayList;

import engine.utils.ArrayUtils;
import engine.utils.GenericUtils;
import engine.utils.Lambda.Action1;
import engine.utils.activatable.IActivatable;
import engine.utils.destroyable.IDestroyable;

public final class GameObject implements IActivatable, IDestroyable
{
    private String name;

    private boolean active;

    private boolean destroyed;

    private GameObject parent;
    private GameObject[] children;
    private GameObject[] childrenArray;

    private Script[] scripts;

    private Scene scene;
    
    public GameObject() { this(""); }

    public GameObject(String name)
    {
        this.name = name;

        childrenArray = new GameObject[0];
        scripts       = new Script    [0];
    }

    //does get used in 
    /*package_private*/ void setScene(Scene scene) 
    { 
        this.scene = scene; 
        childrenArray = new GameObject[0];
        scripts  = new Script    [0]; 
    }

    @Override
    public boolean      isDestroyed() { return destroyed                  ; }
    public String       name       () { return name                       ; }
    public GameObject   parent     () { return parent                     ; }
    public GameObject[] children   () { return ArrayUtils.clone(children) ; }
    public Script    [] scripts    () { return ArrayUtils.clone(scripts ) ; }
    public Engine       engine     () { return scene.engine()             ; }
    public Scene        scene      () { return scene                      ; }

    public boolean hasChildren(final GameObject child)
    {
        for (int i = 0; i < children.length; i++)
            if (children[i] == child) return true;

        return false;
    }

    public void setParent(GameObject newParent)
    {
        GameObjectIsDestroyedException.throwIfIsDestroyed(this);

        if (parent == newParent) 
            return;
        
        GameObject oldParent = parent; 

        parent = newParent;

        if (oldParent != null)
            oldParent.removeChildren(this);

        if (newParent != null)
            newParent.addChildren(this);
    }
    
    public void addChildren(GameObject gameObject)
    {
        GameObjectIsDestroyedException.throwIfIsDestroyed(this);

        if (hasChildren(gameObject) || gameObject == null) 
            return;

        if (gameObject.parent() != this)
            gameObject.setParent(this);
        
        children = ArrayUtils.push(children, gameObject);
    }

    public void removeChildren(GameObject gameObject)
    {
        GameObjectIsDestroyedException.throwIfIsDestroyed(this);

        if (!hasChildren(gameObject) || gameObject == null)
            return;

        if (gameObject.parent() == this)
            gameObject.setParent(null);

        children = ArrayUtils.remove(children, gameObject);
    }

    public void addScript(Script script)
    {
        GameObjectIsDestroyedException.throwIfIsDestroyed(this);

        if (script.gameObject() != null || ArrayUtils.contains(scripts, script))
            return;

        script.setGameObject(this);

        scripts = ArrayUtils.push(scripts, script);
    }

    public void removeScript(Script script)
    {
        GameObjectIsDestroyedException.throwIfIsDestroyed(this);

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

        /*
         * The thing is, destroy does not make that much Sense like this
         * When you destroy a GameObject in a Scene
         * 
         * The Scripts should stay and the Children as well
         * But the Parent should be set to null
         * 
         * The Transform should be rested too
         */

        //scripts.clear();

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
            if (!script.awakedOnce)
            { 
                script.awakedOnce = true; 
                script.awake();
            }
        }
            
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

    private static final class GameObjectIsDestroyedException extends RuntimeException
    {
        GameObjectIsDestroyedException(GameObject gameObject)
        {
            super(gameObject + " is destroyed.");
        }

        private static void throwIfIsDestroyed(GameObject gameObject)
        {
            if (gameObject.isDestroyed())
                throw new GameObjectIsDestroyedException(gameObject);
        }
    }
}