#pragma once

#include <TC/gapi/impl/GLFW_OpenGL_DearImGui.hpp>
#include <Tea/Tea.hpp>

#include <string>

#include "TeaPot/application/ecs/components/DebugComponent.hpp"
#include "TeaPot/application/ecs/systems/DebugSystem.hpp"

#include "TeaPot/application/gui/view/Build.hpp"
#include "TeaPot/application/gui/view/Console.hpp"
#include "TeaPot/application/gui/view/Explorer.hpp"
#include "TeaPot/application/gui/view/Inspector.hpp"
#include "TeaPot/application/gui/view/ProjectLoader.hpp"
#include "TeaPot/application/gui/view/ProjectSettings.hpp"
#include "TeaPot/application/gui/view/SceneEditor.hpp"
#include "TeaPot/application/gui/view/SceneView.hpp"
#include "TeaPot/application/gui/view/Settings.hpp"
#include "TeaPot/application/gui/view/Performance.hpp"

#include "TeaPot/application/gui/Dockspace.hpp"
#include "TeaPot/application/gui/MenuBar.hpp"

#include "TeaPot/project/Project.hpp"

namespace TP
{
    namespace View
    {
        class PerformanceRenderer;
        class Performance;
    }
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

        void ReloadProject();

    public:
        BHW::Random m_random;

        Project m_project;
    };
}