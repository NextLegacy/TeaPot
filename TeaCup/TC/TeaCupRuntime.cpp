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
        // fixing tps
        uint64_t deltaTime = 0;
        uint64_t deltaT    = 0;
        uint64_t last      = BHW::Time::NanoSeconds();

        // measuring tick time
        uint64_t lastTickTime = BHW::Time::NanoSeconds();

        while (IsRunning())
        {
            if (deltaT >= 1e9)
            {
                m_deltaTickTime = (float) (BHW::Time::NanoSeconds() - lastTickTime) / 1.0e9f;
                lastTickTime    = BHW::Time::NanoSeconds();

                m_measuredTicksPerSecond = 1 / m_deltaTickTime;

                Tick();

                deltaT -= 1e9;
            }

            //{
            //    m_fixedTicksDone = false;
            //    std::unique_lock<std::mutex> lock(m_tickMutex);
            //    m_tickCondition.wait(lock, [this] { return m_fixedTicksDone; });
            //    m_fixedTicksDone = false;
            //}

            deltaTime  = BHW::Time::NanoSeconds() - last;
            last      += deltaTime;
            deltaT    += deltaTime * m_tps;
        }
    }

    void ApplicationRuntime::FrameThread()
    {
        // fixing fps
        uint64_t deltaTime = 0;
        uint64_t deltaF    = 0;
        uint64_t last      = BHW::Time::NanoSeconds();

        // measuring frame time
        uint64_t lastFrameTime = BHW::Time::NanoSeconds();

        while (IsRunning())
        {
            if (deltaF >= 1e9)
            {
                m_deltaFrameTime = (float) (BHW::Time::NanoSeconds() - lastFrameTime) / 1.0e9f;
                lastFrameTime = BHW::Time::NanoSeconds();

                m_measuredFramesPerSecond = 1 / m_deltaFrameTime;

                Frame(); 

                deltaF -= 1e9;
            }

            deltaTime = BHW::Time::NanoSeconds() - last;
            last     += deltaTime;
            deltaF   += deltaTime * m_fps;
        }
    }
}