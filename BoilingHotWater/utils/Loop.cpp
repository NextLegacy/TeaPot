#include "Loop.hpp"

namespace BHW
{
    Loop::Loop(int ticksPerSecond, int framesPerSecond)
        : m_ticksPerSecond(ticksPerSecond       ), m_framesPerSecond(framesPerSecond       ),
          m_tickInterval  (1.0e9f / ticksPerSecond), m_frameInterval  (1.0e9f / framesPerSecond)
    {
    }

    void Loop::SetTicksPerSecond(int ticksPerSecond)
    {
        m_ticksPerSecond = ticksPerSecond;
    }

    void Loop::SetFramesPerSecond(int framesPerSecond)
    {
        m_framesPerSecond = framesPerSecond;
    }

    void Loop::StartThread()
    {
        Console::LogLn("Loop::StartThread()");
        m_thread = std::thread(&Loop::Run, this);
    }

    void Loop::StopThread()
    {
        m_shouldStop = true;

        JoinThread();

        m_thread.detach();
    }

    void Loop::JoinThread()
    {
        m_thread.join();
    }

    void Loop::InitializeRun()
    {
        m_ticks  = 0;
        m_frames = 0;

        m_deltaTicks  = 0;
        m_deltaFrames = 0;

        m_now  = Time::Now();
        m_last = m_now;

        m_elapsedTime = 0;

        m_time = 0;

        m_counter = 0;

        m_skipFrame = true;

        m_shouldStop = false;
        m_isRunning  = true;
    }

    void Loop::Run()
    {
        InitializeRun();

        Start();

        while 
        (
            !m_shouldStop && // stop using StopThread()
            isActive()       // stop using isActive() in derived class
        )
        {
            m_now = Time::Now();

            m_skipFrame &= m_deltaTicks >= 1.0f;

            while (m_deltaTicks >= 1.0f) // update with respect to requested ticks per second
            {
                Tick();

                m_ticks++;

                m_deltaTicks--;
            }

            if 
            (
                m_deltaFrames >= 1.0f && // render with respect to requested frames per second
                !m_skipFrame             // only render if an update has occurred
            )
            {
                Frame();

                m_frames++;

                m_deltaFrames--;

                m_skipFrame = false;
            }
            
            m_last = m_now;

            m_now = Time::Now();

            m_elapsedTime = m_now - m_last;

            m_time    += m_elapsedTime;
            m_counter += m_elapsedTime;

            m_deltaTicks  += m_elapsedTime / m_tickInterval;
            m_deltaFrames += m_elapsedTime / m_frameInterval;

            //Console::LogLn(std::to_string(m_counter));

            if (m_counter >= 1e9)
            {
                m_counter = 0;

                PerSecond();

                m_ticks  = 0;
                m_frames = 0;

                m_deltaTicks  = 0;
                m_deltaFrames = 0;
            }
        }

        m_isRunning = false;

        End();
    }
}