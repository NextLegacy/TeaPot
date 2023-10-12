#include "TP/application/ecs/systems/DebugSystem.hpp"

#include "TP/application/TeaPot.hpp"

namespace TP
{
    void DebugSystem::Update()
    {
        BHW::Console::WriteLine("FPS: " + std::to_string(m_teaPot->GetFPS()));
    }
}