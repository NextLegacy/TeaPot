package engine;

import java.util.ArrayList;

import engine.utils.ArrayUtils;
import engine.utils.Lambda.Action1;
import engine.window.Window;

import static engine.utils.ArrayUtils.*;

public abstract class Scene
{
    private static int scenesCounter = 0;

    private final String name;
    
    private Engine engine;

    private GameObject[] gameObjects;
    private GameObject[] currentGameObjects;
    
    public Scene() { this("Scene " + ++scenesCounter); } 

    public Scene(String name)
    {
        this.name = name;

        engine             = null             ;

        gameObjects        = new GameObject[0];   
        currentGameObjects = new GameObject[0];
    }

    void makeActiveScene(Engine engine) { this.engine = engine; }

    final void destroy()
    {
        runForAllGameObjects(GameObject::destroy);

        if (engine.sceneToLoad() == null)
            engine.setActiveScene(null);
        
        engine = null;
    }

    public final String  name         () { return name                                          ; }
    public final Engine  engine       () { return engine                                        ; }
    public final Window  window       () { return engine.window()                               ; }
    public final boolean isActiveScene() { return engine != null && this == engine.activeScene(); }

    @Override
    public String toString() { return "Scene(name: " + name + ")"; }

    public final void addGameObject(GameObject gameObject)
    {
        throwIfIsNotActiveScene();

        if (ArrayUtils.contains(gameObjects, gameObject))
            return;

        gameObject.setScene(this);

        gameObjects = push(gameObjects, gameObject);

        //if (isActiveScene() && gameObject.isActive())
        //    gameObject.start();
    }
    
    public final void removeGameObject(GameObject gameObject)
    {
        throwIfIsNotActiveScene();

        if (!contains(gameObjects, gameObject))
            return;

        gameObject.setScene(null);
        gameObject.destroy();
    
        gameObjects = remove(gameObjects, gameObject);

        end();
    }

    public final GameObject[] getGameObjects()
    {
        throwIfIsNotActiveScene();

        return ArrayUtils.clone(gameObjects);
    }

    public final GameObject getGameObject(String name)
    {
        throwIfIsNotActiveScene();

        for (GameObject gameObject : gameObjects)
            if (gameObject.name() == name)
                return gameObject;
            
        return null;
    }

    protected void init() { }
    protected void end () { } //destroy equivalend
    
    void update() { runForAllGameObjects(GameObject::update); }
    void render() { runForAllGameObjects(GameObject::render); }

    void onSceneChange() { runForAllGameObjects(GameObject::onSceneChange); }

    void updateGameObjects()
    {
        currentGameObjects = ArrayUtils.clone(gameObjects);
    }

    private void throwIfIsNotActiveScene() 
    {
        if (!isActiveScene())
            throw new SceneIsNotActiveException(this);
    }

    private void runForAllGameObjects(final Action1<GameObject> action)
    {   
        updateGameObjects();

        for(GameObject gameObject : currentGameObjects)
            if (gameObject.isActive())
                action.run(gameObject);
    }

    private static final class SceneIsNotActiveException extends RuntimeException
    {
        public SceneIsNotActiveException(Scene scene)
        {
            super("Can not perform Action, " + scene + " is not the active Scene!");
        }
    }
}