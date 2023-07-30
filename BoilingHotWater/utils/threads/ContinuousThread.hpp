#pragma once

#pragma once

#include "utils/threads/Thread.hpp"
#include "utils/Time.hpp"
#include "utils/Console.hpp"

namespace BHW
{
    class ContinuousThread : public Thread
    {    
    public:
        ContinuousThread();

    protected:
        virtual void Initialize();
    
        virtual void Release   ();

        virtual void Run       ();

        virtual bool IsActive  ();

        inline uint64_t DeltaTime() const { return m_deltaTime_ns; }

    private:
        void RunThread();
    
    private:
        uint64_t m_lastTime_ns   ;
        uint64_t m_currentTime_ns;
        uint64_t m_deltaTime_ns  ;
    };
}