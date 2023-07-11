#pragma once

#include <thread>

#include <functional>

namespace BHW
{
    class Thread
    {
    private:
        std::thread m_thread;

        bool m_shouldStop;
        bool m_isRunning;

        std::function<void()> m_function;

    public:
        Thread(std::function<void()> function);

        ~Thread();

        void Activate  ();

        void Deactivate();

        void Join      ();

        inline bool IsRunning () const { return m_isRunning ; }
        inline bool ShouldStop() const { return m_shouldStop; }
    };
}