package engine;

import engine.utils.ArrayUtils;
import engine.utils.destroyable.IDestroyable;
import engine.window.Window;

import static engine.utils.ArrayUtils.*;

public abstract class Scene implements IDestroyable
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

    public final boolean isDestroyed() { return isDestroyed; }

    @Override
    public final void destroy()
    {
        if (isDestroyed) return; 

        updateGameObjects();

        end();

        for (int i = 0; i < currentGameObjects.length; i++)
            currentGameObjects[i].setScene(null);

        if (engine.sceneToLoad() == null)
            engine.setActiveScene(null);

        isDestroyed = true;

        engine             = null;

        gameObjectsInScene = null;
        currentGameObjects = null;
    }

    public final Engine  engine       () { return engine                                        ; }
    public final Window  window       () { return engine.window()                               ; }
    public final boolean isActiveScene() { return engine != null && this == engine.activeScene(); }

    public final void addGameObject(GameObject gameObject)
    {
        throwIfIsNotActiveScene();

        if (contains(gameObjectsInScene, gameObject))
            return;

        gameObject.setScene(this);

        gameObjectsInScene = push(gameObjectsInScene, gameObject);
    }
    
    public final void removeGameObject(GameObject gameObject)
    {
        throwIfIsNotActiveScene();

        if (!contains(gameObjectsInScene, gameObject))
            return;

        gameObject.setScene(null);
    
        gameObjectsInScene = remove(gameObjectsInScene, gameObject);
    }

    public final GameObject[] gameObjects()
    {
        return ArrayUtils.clone(gameObjectsInScene);
    }

    public final GameObject getGameObject(String name)
    {
        for (GameObject gameObject : gameObjectsInScene)
            if (gameObject.name() == name)
                return gameObject;
            
        return null;
    }

    protected void init() { }
    protected void end () { } //destroy equivalend
    
    void update() 
    { 
        for (GameObject gameObject : currentGameObjects)
            if (gameObject.isActive())
                gameObject.update();

        updateGameObjects();
    }
    
    void render() 
    {
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
        currentGameObjects = ArrayUtils.clone(gameObjectsInScene);
    }

    private void throwIfIsNotActiveScene() 
    {
        if (!isActiveScene())
            throw new SceneIsNotActiveException(this);
    }

    private static final class SceneIsNotActiveException extends RuntimeException
    {
        public SceneIsNotActiveException(Scene scene)
        {
            super("Can not perform Action, " + scene + " is not the active Scene!");
        }
    }
}