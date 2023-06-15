#pragma once

#include "graphics/Window.hpp"

#include <iostream>

namespace DigitalTea
{
    enum class ApplicationState
    {
        Uninitialized,
        
        Initializing,

        Initialized,

        Starting,

        Running,

        Terminating,

        Terminated
    };

    class Application
    {
    private:
        ApplicationState m_state;
        Window* m_window;

        static Application* s_instance;
    public:
        Application();
        virtual ~Application();

        void Run();

        Window* GetWindow() const { return m_window; }
        ApplicationState GetState() const { return m_state; }

        static Application* GetInstance() { return s_instance; }

    protected:
        virtual int  Initialize();

        virtual void Update();
        
        virtual void StartFrame();

        virtual void Render();

        virtual void EndFrame();

        virtual void Terminate();
    };
}