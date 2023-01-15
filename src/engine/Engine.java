package engine;

import engine.math.FinalVector;
import engine.math.Vector4;
import engine.utils.Screen;
import engine.utils.activatable.IActivatable;
import engine.utils.time.GameLoop;
import engine.window.Window;
import engine.window.WindowLayer;
import engine.window.Input.Input;

public final class Engine implements IActivatable
{
    private final GameWindow window;
    private final GameLoop gameLoop;
 
    private Scene sceneToLoad;
    private Scene activeScene;

    private boolean active;

    private WindowLayer currentImage; //Variable that is only defined when Script.render is executed

    public Engine(Screen screen, Vector4 size, int tps, int fps, String... layers)
    {
        window   = new GameWindow(screen, size, layers);
        gameLoop = new EngineGameLoop(this, tps, fps);

        activeScene = sceneToLoad = null;

        active = false;
        
        currentImage = null;
    }

    public void setActiveScene(Scene scene) { sceneToLoad = scene; }

    @SuppressWarnings("unchecked") public <T extends Scene> T activeScene() { return (T) activeScene; }
    @SuppressWarnings("unchecked") public <T extends Scene> T sceneToLoad() { return (T) sceneToLoad; }

    public Window        window        () { return window                            ; }
    public Input         input         () { return window.input()                    ; }
    public WindowLayer   image         () { return currentImage                      ; }

    public double        fps           () { return gameLoop.currentFPS()             ; }
    public double        tps           () { return gameLoop.currentTPS()             ; }

    public double        frameSkips    () { return gameLoop.frameSkips()             ; }

    public double        deltaTime     () { return frameDeltaTime() + tickDeltaTime(); }
    public double        frameDeltaTime() { return gameLoop.frameDeltaTimeInSeconds(); }
    public double        tickDeltaTime () { return gameLoop.tickDeltaTimeInSeconds (); }

    public String toString()
    {
        return "Engine(tps: " + tps() + " | fps: " + fps() + " | dt: " + deltaTime() + ")";
    }

    protected void onActivate  () { gameLoop.startThread(); }
    protected void onDeactivate() { gameLoop.stopThread (); }

    private final class GameWindow extends Window
    {
        GameWindow(final Screen screen, final Vector4 size, final String[] layers) 
        {
            super(screen, size, layers);
        }
        
        public void renderImage(WindowLayer image)
        {
            currentImage = image;

            image.clear();
            
            if (activeScene != null) 
                activeScene.render();

            currentImage = null;
        }
    }

    public static final Engine fastSetup(Script script)
    {
        Engine engine = new Engine(Screen.get(0), new FinalVector(1080, 720), 60, 60, "main");

        Scene scene = new Scene() 
        { 
            @Override
            protected void init() 
            {
                GameObject gameObject = new GameObject();  
                
                addGameObject(gameObject);
            
                gameObject.addScript(script);
        
                gameObject.activate();
                script.activate();
            }
        };

        engine.setActiveScene(scene);
        
        return engine;
    }

    @Override
    public void setActive(boolean state) 
    {
        if (active == state) return;

        active = state;

        if (active) onActivate  ();
        else        onDeactivate();
    }

    @Override public boolean isActive() { return active; }

    /*
     * EXECUTION
     * 
     * Engine Activate
     *     ↓
     * Create and show Window
     *     ↓
     * mainLoop: while active
     *     If there is a scene to load, destroy old scene if there is one and load the new scene
     *     Start
     *     Update
     *     Render
     * mainLoop: end because Engine Deactivate 
     *     ↓
     * Close Window
     */
    private final class EngineGameLoop extends GameLoop
    {
        final Engine engine;

        public EngineGameLoop(Engine engine, int TPS, int FPS) { super(TPS, FPS); this.engine = engine; }

        private void tryLoadScene()
        {
            if (sceneToLoad == null) return;
            
            if (activeScene != null)
            {
                activeScene.onSceneChange();
                activeScene.destroy      ();
            }

            activeScene = sceneToLoad; 
            
            sceneToLoad = null;

            activeScene.setEngine(engine);

            activeScene.init();
        }

        @Override
        public void start() 
        {
            window.start();

            tryLoadScene();
        }

        @Override
        public void end() 
        {
            if (activeScene != null) activeScene.destroy();
            if (sceneToLoad != null) sceneToLoad.destroy();

            activeScene = null;
            sceneToLoad = null;

            window.dispose();
        }

        @Override
        public void update() 
        {
            tryLoadScene();

            if (activeScene == null) return;
            
            activeScene.update();
            input().update();
        }

        @Override
        public void render() 
        {
            window.renderImages(); 
            window.renderImagesOntoWindowBuffer();
            window.renderWindowBufferOntoFrame();
        }

        @Override
        public boolean isActive() 
        {
            return input().isActive() && engine.isActive();  
        }
    }
}
