package engine;

import java.util.ArrayList;

import engine.utils.ClassUtils;
import engine.utils.Lambda.Action1;
import engine.window.Window;

public abstract class Scene
{
    private static int scenesCounter = 0;

    private final String name;
    
    private Engine engine;

    private final ArrayList<GameObject> gameObjects;
    private GameObject[] gameObjectsArray;
    
    public Scene() { this("Scene " + scenesCounter++); } 

    public Scene(String name)
    {
        this.name = name;

        engine = null;
        gameObjects = new ArrayList<>();
        gameObjectsArray = new GameObject[0];   
    }

    void makeActiveScene(Engine engine) { this.engine = engine; }

    final void destroy()
    {
        runForAllGameObjects(GameObject::destroy);
        
        updateGameObjectsArray();

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

        if (gameObjects.contains(gameObject))
            return;

        gameObject.setScene(this);

        gameObjects.add(gameObject);
        updateGameObjectsArray();

        //if (isActiveScene() && gameObject.isActive())
        //    gameObject.start();
    }
    
    public final void removeGameObject(GameObject gameObject)
    {
        throwIfIsNotActiveScene();

        if (!gameObjects.contains(gameObject))
            return;

        gameObject.setScene(null);
        gameObject.destroy();
    
        gameObjects.remove(gameObject);
        updateGameObjectsArray();

        end();
    }

    public final ArrayList<GameObject> getGameObjects()
    {
        throwIfIsNotActiveScene();

        return new ArrayList<>(gameObjects);
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
    
    //void start () { runForAllGameObjects(GameObject::start ); }
    void update() { runForAllGameObjects(GameObject::update); }
    void render() { runForAllGameObjects(GameObject::render); }

    void onSceneChange() { runForAllGameObjects(GameObject::onSceneChange); }

    private void throwIfIsNotActiveScene() 
    {
        if (!isActiveScene())
            throw new SceneIsNotActiveException(this);
    }

    private void updateGameObjectsArray()
    {
        gameObjectsArray = gameObjects.toArray(new GameObject[gameObjects.size()]);   
    }

    private void runForAllGameObjects(Action1<GameObject> action)
    {   
        for(GameObject gameObject : gameObjectsArray)
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