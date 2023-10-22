#pragma once

#include <BHW/utils/console/Console.hpp>
#include <BHW/ecs/ECS.hpp>
#include <BHW/utils/event/EventSystem.hpp>
#include <BHW/utils/random/Random.hpp>

#include <TC/TeaCup.hpp>
#include <TC/gapi/impl/GLFW_OpenGL_DearImGui.hpp>

#include "TP/application/EventSubscriber.hpp"

#include "TP/application/ecs/components/DebugComponent.hpp"
#include "TP/application/ecs/systems/DebugSystem.hpp"

#include "TP/application/gui/window/ConsoleWindow.hpp"
#include "TP/application/gui/Dockspace.hpp"
#include "TP/application/gui/MenuBar.hpp"

namespace TP
{
    class TeaPot;

    typedef TC::TeaCup<TC::GLFW_OpenGL_DearImGui, EventSubscriber, TeaPot> TeaCup;

    typedef BHW::EventSystem<EventSubscriber, 
        DebugSystem,
        DockspaceRenderer,
        ConsoleWindowRenderer,
        MenuBarRenderer
    > EventSystem;

    typedef BHW::ECS<EventSystem,
        DebugComponent,
        DockspaceData,
        ConsoleWindowData
    > ECS;

    class ECSEventSystemSubscriber : public EventSubscriber
    {
        virtual void Start      (TeaPot& teaPot) override;
        virtual void Update     (TeaPot& teaPot) override;
        virtual void FixedUpdate(TeaPot& teaPot) override;
        virtual void Render     (TeaPot& teaPot) override;
        virtual void End        (TeaPot& teaPot) override;
    };

    class TeaPot : public TeaCup, public ECS
    {
    public:
        inline TeaPot() : TeaCup(), ECS(), m_random()
        {
            SubscribeEventSystem<ECSEventSystemSubscriber>();

            GetEntity(CreateEntity()).AddComponent<DockspaceData>("Dockspace");
        }

        template <typename TWindow, typename... TArgs>
        inline BHW::Entity CreateWindow(TArgs&&... args)
        {
            auto entity = GetEntity(CreateEntity());
            entity.AddComponent<TWindow>(std::forward<TArgs>(args)...);
            return entity;
        }

    private:
        friend class ECSEventSystemSubscriber;

        BHW::Random m_random;
    };
}