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

#include "TP/application/gui/view/Build.hpp"
#include "TP/application/gui/view/Console.hpp"
#include "TP/application/gui/view/Explorer.hpp"
#include "TP/application/gui/view/Inspector.hpp"
#include "TP/application/gui/view/ProjectLoader.hpp"
#include "TP/application/gui/view/ProjectSettings.hpp"
#include "TP/application/gui/view/SceneEditor.hpp"
#include "TP/application/gui/view/SceneView.hpp"
#include "TP/application/gui/view/Settings.hpp"
#include "TP/application/gui/view/Performance.hpp"

#include "TP/application/gui/Dockspace.hpp"
#include "TP/application/gui/MenuBar.hpp"

#include "TP/project/TeaPotProject.hpp"

namespace TP
{
    class TeaPot;

    typedef TC::TeaCup<TC::GLFW_OpenGL_DearImGui, EventSubscriber, TeaPot> TeaCup;

    typedef BHW::EventSystem<EventSubscriber, 
        DebugSystem,

        DockspaceRenderer,

        View::BuildRenderer,
        View::ConsoleRenderer,
        View::ExplorerRenderer,
        View::InspectorRenderer,
        View::ProjectLoaderRenderer,
        View::ProjectSettingsRenderer,
        View::SceneEditorRenderer,
        View::SceneViewRenderer,
        View::SettingsRenderer,
        View::PerformanceRenderer,

        MenuBarRenderer
    > EventSystem;

    typedef BHW::ECS<EventSystem,
        DebugComponent,

        View::Build,
        View::Console,
        View::Explorer,
        View::Inspector,
        View::ProjectLoader,
        View::ProjectSettings,
        View::SceneEditor,
        View::SceneView,
        View::Settings,
        View::Performance
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
        }

        template <typename TWindow, typename... TArgs>
        inline TWindow& CreateView(std::string name, TArgs&&... args)
        {
            auto entity = GetEntity(CreateEntity());
            auto& window = entity.AddComponent<TWindow>(name + "####" + m_random.UUID().ToString(), std::forward<TArgs>(args)...);
            return window;
        }

    public:
        BHW::Random m_random;

        TeaPotProject m_project;
    };
}