#pragma once

#include <BHW/utils/console/Console.hpp>
#include <BHW/ecs/ECS.hpp>
#include <BHW/utils/event/EventSystem.hpp>

#include <TC/TeaCup.hpp>
#include <TC/gapi/impl/GLFW_OpenGL_DearImGui.hpp>

#include "TP/application/EventSubscriber.hpp"

#include "TP/application/ecs/components/DebugComponent.hpp"
#include "TP/application/ecs/systems/DebugSystem.hpp"

#include "TP/application/windows/ConsoleWindow.hpp"
#include "TP/application/windows/Dockspace.hpp"

namespace TP
{
    typedef TC::TeaCup<TC::GLFW_OpenGL_DearImGui<
        Dockspace,
        ConsoleWindow
    >, EventSubscriber> TeaCup;

    typedef BHW::EventSystem<EventSubscriber, 
        DebugSystem
    > EventSystem;

    typedef BHW::ECS<EventSystem,
        DebugComponent
    > ECS;

    class TeaPot 
        : public std::enable_shared_from_this<TeaPot>,
          public TeaCup,
          public ECS
    {
    public:
        inline TeaPot() : TeaCup(), ECS()
        {
            AddImGuiWindow<TP::Dockspace>(*this);
        }

        template <typename ...TArgs>
        inline void ForEachEventSystem(void (EventSubscriber::*function)(TArgs...), TArgs... args)
        {
            BHW::Console::WriteLine("TeaPot::ForEachEventSystem");
            TC::TeaCup::ForEachEventSystem(function, args...);
            ECS::ForEachEventSystem(function, args...);
        }
    };
}