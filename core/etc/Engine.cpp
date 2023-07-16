#include "Engine.hpp"

namespace DigitalTea
{
    Engine::Engine() : m_window()
    {
    }

    Engine::~Engine()
    {
        m_state = EngineState::Terminating;

        // delete m_scene
        
        m_window.Shutdown();
        delete &m_window;

        m_state = EngineState::Uninitialized;
    }

    void Engine::Initialize()
    {
        m_state = EngineState::Initializing;

        m_window.Init();

        m_window.Center();

        m_state = EngineState::Initialized;
    }

    void Engine::Run()
    {
        m_state = EngineState::Running;

        while (m_state == EngineState::Running)
        {
            Update();
            Render();
        
            if (m_window.ShouldClose())
            {
                m_state = EngineState::Terminating;
            }
        }

        m_state = EngineState::Initialized;
    }

    void Engine::Terminate()
    {
        delete this;
    }

    void Engine::Update()
    {
        m_window.Update();
    }

    void Engine::Render()
    {
        m_window.StartFrame();

        // render scene

        m_window.EndFrame();
    }
}