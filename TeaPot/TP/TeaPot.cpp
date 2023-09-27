#include "TP/TeaPot.hpp"

#include <bitset>

#include "imgui_internal.h"

struct ApplicationSystem : public TP::TeaPotBaseEvent
{
    using TP::TeaPotBaseEvent::TeaPotBaseEvent;

    inline void Start() override
    {
        //entityId = m_editor->Create();

        BHW::Console::WriteLine("Start!");
    }


    inline void Update() override
    {
        m_editor->SetTitle("TeaPot | [FPS: " + std::to_string(m_editor->GetFPS()) + "] [TPS: " + std::to_string(m_editor->GetTPS()) + "] [FTPS: " + std::to_string(m_editor->GetFTPS()) + "]");
    }

    inline void FixedUpdate() override
    {
       // BHW::Console::WriteLine("FixedUpdate!");
    }

    inline void Render() override
    {
        static ImGuiDockNodeFlags dockspace_flags = ImGuiDockNodeFlags_PassthruCentralNode;

        // We are using the ImGuiWindowFlags_NoDocking flag to make the parent window not dockable into,
        // because it would be confusing to have two docking targets within each others.
        ImGuiWindowFlags window_flags = ImGuiWindowFlags_MenuBar | ImGuiWindowFlags_NoDocking;

        ImGuiViewport* viewport = ImGui::GetMainViewport();
        ImGui::SetNextWindowPos(viewport->Pos);
        ImGui::SetNextWindowSize(viewport->Size);
        ImGui::SetNextWindowViewport(viewport->ID);
        ImGui::PushStyleVar(ImGuiStyleVar_WindowRounding, 0.0f);
        ImGui::PushStyleVar(ImGuiStyleVar_WindowBorderSize, 0.0f);
        window_flags |= ImGuiWindowFlags_NoTitleBar | ImGuiWindowFlags_NoCollapse | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoMove;
        window_flags |= ImGuiWindowFlags_NoBringToFrontOnFocus | ImGuiWindowFlags_NoNavFocus;


        // When using ImGuiDockNodeFlags_PassthruCentralNode, DockSpace() will render our background and handle the pass-thru hole, so we ask Begin() to not render a background.
        if (dockspace_flags & ImGuiDockNodeFlags_PassthruCentralNode)
        window_flags |= ImGuiWindowFlags_NoBackground;

        ImGui::PushStyleVar(ImGuiStyleVar_WindowPadding, ImVec2(0.0f, 0.0f));
        ImGui::Begin("DockSpace", nullptr, window_flags);
        ImGui::PopStyleVar();
        ImGui::PopStyleVar(2);

        // DockSpace
        ImGuiIO& io = ImGui::GetIO();
        if (io.ConfigFlags & ImGuiConfigFlags_DockingEnable)
        {
            ImGuiID dockspace_id = ImGui::GetID("MyDockSpace");
            ImGui::DockSpace(dockspace_id, ImVec2(0.0f, 0.0f), dockspace_flags);

            static auto first_time = true;
            if (first_time)
            {
                first_time = false;

                ImGui::DockBuilderRemoveNode(dockspace_id); // clear any previous layout
                ImGui::DockBuilderAddNode(dockspace_id, dockspace_flags | ImGuiDockNodeFlags_DockSpace);
                ImGui::DockBuilderSetNodeSize(dockspace_id, viewport->Size);

                // split the dockspace into 2 nodes -- DockBuilderSplitNode takes in the following args in the following order
                //   window ID to split, direction, fraction (between 0 and 1), the final two setting let's us choose which id we want (which ever one we DON'T set as NULL, will be returned by the function)
                //                                                              out_id_at_dir is the id of the node in the direction we specified earlier, out_id_at_opposite_dir is in the opposite direction
                auto dock_id_left = ImGui::DockBuilderSplitNode(dockspace_id, ImGuiDir_Left, 0.25f, nullptr, &dockspace_id);
                auto dock_id_right = ImGui::DockBuilderSplitNode(dockspace_id, ImGuiDir_Right, 0.3f, nullptr, &dockspace_id);
                auto dock_id_down = ImGui::DockBuilderSplitNode(dockspace_id, ImGuiDir_Down, 0.35f, nullptr, &dockspace_id);

                // we now dock our windows into the docking node we made above
                ImGui::DockBuilderDockWindow("Hierarchy", dock_id_left);
                ImGui::DockBuilderDockWindow("Inspector", dock_id_right);
                ImGui::DockBuilderDockWindow("Console", dock_id_down);

                ImGui::DockBuilderDockWindow("Scene View", dockspace_id);

                ImGui::DockBuilderFinish(dockspace_id);
            }
        }

        ImGui::End();

        // Render Menu Bar, and enable windows through it
        static bool show_console_window = true;
        static bool show_hierarchy_window = true;
        static bool show_inspector_window = true;
        static bool show_scene_view_window = true;

        if (ImGui::BeginMainMenuBar())
        {
            if (ImGui::BeginMenu("Windows"))
            {
                ImGui::MenuItem("Console", NULL, &show_console_window);
                ImGui::MenuItem("Hierarchy", NULL, &show_hierarchy_window);
                ImGui::MenuItem("Inspector", NULL, &show_inspector_window);
                ImGui::MenuItem("Scene View", NULL, &show_scene_view_window);
                ImGui::EndMenu();
            }
            ImGui::EndMainMenuBar();
        }

        // Render Windows
        if (show_console_window)
        {
            ImGui::Begin("Console");
            ImGui::Text("Hello, down!");
            ImGui::End();
        }

        if (show_hierarchy_window)
        {
            ImGui::Begin("Hierarchy");
            ImGui::Text("Hello, left!");
            ImGui::End();
        }

        if (show_inspector_window)
        {
            ImGui::Begin("Inspector");
            ImGui::Text("Hello, right!");
            ImGui::End();
        }

        if (show_scene_view_window)
        {
            ImGui::Begin("Scene View");
            ImGui::Text((std::to_string(m_editor->GetInput()->MousePosition().x)+ ", " + std::to_string(m_editor->GetInput()->MousePosition().y)).c_str());
            ImGui::End();
        }

        ImGui::Begin("Hello, world!");

        ImGui::Text("FPS: %f", m_editor->GetFPS());
        ImGui::Text("TPS: %f", m_editor->GetTPS());
        ImGui::Text("FTPS: %f", m_editor->GetFTPS());

        ImGui::Text(("FrameTime: " + BHW::to_string(m_editor->FrameDeltaTime())).c_str());
        ImGui::Text(("FixedTickTime: " + BHW::to_string(m_editor->FixedTickDelta())).c_str());
        ImGui::Text(("TickTime: " + BHW::to_string(m_editor->TickDeltaTime())).c_str());

        ImGui::End();
    }

