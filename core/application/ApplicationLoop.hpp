#pragma once

#include "utils/Time.hpp"
#include <thread>

namespace DigitalTea
{
    class ApplicationLoop
    {
    private:
        std::thread m_thread;

        int m_ticksPerSecond;
        int m_framesPerSecond;

        float m_tickInterval;
        float m_frameInterval;

        float m_deltaTicks;
        float m_deltaFrames;

        uint64_t m_ticks;
        uint64_t m_frames;

        uint64_t m_now;
        uint64_t m_last;

        uint64_t m_elapsedTime;
        
        uint64_t m_time;

        uint64_t m_counter;

        bool m_skipFrame;

        bool m_shouldStop;
        bool m_isRunning;

    public:
        ApplicationLoop(int ticksPerSecond, int framesPerSecond);

        void SetTicksPerSecond(int ticksPerSecond);
        void SetFramesPerSecond(int framesPerSecond);

        void StartThread();
        void StopThread();

        void JoinThread();

    protected:
        virtual void Start() = 0;
    
        virtual void Update() = 0;
        virtual void Render() = 0;

        virtual void PerSecond() = 0;

        virtual void End() = 0;

        virtual bool isActive() = 0;

    private:
        void InitializeRun();

        void Run();
    };
}