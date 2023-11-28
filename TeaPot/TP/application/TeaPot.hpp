#pragma once

#include <TC/gapi/impl/GLFW_OpenGL_DearImGui.hpp>
#include <Tea/Tea.hpp>

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
    class TeaPot : public Tea::Tea
    <
        TC::GLFW_OpenGL_DearImGui, 
        EventSubscriber, 
        Tea::ECSSystem
        <
            EventSubscriber,
        
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
        >,
        TeaPot,

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
    > 
    { 
    public:
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