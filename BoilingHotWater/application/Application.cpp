#include "Application.hpp"

namespace BHW
{
    Application* Application::s_instance = nullptr;

    Application::Application() : ApplicationThread(60, 60), m_window(nullptr)
    {
        if (s_instance)
        {
            delete s_instance;
        }

        s_instance = this;
    }

    Application::~Application()
    {
        if (m_window)  
        {
            delete m_window;
        }

        s_instance = nullptr;
    }

    void Application::Initialize()
    {
        m_window = new Window();

        m_window->Initialize();

        Start();
    }

    void Application::Release()
    {
        End();
        
        m_window->Terminate();
    }

    void Application::Tick()
    {
        Update();
    }

    void Application::Frame()
    {
        m_window->StartFrame();

        Render();

        m_window->EndFrame();
    }

    bool Application::IsActive()
    {
        return !m_window->ShouldClose() && ApplicationThread::IsActive();
    }

    void Application::Update() { }
    void Application::Render() { }
}