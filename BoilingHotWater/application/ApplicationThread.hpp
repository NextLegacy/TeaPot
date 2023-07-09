#pragma once

#include "utils/threads/ContinuousThread.hpp"

namespace BHW
{
    class ApplicationThread : public ContinuousThread
    {
    private:
        uint32_t m_TPS_defined , m_FPS_defined ;

        float    m_SPT_defined , m_SPF_defined ;

        float    m_TPS_counter , m_FPS_counter ;

        uint64_t m_last_tick   , m_last_frame  ;

        float    m_TPS_measured, m_FPS_measured;

        float    m_T_delta     , m_F_delta     ;

    public:
        ApplicationThread(uint32_t tps, uint32_t fps);

        void SetTPS(uint32_t tps);
        void SetFPS(uint32_t fps);

        inline float TPS() const { return m_TPS_measured; }
        inline float FPS() const { return m_FPS_measured; }

    protected:
        virtual void Start    ();
        
        virtual void Tick     ();
        
        virtual void FixedTick();
        virtual void LateTick ();

        virtual void Frame    ();

        virtual void End      ();

    private:
        void Run() override;
    };
}