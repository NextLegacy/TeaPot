package engine.utils.time;

/**
 * This class is used to create a game loop.
 * 
 * Provides various usefull methods:
 * 
 * <ul>
 * <li>{@link #start()} - gets called when the game loop starts</li>
 * <li>{@link #update(float)} - gets called every tick</li>
 * <li>{@link #render(float)} - gets called every frame</li>
 * <li>{@link #end()} - gets called when the game loop ends</li> 
 * </ul>
 * 
 * @author NextLegacy
 * @version 1.0
 */
public abstract class GameLoop implements Runnable
{
    private Thread thread;
    
    private boolean isThreadRunning;
    private volatile boolean shouldStopThread;

    private int FPS;
    private int TPS;

    private float TICK_INTERVAL;
    private float FRAME_INTERVAL;

    private float TICK_INTERVAL_IN_SECONDS;
    private float FRAME_INTERVAL_IN_SECONDS;

    private long  elapsedTime;
    private float elapsedTimeInSeconds;

    private float currentFPS;
    private float currentTPS;

    private float frameSkips;

    private float frameDeltaTime;
    private float frameDeltaTimeInSeconds;
    private float tickDeltaTime;
    private float tickDeltaTimeInSeconds;

    public GameLoop(int TPS, int FPS) { setTPS(TPS); setFPS(FPS); }

    public void setTPS(int TPS)
    {
        this.TPS = TPS;

        TICK_INTERVAL            = 1e9f / TPS;
        TICK_INTERVAL_IN_SECONDS = 1.0f / TPS;
    }

    public void setFPS(int FPS)
    {
        this.FPS = FPS;

        FRAME_INTERVAL            = 1e9f / FPS;
        FRAME_INTERVAL_IN_SECONDS = 1.0f / FPS;
    }

    public void startThread()
    {
        if (!isThreadRunning && thread != null) return;

        shouldStopThread = false;
        
        thread = new Thread(this);
        thread.start();
    }

    public void stopThread()
    {
        shouldStopThread = true;
    }

    public void forceStopThread()
    {
        thread.interrupt();
        thread = null;
    }

    public boolean isThreadRunning       () { return isThreadRunning       ; }

    public float  currentFPS             () { return currentFPS             ; }
    public float  currentTPS             () { return currentTPS             ; }

    public float  frameSkips             () { return frameSkips             ; }

    public float  frameDeltaTime         () { return frameDeltaTime         ; }
    public float  frameDeltaTimeInSeconds() { return frameDeltaTimeInSeconds; }
    public float  tickDeltaTime          () { return tickDeltaTime          ; }
    public float  tickDeltaTimeInSeconds () { return tickDeltaTimeInSeconds ; }

    public float  elapsedTime            () { return elapsedTime            ; }
    public float  elapsedTimeInSeconds   () { return elapsedTimeInSeconds   ; }

    public abstract boolean isActive();

    protected abstract void start ();
    protected abstract void end   ();
    protected abstract void update();
    protected abstract void render();

    @Override
    public void run()
    {
        isThreadRunning = true;

        elapsedTime          = 0l  ;
        elapsedTimeInSeconds = 0.0f;

        float deltaT = 0.0f;
        float deltaF = 0.0f;
        long  time   = 0l  ;

        long now  = Time.nanos(); 
        long last = now         ;

        int ticks  = TPS;
        int frames = FPS;
        
        currentTPS = 0;
        currentFPS = 0;

        frameDeltaTime = FRAME_INTERVAL_IN_SECONDS;
        tickDeltaTime  = TICK_INTERVAL_IN_SECONDS ;
        
        start();

        while(!shouldStopThread && isActive())
        {
            now = Time.nanos();

            elapsedTime = now - last;
            elapsedTimeInSeconds = elapsedTime * 1.0E-9f;

            last = now;
            
            deltaT += elapsedTime / TICK_INTERVAL;
            deltaF += elapsedTime / FRAME_INTERVAL;

            time += elapsedTime;

            while (
                    deltaT >= 1 && // update while respecting intended tps
                    isActive()     // isActive() check, because updates might turned off engine
                  )
            {
                final long beforeUpdate = Time.nanos();

                update();
             
                final long afterUpdate = Time.nanos();

                tickDeltaTime = (afterUpdate - beforeUpdate);
                tickDeltaTimeInSeconds = tickDeltaTime * 1.0E-9f;

                ticks++;
                deltaT--;
            }
        
            //After updates, gameloop or engine might be deactivated, no need to continue, therefore break from gameloop
            if (!isActive())
                break;

            if (deltaF >= 1)
            {
                final long beforeRender = Time.nanos();

                render();

                final long afterRender = Time.nanos();

                frameDeltaTime = (afterRender - beforeRender);
                frameDeltaTimeInSeconds = frameDeltaTime * 1.0E-9f;

                frames++;
                deltaF--;
            }
            
            frameSkips = deltaF >= 1 ? deltaF - 1 : 0;

            if(time >= 1e9) //If one Second passed
            {
                currentTPS = ticks;
                currentFPS = frames;

                ticks  = 0;
                frames = 0;
                time   = 0;
            }
        }

        end();

        isThreadRunning  = false;
        shouldStopThread = false;
    }
}
