#include "TP/application/windows/Dockspace.hpp"

#include <BHW/utils/console/Console.hpp>

#include "TP/application/TeaPot.hpp"

namespace TP
{
    void DockspaceRenderer::Render(TeaPot& teaPot)
    {
        teaPot.ForEachComponent<DockspaceData>([&](BHW::EntityUUID uuid, DockspaceData& data)
        {
            RenderDockspace(teaPot, data);
        });
    }

    void DockspaceRenderer::RenderDockspace(TeaPot& teaPot)
    {
        static ImGuiDockNodeFlags dockspace_flags = ImGuiDockNodeFlags_PassthruCentralNode;

        ImGuiWindowFlags window_flags = ImGuiWindowFlags_MenuBar | ImGuiWindowFlags_NoDocking;
        ImGuiViewport*   viewport     = ImGui::GetMainViewport();

        ImGui::SetNextWindowPos     (viewport->Pos );
        ImGui::SetNextWindowSize    (viewport->Size);
        ImGui::SetNextWindowViewport(viewport->ID  );

        ImGui::PushStyleVar(ImGuiStyleVar_WindowRounding  , 0.0f);
        ImGui::PushStyleVar(ImGuiStyleVar_WindowBorderSize, 0.0f);

        window_flags |= ImGuiWindowFlags_NoTitleBar            | ImGuiWindowFlags_NoCollapse | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoMove;
        window_flags |= ImGuiWindowFlags_NoBringToFrontOnFocus | ImGuiWindowFlags_NoNavFocus;

        if (dockspace_flags & ImGuiDockNodeFlags_PassthruCentralNode) window_flags |= ImGuiWindowFlags_NoBackground;

        ImGui::PushStyleVar(ImGuiStyleVar_WindowPadding, ImVec2(0.0f, 0.0f));
        
        ImGui::Begin(data.m_name.c_str(), nullptr, window_flags);

        ImGui::PopStyleVar();
        ImGui::PopStyleVar(2);

        if (teaPot.GetIO()->ConfigFlags & ImGuiConfigFlags_DockingEnable)
        {
            ImGuiID dockspaceID = ImGui::GetID("MyDockspace");

            ImGui::DockSpace(dockspaceID, ImVec2(0.0f, 0.0f), dockspace_flags);

            static auto first_time = true;
            if (first_time)
            {
                first_time = false;

                ImGui::DockBuilderRemoveNode (dockspaceID); // clear any previous layout
                ImGui::DockBuilderAddNode    (dockspaceID, dockspace_flags | ImGuiDockNodeFlags_DockSpace);
                ImGui::DockBuilderSetNodeSize(dockspaceID, viewport->Size);

                SetupDockspace(teaPot, data, dockspaceID);

                ImGui::DockBuilderFinish(dockspaceID);
            }
        }

        ImGui::End();
    }

    void DockspaceRenderer::SetupDockspace(TeaPot& teaPot, unsigned int& dockspaceID)
    {
        auto dock_id_left  = ImGui::DockBuilderSplitNode(dockspaceID, ImGuiDir_Left , 0.25f, nullptr, &dockspaceID);
        auto dock_id_right = ImGui::DockBuilderSplitNode(dockspaceID, ImGuiDir_Right, 0.3f , nullptr, &dockspaceID);
        auto dock_id_down  = ImGui::DockBuilderSplitNode(dockspaceID, ImGuiDir_Down , 0.35f, nullptr, &dockspaceID);

        auto console1 = teaPot.GetEntity(teaPot.CreateEntity());
        auto console2 = teaPot.GetEntity(teaPot.CreateEntity());
        auto console3 = teaPot.GetEntity(teaPot.CreateEntity());
        auto console4 = teaPot.GetEntity(teaPot.CreateEntity());

        auto consoleData1 = console1.AddComponent<ConsoleWindowData>("Console###1");
        auto consoleData2 = console2.AddComponent<ConsoleWindowData>("Console###2");
        auto consoleData3 = console3.AddComponent<ConsoleWindowData>("Console###3");
        auto consoleData4 = console4.AddComponent<ConsoleWindowData>("Console###4");

        ImGui::DockBuilderDockWindow(consoleData1.m_name.c_str(), dock_id_left );
        ImGui::DockBuilderDockWindow(consoleData2.m_name.c_str(), dock_id_right);
        ImGui::DockBuilderDockWindow(consoleData3.m_name.c_str(), dock_id_down);

        ImGui::DockBuilderDockWindow(consoleData4.m_name.c_str(), dockspaceID);
    }
}