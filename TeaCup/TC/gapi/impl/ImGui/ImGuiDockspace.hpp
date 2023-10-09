#pragma once

#include "TC/gapi/impl/ImGui/ImGuiWindow.hpp"

#include <imgui_internal.h>

namespace TC
{
    class ImGuiDockspace : public ImGuiWindow
    {
    public:
        inline ImGuiDockspace(const std::string& name) : ImGuiWindow(name)
        {
        }

        inline virtual void InitializeDockspace() 
        {

        }  

        inline virtual void RenderImpl()
        {
            static ImGuiDockNodeFlags dockspace_flags = ImGuiDockNodeFlags_PassthruCentralNode;

            ImGuiWindowFlags window_flags = ImGuiWindowFlags_MenuBar | ImGuiWindowFlags_NoDocking;

            ImGuiViewport* viewport = ImGui::GetMainViewport();
            ImGui::SetNextWindowPos(viewport->Pos);
            ImGui::SetNextWindowSize(viewport->Size);
            ImGui::SetNextWindowViewport(viewport->ID);
            ImGui::PushStyleVar(ImGuiStyleVar_WindowRounding, 0.0f);
            ImGui::PushStyleVar(ImGuiStyleVar_WindowBorderSize, 0.0f);
            window_flags |= ImGuiWindowFlags_NoTitleBar | ImGuiWindowFlags_NoCollapse | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoMove;
            window_flags |= ImGuiWindowFlags_NoBringToFrontOnFocus | ImGuiWindowFlags_NoNavFocus;


            if (dockspace_flags & ImGuiDockNodeFlags_PassthruCentralNode)
            window_flags |= ImGuiWindowFlags_NoBackground;

            ImGui::PushStyleVar(ImGuiStyleVar_WindowPadding, ImVec2(0.0f, 0.0f));
            
            ImGui::Begin("DockSpace Demo", nullptr, window_flags);

            ImGui::PopStyleVar();
            ImGui::PopStyleVar(2);

            // DockSpace
            ImGuiIO& io = ImGui::GetIO();
            if (io.ConfigFlags & ImGuiConfigFlags_DockingEnable)
            {
                m_dockspaceID = ImGui::GetID("MyDockSpace");
                ImGui::DockSpace(m_dockspaceID, ImVec2(0.0f, 0.0f), dockspace_flags);

                static auto first_time = true;
                if (first_time)
                {
                    first_time = false;

                    ImGui::DockBuilderRemoveNode(m_dockspaceID); // clear any previous layout
                    ImGui::DockBuilderAddNode(m_dockspaceID, dockspace_flags | ImGuiDockNodeFlags_DockSpace);
                    ImGui::DockBuilderSetNodeSize(m_dockspaceID, viewport->Size);

                    DockSpaceImpl();

                    ImGui::DockBuilderFinish(m_dockspaceID);
                }
            }

            ImGui::End();
        }

    protected:
        virtual void DockSpaceImpl() = 0;

    protected:
        ImGuiID m_dockspaceID;
    };
}