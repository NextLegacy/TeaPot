#include "Thread.hpp"

namespace BHW
{
    Thread::Thread(std::function<void()> function) 
        : m_function  (function), 
          m_isRunning (false   ), 
          m_shouldStop(false   ) { }

    Thread::~Thread()
    {
        Deactivate();
    }

    void Thread::Activate()
    {
        if (m_isRunning && !m_shouldStop)
        {
            Deactivate();
            Join();
        }

        m_isRunning = true;
        
        m_thread = std::thread(m_function);
    }

    void Thread::Deactivate()
    {
        m_isRunning = false;
     
        m_shouldStop = true;

        Join();

        m_thread.detach();

        delete &m_thread;

        m_shouldStop = false;
    }

    void Thread::Join()
    {
        m_thread.join();
    }
}