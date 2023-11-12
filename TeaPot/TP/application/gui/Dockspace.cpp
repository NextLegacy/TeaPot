#include "TP/application/gui/Dockspace.hpp"

#include <BHW/utils/console/Console.hpp>

#include "TP/application/TeaPot.hpp"

namespace TP
{
    void DockspaceRenderer::Render(TeaPot& teaPot)
    {
        RenderDockspace(teaPot);
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
        
        ImGui::Begin("Dockspace", nullptr, window_flags);

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

                SetupDockspace(teaPot, dockspaceID);

                ImGui::DockBuilderFinish(dockspaceID);
            }
        }

        ImGui::End();
    }

    void DockspaceRenderer::SetupDockspace(TeaPot& teaPot, unsigned int& dockspaceID)
    {
        //auto dock_id_left  = ImGui::DockBuilderSplitNode(dockspaceID, ImGuiDir_Left , 0.25f, nullptr, &dockspaceID);
        //auto dock_id_right = ImGui::DockBuilderSplitNode(dockspaceID, ImGuiDir_Right, 0.3f , nullptr, &dockspaceID);
        //auto dock_id_down  = ImGui::DockBuilderSplitNode(dockspaceID, ImGuiDir_Down , 0.35f, nullptr, &dockspaceID);

        auto& console     = teaPot.CreateView<View::Console    >("Console"    );
        auto& explorer    = teaPot.CreateView<View::Explorer   >("Explorer"   );
        auto& inspector   = teaPot.CreateView<View::Inspector  >("Inspector"  );
        auto& sceneEditor = teaPot.CreateView<View::SceneEditor>("SceneEditor");
        auto& sceneView   = teaPot.CreateView<View::SceneView  >("SceneView"  );

        auto dock_id_left  = ImGui::DockBuilderSplitNode(dockspaceID, ImGuiDir_Left , 0.25f, nullptr, &dockspaceID);
        auto dock_id_right = ImGui::DockBuilderSplitNode(dockspaceID, ImGuiDir_Right, 0.3f , nullptr, &dockspaceID);
        auto dock_id_down  = ImGui::DockBuilderSplitNode(dockspaceID, ImGuiDir_Down , 0.35f, nullptr, &dockspaceID);

        ImGui::DockBuilderDockWindow(explorer   .m_name.c_str(), dock_id_down );
        ImGui::DockBuilderDockWindow(console    .m_name.c_str(), dock_id_down );
        ImGui::DockBuilderDockWindow(inspector  .m_name.c_str(), dock_id_right);
        ImGui::DockBuilderDockWindow(sceneEditor.m_name.c_str(), dock_id_left );

        ImGui::DockBuilderDockWindow(sceneView  .m_name.c_str(), dockspaceID);
    }
}