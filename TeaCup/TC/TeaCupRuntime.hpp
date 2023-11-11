#pragma once

#include <mutex>
#include <condition_variable>

#include <BHW/utils/threads/Thread.hpp>
#include <BHW/utils/Time.hpp>

#include <BHW/utils/console/Console.hpp>

namespace TC
{
    class ApplicationRuntime
    {
    public:
        ApplicationRuntime();

    public:
        void StartRuntime();
        void EndRuntime  ();

        inline float GetUPS () { return m_measuredUpdatesPerSecond      + 0.5; }
        inline float GetFUPS() { return m_measuredFixedUpdatesPerSecond + 0.5; }
        inline float GetFPS () { return m_measuredFramesPerSecond       + 0.5; }
        
        inline float GetDeltaTime     () { return m_deltaUpdateTime     ; }
        inline float GetFixedDeltaTime() { return m_deltaFixedUpdateTime; }
        inline float GetFrameDeltaTime() { return m_deltaFrameTime      ; }

        inline bool IsRunning() { return m_isRunning && RuntimeIsRunning(); }

    protected:
        virtual void RuntimeUpdate     () = 0;
        virtual void RuntimeFixedUpdate() = 0;
        virtual void RuntimeRender     () = 0;

        virtual void RuntimeMainThreadStart       () = 0;
        virtual void RuntimeFixedUpdateThreadStart() = 0;
        virtual void RuntimeRenderThreadStart     () = 0;

        virtual void RuntimeMainThreadEnd       () = 0;
        virtual void RuntimeFixedUpdateThreadEnd() = 0;
        virtual void RuntimeRenderThreadEnd     () = 0;

        virtual bool RuntimeIsRunning() = 0;

    private:
        void MainThread       ();
        void FixedUpdateThread();
        void RenderThread     ();

    private:
        float m_deltaUpdateTime     ;
        float m_deltaFixedUpdateTime;
        float m_deltaFrameTime      ;

        uint64_t m_fps ;
        uint64_t m_fups;

        float m_measuredFramesPerSecond      ;
        float m_measuredUpdatesPerSecond     ;
        float m_measuredFixedUpdatesPerSecond;

        BHW::Thread m_fixedUpdateThread;
        BHW::Thread m_renderThread     ;

        bool m_isRunning;
    };
}