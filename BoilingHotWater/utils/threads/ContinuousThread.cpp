#include "ContinuousThread.hpp"

namespace BHW
{
    ContinuousThread::ContinuousThread() : 
        Thread(std::bind(&ContinuousThread::RunThread, this)),
        m_currentTime_ns(0), m_lastTime_ns(0)
    {
    }

    void ContinuousThread::RunThread()
    {
        Initialize();
     
        while (IsActive() && !ShouldStop())
        {
            m_deltaTime_ns = m_currentTime_ns - m_lastTime_ns;

            m_lastTime_ns = Time::Now();

            Run();
       
            m_currentTime_ns = Time::Now();
        }

        Release();
    }

    void ContinuousThread::Initialize() { }
    void ContinuousThread::Run       () { }
    void ContinuousThread::Release   () { }

    bool ContinuousThread::IsActive  () { return true; }
}