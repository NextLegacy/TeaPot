#pragma once

#pragma once

#include "utils/threads/Thread.hpp"
#include "utils/Time.hpp"
#include "utils/Console.hpp"

namespace BHW
{
    class ContinuousThread : public Thread
    {
    private:
        float    m_deltaTime     ;
        uint64_t m_lastTime_ns   ;
        uint64_t m_currentTime_ns;
        uint64_t m_deltaTime_ns  ;
    
    public:
        ContinuousThread();

        inline float GetDeltaTime() const { return m_deltaTime; }

    protected:
        virtual void Initialize();
    
        virtual void Release   ();

        virtual void Run       ();

        virtual bool IsActive  ();

        inline uint64_t DeltaTime() const { return m_deltaTime_ns; }

    private:
        void RunThread();
    };
}