#include <Tea/Tea.hpp>

class Application : public Tea::Tea
<        
    TC::GLFW_OpenGL_DearImGui, 
    EventSPubscriber, 
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
}