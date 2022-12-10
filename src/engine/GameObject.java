package engine;

import java.util.ArrayList;

import engine.utils.Lambda.Action1;
import engine.utils.activatable.IActivatable;
import engine.utils.destroyable.IDestroyable;

public final class GameObject implements IActivatable, IDestroyable
{
    private String name;

    private boolean active;

    private boolean destroyed;

    private GameObject parent;
    private final ArrayList<GameObject> children;
    private GameObject[] childrenArray;

    private final ArrayList<Script> scripts;
    private Script[] scriptsArray;

    private Scene scene;
    
    public GameObject() { this(""); }

    public GameObject(String name)
    {
        this.name = name;

        children = new ArrayList<>();
        scripts = new ArrayList<>();
    }

    void setScene(Scene scene) { this.scene = scene; init(); }

    @Override
    public boolean               isDestroyed() { return destroyed                ; }
    public String                name       () { return name                     ; }
    public GameObject            parent     () { return parent                   ; }
    public ArrayList<GameObject> children   () { return new ArrayList<>(children); }
    public ArrayList<Script>     scripts    () { return new ArrayList<>(scripts ); }
    public Engine                engine     () { return scene.engine()           ; }
    public Scene                 scene      () { return scene                    ; }

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

        if (children.contains(gameObject) || gameObject == null) 
            return;

        if (gameObject.parent() != this)
            gameObject.setParent(this);
        
        children.add(gameObject);
       
        updateChildrenArray();
    }

    public void removeChildren(GameObject gameObject)
    {
        GameObjectIsDestroyedException.throwIfIsDestroyed(this);

        if (!children.contains(gameObject) || gameObject == null)
            return;

        if (gameObject.parent() == this)
            gameObject.setParent(null);

        children.remove(gameObject);

        updateChildrenArray();
    }

    public void addScript(Script script)
    {
        GameObjectIsDestroyedException.throwIfIsDestroyed(this);

        if (script.gameObject() != null || scripts.contains(script))
            return;

        script.setGameObject(this);

        scripts.add(script);
        updateScriptsArray();
    }

    public void removeScript(Script script)
    {
        GameObjectIsDestroyedException.throwIfIsDestroyed(this);

        if (!scripts.contains(script))
            return;

        script.setGameObject(null);

        scripts.remove(script);
        updateScriptsArray();
    }

    void init()
    {
        childrenArray = new GameObject[0];
        scriptsArray = new Script[0];
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
        for (Script script : scriptsArray)
        { 
            if (!script.awakedOnce)
            { 
                script.awakedOnce = true; 
                script.awake();
            }
        }
            
        for (Script script : scriptsArray)
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

    private void updateChildrenArray()
    {
        childrenArray = children.toArray(new GameObject[children.size()]);
    }

    private void updateScriptsArray()
    {
        scriptsArray = scripts.toArray(new Script[scripts.size()]);
    }

    private void runForAllScripts(Action1<Script> action)
    {
        for (Script script : scriptsArray)
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