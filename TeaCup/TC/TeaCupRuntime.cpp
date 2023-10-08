#include "TC/TeaCupRuntime.hpp"

namespace TC
{
    ApplicationRuntime::ApplicationRuntime() : 
        m_deltaUpdateTime     (0),
        m_deltaFixedUpdateTime(0),
        m_deltaFrameTime      (0),

        m_fps (60 ),
        m_fups(100),

        m_measuredFramesPerSecond      (0),
        m_measuredUpdatesPerSecond     (0),
        m_measuredFixedUpdatesPerSecond(0),

        m_fixedUpdateThread(std::bind(&ApplicationRuntime::FixedUpdateThread, this)),
        m_renderThread     (std::bind(&ApplicationRuntime::RenderThread    , this)),

        m_isRunning(false)
    {

    }

    void ApplicationRuntime::StartRuntime() 
    {
        m_isRunning = true;

        MainThread();
    }

    void ApplicationRuntime::EndRuntime  () { }

    void ApplicationRuntime::MainThread()
    {
        // fixing tps
        uint64_t deltaTime = 0;
        uint64_t deltaT    = 0;
        uint64_t last      = BHW::NanoSeconds();

        // measuring tick time
        uint64_t lastTickTime = BHW::NanoSeconds();

        RuntimeMainThreadStart();
    
        m_renderThread.Activate();

        while (IsRunning())
        {
            //if (deltaT >= 1e9)
            {
                m_deltaUpdateTime = (float) (BHW::NanoSeconds() - lastTickTime) / 1.0e9f;
                lastTickTime      = BHW::NanoSeconds();

                m_measuredUpdatesPerSecond = 1 / m_deltaUpdateTime;

                RuntimeUpdate();

            //    deltaT -= 1e9;
            }

            deltaTime  = BHW::NanoSeconds() - last;
            last      += deltaTime;
            deltaT    += deltaTime * 60;
        }

        RuntimeMainThreadEnd();

        m_renderThread.Deactivate();
    }


    void ApplicationRuntime::FixedUpdateThread()
    {
        // measuring fixed ticks per second
        uint64_t measurementStartTime  = BHW::NanoSeconds();
        uint64_t measurementSamples    = 1000;
        uint64_t measurementFixedUpdates = 0;

        // fixing ftps
        uint64_t deltaTime   = 0;
        float    accumulator = 0;
        float    slice       = 1.0e9f / (float) m_fups;
        uint64_t last        = BHW::NanoSeconds();

        RuntimeFixedUpdateThreadStart();

        while (IsRunning())
        {
            deltaTime    = BHW::NanoSeconds() - last;
            last        += deltaTime;
            accumulator += deltaTime;

            bool atLeastOne = accumulator > slice;

            while (accumulator > slice)
            {
                m_deltaFixedUpdateTime = slice / 1e9f;

                RuntimeFixedUpdate(); 
                
                measurementFixedUpdates++;

                accumulator -= slice;
            }

            m_measuredFixedUpdatesPerSecond = ((float) measurementFixedUpdates / ((float)(BHW::NanoSeconds() - measurementStartTime))) * 1e9f;

            if (measurementFixedUpdates >= measurementSamples)
            {
                measurementStartTime  = BHW::NanoSeconds();
                measurementFixedUpdates = 0;
            }
        }

        RuntimeFixedUpdateThreadEnd();
    }

    void ApplicationRuntime::RenderThread()
    {
        // fixing fps
        uint64_t deltaTime = 0;
        uint64_t deltaF    = 0;
        uint64_t last      = BHW::NanoSeconds();

        // measuring frame time
        uint64_t lastFrameTime = BHW::NanoSeconds();

        RuntimeRenderThreadStart();

        m_fixedUpdateThread.Activate();

        while (IsRunning())
        {
            if (deltaF >= 1e9)
            {
                m_deltaFrameTime = (float) (BHW::NanoSeconds() - lastFrameTime) / 1.0e9f;
                lastFrameTime = BHW::NanoSeconds();

                m_measuredFramesPerSecond = 1 / m_deltaFrameTime;

                RuntimeRender(); 

                deltaF -= 1e9;
            }

            deltaTime = BHW::NanoSeconds() - last;
            last     += deltaTime;
            deltaF   += deltaTime * m_fps;
        }

        RuntimeRenderThreadEnd();

        m_fixedUpdateThread.Deactivate();
    }
}