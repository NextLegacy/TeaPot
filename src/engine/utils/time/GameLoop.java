package engine.utils.time;

/**
 * This class is used to create a game loop.
 * 
 * Provides various usefull methods:
 * 
 * <ul>
 * <li>{@link #start()} - gets called when the game loop starts</li>
 * <li>{@link #update(double)} - gets called every tick</li>
 * <li>{@link #render(double)} - gets called every frame</li>
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
    private boolean shouldStopThread;

    private int FPS;
    private int TPS;

    private double TICK_INTERVAL;
    private double FRAME_INTERVAL;

    private double TICK_INTERVAL_IN_SECONDS;
    private double FRAME_INTERVAL_IN_SECONDS;

    private long   elapsedTime;
    private double elapsedTimeInSeconds;

    private double currentFPS;
    private double currentTPS;

    private double frameSkips;

    private double frameDeltaTime;
    private double frameDeltaTimeInSeconds;
    private double tickDeltaTime;
    private double tickDeltaTimeInSeconds;

    // FIXME: actual tps and fps vary from the given tps and fps, investigate on this
    public GameLoop(int TPS, int FPS) { setTPS(TPS); setFPS(FPS); }

    public void setTPS(int TPS)
    {
        this.TPS = TPS;

        TICK_INTERVAL            = 1_000_000_000.0d / TPS;
        TICK_INTERVAL_IN_SECONDS =             1.0d / TPS;
    }

    public void setFPS(int FPS)
    {
        this.FPS = FPS;

        FRAME_INTERVAL            = 1_000_000_000.0d / FPS;
        FRAME_INTERVAL_IN_SECONDS =             1.0d / FPS;
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

    public boolean isThreadRunning        () { return isThreadRunning        ; }

    public double  currentFPS             () { return currentFPS             ; }
    public double  currentTPS             () { return currentTPS             ; }

    public double  frameSkips             () { return frameSkips             ; }

    public double  frameDeltaTime         () { return frameDeltaTime         ; }
    public double  frameDeltaTimeInSeconds() { return frameDeltaTimeInSeconds; }
    public double  tickDeltaTime          () { return tickDeltaTime          ; }
    public double  tickDeltaTimeInSeconds () { return tickDeltaTimeInSeconds ; }

    public double  elapsedTime            () { return elapsedTime            ; }
    public double  elapsedTimeInSeconds   () { return elapsedTimeInSeconds   ; }

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
        elapsedTimeInSeconds = 0.0d;

        double deltaT = 0.0d;
        double deltaF = 0.0d;
        long   time   = 0l  ;

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
            elapsedTimeInSeconds = elapsedTime * Time.NANOS_TO_SECONDS;

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
                tickDeltaTimeInSeconds = tickDeltaTime * Time.NANOS_TO_SECONDS;

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
                frameDeltaTimeInSeconds = frameDeltaTime * Time.NANOS_TO_SECONDS;

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
