#include "TC/TeaCupRuntime.hpp"

namespace TC
{
    ApplicationRuntime::ApplicationRuntime() : 
        m_deltaTickTime          (0),
        m_deltaFixedTickTime     (0), 
        m_deltaFrameTime         (0),
        m_measuredFramesPerSecond(0), 
        m_measuredTicksPerSecond (0), 
        m_tps (60), 
        m_ftps(100),
        m_fps (60),
        m_tickThread     (std::bind(&ApplicationRuntime::TickThread     , this)),
        m_fixedTickThread(std::bind(&ApplicationRuntime::FixedTickThread, this))
    {

    }

    void ApplicationRuntime::StartRuntime()
    {
        m_tickThread.Activate();
        m_fixedTickThread.Activate();

        FrameThread();

        m_tickThread.Deactivate();
        m_fixedTickThread.Deactivate();
    }

    void ApplicationRuntime::FixedTickThread()
    {
        // measuring fixed ticks per second
        uint64_t measurementStartTime  = BHW::Time::NanoSeconds();
        uint64_t measurementSamples    = 1000;
        uint64_t measurementFixedTicks = 0;

        // fixing ftps
        uint64_t deltaTime   = 0;
        float    accumulator = 0;
        float    slice       = 1.0e9f / (float) m_ftps;
        uint64_t last        = BHW::Time::NanoSeconds();

        while (IsRunning())
        {
            deltaTime    = BHW::Time::NanoSeconds() - last;
            last        += deltaTime;
            accumulator += deltaTime;

            bool atLeastOne = accumulator > slice;

            while (accumulator > slice)
            {
                m_deltaFixedTickTime = slice / 1e9f;

                FixedTick(); 
                
                measurementFixedTicks++;

                accumulator -= slice;
            }

            if (atLeastOne)
            {
                std::lock_guard<std::mutex> lock(m_tickMutex);
                m_fixedTicksDone = true;
                m_tickCondition.notify_one();
            }

            m_measuredFixedTicksPerSecond = ((float) measurementFixedTicks / ((float)(BHW::Time::NanoSeconds() - measurementStartTime))) * 1e9f;

            if (measurementFixedTicks >= measurementSamples)
            {
                measurementStartTime  = BHW::Time::NanoSeconds();
                measurementFixedTicks = 0;
            }
        }
    }

    void ApplicationRuntime::TickThread()
    {
        // measuring ticks per second
        uint64_t measurementStartTime = BHW::Time::NanoSeconds();
        uint64_t measurementSamples   = 1000;
        uint64_t measurementTicks     = 0;
        
        // fixing tps
        uint64_t deltaTime = 0;
        float   deltaT    = 0;
        uint64_t last      = BHW::Time::NanoSeconds();

        while (IsRunning())
        {
            deltaTime  = BHW::Time::NanoSeconds() - last;
            last      += deltaTime;

            if (deltaT >= 1)
            {
                m_deltaTickTime = deltaTime;

                Tick();

                measurementTicks++;
                deltaT--;
            }

            {
                m_fixedTicksDone = false;
                std::unique_lock<std::mutex> lock(m_tickMutex);
                m_tickCondition.wait(lock, [this] { return m_fixedTicksDone; });
                m_fixedTicksDone = false;
            }

            deltaT += deltaTime / (1.0e9f / (float) m_tps);

            m_measuredTicksPerSecond = ((float) measurementTicks / ((float)(BHW::Time::NanoSeconds() - measurementStartTime))) * 1e9;

            if (measurementTicks >= measurementSamples)
            {
                measurementStartTime = BHW::Time::NanoSeconds();
                measurementTicks     = 0;
            }
        }
    }

    void ApplicationRuntime::FrameThread()
    {
        // measuring frames per second
        uint64_t measurementStartTime  = BHW::Time::NanoSeconds();
        uint64_t measurementSamples    = 1000;
        uint64_t measurementFrames     = 0;
        uint64_t lastDeltaTime         = 0;

        // fixing fps
        uint64_t deltaTime = 0;
        float    deltaF    = 0;
        uint64_t last      = BHW::Time::NanoSeconds();

        while (IsRunning())
        {
            deltaTime  = BHW::Time::NanoSeconds() - last;
            last      += deltaTime;

            if (deltaF >= 1)
            {
                m_deltaFrameTime = deltaTime;

                Frame(); 
                measurementFrames++; 
                deltaF--;
            }

            deltaF += (float) deltaTime / (1.0e9f / (float) m_fps);


            m_measuredFramesPerSecond = ((float) measurementFrames / ((float) (BHW::Time::NanoSeconds() - measurementStartTime))) * 1e9;

            if (measurementFrames >= measurementSamples)
            {
                measurementStartTime = BHW::Time::NanoSeconds();
                measurementFrames    = 0;
            }
        }
    }
}