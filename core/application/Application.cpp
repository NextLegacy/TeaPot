#include "Application.hpp"

namespace DigitalTea
{
    Application* Application::s_instance = nullptr;

    Application::Application() : m_state(ApplicationState::Uninitialized), m_window(nullptr), m_running(false)
    {
        if (s_instance)
        {
            std::cout << "Application already exists!" << std::endl;
            
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

    int Application::Init()
    {
        m_state = ApplicationState::Initializing;

        m_window = new Window();

        if (m_window->Init() != 0)
        {
            return 1;
        }

        m_state = ApplicationState::Initialized;

        return 0;
    }

    void Application::Update()
    {
    }

    void Application::StartFrame()
    {
        m_window->StartFrame();
    }

    void Application::Render()
    {
    }

    void Application::EndFrame()
    {
        m_window->EndFrame();
    }

    void Application::Terminate()
    {
        m_state = ApplicationState::Terminating;

        m_running = false;

        m_state = ApplicationState::Terminated;
    }
}