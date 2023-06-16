#pragma once

#include "graphics/imgui/ImGuiWindow.hpp"

namespace DigitalTea
{
    enum class EngineState
    {
        Uninitialized,
        
        Initializing,

        Initialized,

        Running,

        Terminating
    };

    class Engine
    {
    private:
        EngineState m_state = EngineState::Uninitialized;
        ImGuiWindow m_window;
    public:
        Engine();
        ~Engine();

        void Initialize();

        void Run();

        void Terminate();
    
        inline EngineState GetState() const { return m_state; }
        inline Window& GetWindow() { return m_window; }
    private:
        void Update();
        void Render();
    };
}