package engine;

import engine.utils.ArrayUtils;
import engine.utils.activatable.IActivatable;
import engine.utils.destroyable.IDestroyable;
import engine.utils.destroyable.ObjectIsDestroyedException;

import static engine.utils.ArrayUtils.*;

import engine.math.Transform;

public final class GameObject implements IActivatable, IDestroyable
{
    private String name;

    public Transform transform;

    private boolean isActive   ;
    private boolean isDestroyed;

    private GameObject   parent  ;
    private GameObject[] children;

    private Script[] scripts       ;
    private Script[] currentScripts;

    private Scene scene;
    
    public GameObject() { this(""); }

    public GameObject(String name)
    {
        this.name = name;

        children       = new GameObject[0];
        scripts        = new Script    [0];
        currentScripts = new Script    [0];
    }

    void setScene(Scene newScene) 
    { 
        if (scene == newScene) return;

        scene = newScene;

        if (scene == null) return;

        for (int i = 0; i < children.length; i++)
            scene.addGameObject(children[i]);
    }

    @Override
    public boolean      isDestroyed() { return isDestroyed                 ; }
    public String       name       () { return name                      ; }
    public Transform    transform  () { return transform                 ; }
    public GameObject   parent     () { return parent                    ; }
    public GameObject[] children   () { return ArrayUtils.clone(children); }
    public Script    [] scripts    () { return ArrayUtils.clone(scripts ); }
    public Engine       engine     () { return scene.engine()            ; }
    public Scene        scene      () { return scene                     ; }

    public boolean hasChildren(final GameObject child ) { return contains(children, child ); }
    public boolean hasScript  (final Script     script) { return contains(scripts , script); }

    private final void addChildrenInternal   (GameObject gameObject) { children = push  (children, gameObject); }
    private final void removeChildrenInternel(GameObject gameObject) { children = remove(children, gameObject); }
    private final void setParentInternal     (GameObject gameObject) { parent   = gameObject                  ; }

    public void setParent(GameObject newParent)
    {
        ObjectIsDestroyedException.throwIfIsDestroyed(this);

        if (parent    == newParent || // don't set if already set
            newParent == this     )   // don't set if is self
            return; 
        
        if (parent    != null) parent   .removeChildrenInternel(this);
        if (newParent != null) newParent.addChildrenInternal   (this);

        setParentInternal(newParent);
        
        if (newParent != null && newParent.scene() != scene && newParent.scene() != null)
            newParent.scene().addGameObject(newParent);
    }
    
    public void addChildren(GameObject child)
    {
        ObjectIsDestroyedException.throwIfIsDestroyed(this);

        if (child          == null || // don't add if null
            child.parent() == this || // don't add if already has this as parent
            child          == this)   // don't add if is self
            return;

        child.setParent(this);
    }

    public void removeChildren(GameObject child)
    {
        ObjectIsDestroyedException.throwIfIsDestroyed(this);

        if (child          == null || // don't remove if null
            child.parent() != this || // don't remove if doesn't have child
            child          == this)   // don't remove if is self
            return;

        child.setParent(null);
    }

    public void addScript(Script script)
    {
        ObjectIsDestroyedException.throwIfIsDestroyed(this);

        if (script.gameObject() != null || contains(scripts, script)) return;

        script.setGameObject(this);

        scripts = push(scripts, script);
    }

    public void removeScript(Script script)
    {
        ObjectIsDestroyedException.throwIfIsDestroyed(this);

        if (!contains(scripts, script)) return;

        script.setGameObject(null);

        scripts = remove(scripts, script);
    }
    
    @Override
    public void destroy()
    {
        if (isDestroyed) return;

        // first, deactivate the object
        deactivate();

        // then, destroy all scripts and children
        for (Script script : scripts)
            script.destroy();

        for (GameObject children : children)
            children.destroy();

        // then, remove the object from the scene
        if (scene != null) 
        {
            scene.removeGameObject(this);
            
            scene = null;
        }

        // then, remove the object from the parent
        setParent(null);
        
        // then, mark the object as destroyed
        isDestroyed = true;

        // finally, remove all references
        scripts  = null;
        children = null;
        parent   = null;

        // at this point, the object is fully destroyed. Call the onDestroy method to allow the object to do last cleanup
        onDestroy();
    }
    
    void update() 
    {
        for (Script script : currentScripts)
            script.tryStartOnce();

        for (Script script : currentScripts) 
            if (script.isActive()) 
                script.update();

        updateScripts();
    }

    void render()
    { 
        for (Script script : currentScripts) 
            if (script.isActive())
                script.render(); 
    }

    void onActivate   () { updateScripts(); for (Script script : currentScripts) script.onGameObjectActivate  (); }
    void onDeactivate () { updateScripts(); for (Script script : currentScripts) script.onGameObjectDeactivate(); }
    void onDestroy    () { updateScripts(); for (Script script : currentScripts) script.onDestroy             (); }
    void onSceneChange() { updateScripts(); for (Script script : currentScripts) script.onSceneChange         (); }

    void updateScripts() { currentScripts = scripts; }

    @Override
    public void setActive(boolean state) 
    {
        if (isActive == state) return;

        isActive = state;
        
        if   (isActive) onActivate  ();
        else            onDeactivate();
    }

    @Override
    public boolean isActive() 
    {
        if (parent() == null) return isActive && !isDestroyed;

        return parent().isActive() && isActive && !isDestroyed;
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "(" + name + ")";
    }
}