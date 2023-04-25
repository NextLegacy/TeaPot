package engine;

import engine.utils.ArrayUtils;
import engine.utils.activatable.IActivatable;
import engine.utils.activatable.ObjectIsNotActiveException;
import engine.utils.destroyable.IDestroyable;
import engine.utils.destroyable.ObjectIsDestroyedException;
import engine.window.Window;

import engine.utils.ArrayUtils.ArrayFunctions.*;

import static engine.utils.ArrayUtils.*;

/**
 * A scene is a collection of GameObjects that get updated and rendered by the engine.
 * To load a scene, use the {@link Engine#loadScene(Scene)} method.
 * 
 * @author NextLegacy
 * @version 1.0.0
 */
public abstract class Scene implements IActivatable, IDestroyable
{
    private Engine engine;
    
    private GameObject[] currentGameObjects;
    private GameObject[] gameObjectsInScene;

    private boolean isDestroyed;

    public Scene()
    {
        engine             = null             ;
        gameObjectsInScene = new GameObject[0];
        currentGameObjects = new GameObject[0];
    }

    void setEngine(Engine engine) { this.engine = engine; }

    public final Engine  engine       () { throwIfIsUnvalid(this); return engine           ; }
    public final Window  window       () { throwIfIsUnvalid(this); return engine.window()  ; }
    public final boolean isActiveScene() { return engine != null && this == engine.activeScene(); }
    public final boolean isValidScene () { return isActiveScene() && !isDestroyed()             ; }

    public final void addGameObject(GameObject gameObject)
    {
        throwIfIsUnvalid(this);

        if (contains(gameObjectsInScene, gameObject))
            return;

        gameObject.setScene(this);

        gameObjectsInScene = push(gameObjectsInScene, gameObject);
    }
    
    public final void removeGameObject(GameObject gameObject)
    {
        throwIfIsUnvalid(this);

        if (!contains(gameObjectsInScene, gameObject))
            return;

        gameObject.setScene(null);
    
        gameObjectsInScene = remove(gameObjectsInScene, gameObject);
    }

    public final GameObject[] gameObjects()
    {
        throwIfIsUnvalid(this);

        return ArrayUtils.clone(gameObjectsInScene);
    }

    public final GameObject getGameObject(String name)
    {
        throwIfIsUnvalid(this);

        for (GameObject gameObject : gameObjectsInScene)
            if (gameObject.name() == name)
                return gameObject;
            
        return null;
    }

    public final GameObject[] getGameObjects(GenericFilter<GameObject> filter)
    {
        throwIfIsUnvalid(this);

        return ArrayUtils.filter(gameObjectsInScene, filter);
    }

    @Override
    public void setActive(boolean state) 
    {
        throw new UnsupportedOperationException("This method is not supported for the Scene class. Scenes can only be activated or deactivated by Engine class.");
    }

    @Override
    public boolean isActive() { return isActiveScene(); }

    protected void init() { }
    protected void end () { } //destroy equivalent
    
    void update() 
    { 
        updateGameObjects();

        for (GameObject gameObject : currentGameObjects)
            if (gameObject.isActive())
                gameObject.update();
    }
    
    void render() 
    {
        updateGameObjects();
        
        for (GameObject gameObject : currentGameObjects)
            if (gameObject.isActive())
                gameObject.render();
    }

    void onSceneChange() 
    {
        for (GameObject gameObject : currentGameObjects)
            gameObject.onSceneChange();
    }

    void updateGameObjects()
    {
        currentGameObjects = gameObjectsInScene;
    }

    public final boolean isDestroyed() { return isDestroyed; }

    @Override
    public final void destroy()
    {
        if (isDestroyed) return; 

        updateGameObjects();

        for (int i = 0; i < currentGameObjects.length; i++)
            currentGameObjects[i].destroy();

        isDestroyed        = true;

        engine             = null;

        gameObjectsInScene = null;
        currentGameObjects = null;

        end();
    }

    public static final Scene emptyScene()
    {
        return new Scene() { };
    }

    private static final void throwIfIsUnvalid(Scene scene)
    {
        ObjectIsDestroyedException.throwIfIsDestroyed(scene);
        ObjectIsNotActiveException.throwIfIsNotActive(scene);
    }
}