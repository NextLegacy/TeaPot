#include "BHW/application/Application.hpp"

namespace BHW
{
    Application* Application::s_instance = nullptr;

    Application::Application() : m_isRunning(false)
    {
        if (s_instance)
        {
            delete s_instance;
        }

        s_instance = this;
    }

    Application::~Application()
    {
        s_instance = nullptr;
    }

    void Application::Run()
    {   
        m_isRunning = true;

        ApplicationEntryPoint();

        m_isRunning = false;
    }
}