    inline void End() override
    {
        BHW::Console::WriteLine("End!");
    }
};

/*

typedef BHW::ECS
<
    BHW::EventSystem
    <
        BaseEventSystem,
        TestEventSystem
    >,
    TestComponent_1, 
    TestComponent_2, 
    TestComponent_3
> TP_ECS;

struct BaseEventSystem : public TP_ECS::TEventSystem
{
    virtual void Update() { }
};

struct TestEventSystem : public BaseEventSystem
{
    inline void Update() override
    {
        BHW::Console::WriteLine("TestEventSystem::Update()");
    }
};

struct TestComponent_1 { uint32_t m_test; };
struct TestComponent_2 { uint32_t m_test; };
struct TestComponent_3 { uint32_t m_test; };

*/
int BHW::EntryPoint(std::vector<std::string> args)
{
    TP::Editor editor;

    BHW::Console::InitConsole();

    /*
    TP_ECS ecs;

    EntityUUID entityUUID = ecs.AddEntity();

    ecs.AddComponent<TestComponent_1>(entityUUID);

    ecs.ForEachEntity([&ecs](EntityUUID someEntity)
    {
        TestComponent_1* testComponent = ecs.GetComponent<TestComponent_1>(someEntity);

        testComponent->m_test = 5;
    });

    ecs.ForEachEntity([&ecs](EntityUUID someEntity)
    {
        TestComponent_1* testComponent = ecs.GetComponent<TestComponent_1>(someEntity);

        Console::WriteLine(std::to_string(testComponent->m_test));
    });

    */

    editor.SubscribeEventSystem<ApplicationSystem>(&editor);

    editor.Run();

    return 0; 
}