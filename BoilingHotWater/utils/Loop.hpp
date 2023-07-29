#pragma once

#include "utils/Time.hpp"
#include <thread>
#include "utils/Console.hpp"

namespace BHW
{
    class Loop
    {
    public:
        Loop(int ticksPerSecond, int framesPerSecond);

        void SetTicksPerSecond(int ticksPerSecond);
        void SetFramesPerSecond(int framesPerSecond);

        void StartThread();
        void StopThread();

        void JoinThread();

        inline uint64_t TPS() const { return m_ticks ; }
        inline uint64_t FPS() const { return m_frames; }

    protected:
        virtual void Start    () = 0;
    
        virtual void Tick     () = 0;
        virtual void Frame    () = 0;

        virtual void PerSecond() = 0;

        virtual void End      () = 0;

        virtual bool isActive () = 0;

    private:
        void InitializeRun();

        void Run();
        
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
    };
}