#pragma once

#include <mutex>
#include <condition_variable>

#include <BHW/utils/threads/Thread.hpp>
#include <BHW/utils/Time.hpp>

#include <BHW/utils/console/Console.hpp>

namespace TC
{
    class ApplicationRuntime
    {
    public:
        ApplicationRuntime();
    
        void StartRuntime();

        inline uint64_t GetTPS () { return m_measuredTicksPerSecond + 0.5 ; }
        inline uint64_t GetFTPS() { return m_measuredFixedTicksPerSecond + 0.5; }
        inline uint64_t GetFPS () { return m_measuredFramesPerSecond + 0.5; }

        inline float TickDeltaTime () { return m_deltaTickTime     ; }
        inline float FixedTickDelta() { return m_deltaFixedTickTime; }
        inline float FrameDeltaTime() { return m_deltaFrameTime    ; }

        virtual bool IsRunning() = 0;

    protected:
        virtual void Tick     () = 0;
        virtual void FixedTick() = 0;
        virtual void Frame    () = 0;

    private:
        void TickThread();
        void FixedTickThread();

        void FrameThread();

    private:
        float m_deltaTickTime;
        float m_deltaFixedTickTime;
        float m_deltaFrameTime;

        uint64_t m_fps;
        uint64_t m_ftps;
        uint64_t m_tps;

        float m_measuredFramesPerSecond;
        float m_measuredTicksPerSecond;
        float m_measuredFixedTicksPerSecond;

        BHW::Thread m_tickThread;
        BHW::Thread m_fixedTickThread;

        std::mutex m_tickMutex;
        std::condition_variable m_tickCondition;
        bool m_fixedTicksDone;
    };
}