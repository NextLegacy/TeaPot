#include "ApplicationThread.hpp"

namespace BHW
{
    //   m_TPS_defined (tps                   ), m_FPS_defined (fps                   ),
    //   m_SPT_defined (1.0e9f / m_TPS_defined), m_SPF_defined (1.0e9f / m_FPS_defined),
    
    ApplicationThread::ApplicationThread(uint32_t tps, uint32_t fps)
        : ContinuousThread(),
          m_TPS_counter (0   ), m_FPS_counter (0   ),
          m_TPS_measured(0   ), m_FPS_measured(0   ),
          m_T_delta     (0.0f), m_F_delta     (0.0f)
    {
        SetTPS(tps); 
        SetFPS(fps);
    }

    void ApplicationThread::SetTPS(uint32_t tps)
    {
        m_TPS_defined  = tps;
        m_SPT_defined  = 1.0e9f / m_TPS_defined;
    }

    void ApplicationThread::SetFPS(uint32_t fps)
    {
        m_FPS_defined  = fps;
        m_SPF_defined  = 1.0e9f / m_FPS_defined;
    }

    void ApplicationThread::Run()
    {

        while (m_T_delta >= 1.0f) // update with respect to requested ticks per second
        {
            m_last_tick = Time::Now();

            Tick();

            m_TPS_counter += (m_last_tick - (m_last_tick = Time::Now()));

            m_T_delta--;
        }


        if (m_F_delta >= 1.0f) // render with respect to requested frames per second
        {
            m_last_frame = Time::Now();
            
            Frame();

            m_FPS_counter = ((Time::Now()) - m_last_frame) * 60;
            m_last_frame   = Time::Now();

            m_FPS_counter += m_FPS_counter;

            m_F_delta--;
        }

        m_T_delta += DeltaTime() / m_SPT_defined;
        m_F_delta += DeltaTime() / m_SPF_defined;
    }

    void ApplicationThread::Start    () { }
    void ApplicationThread::Tick     () { }
    void ApplicationThread::FixedTick() { }
    void ApplicationThread::LateTick () { }
    void ApplicationThread::Frame    () { }
    void ApplicationThread::End      () { }
}