#include "TP/application/gui/MenuBar.hpp"

#include "TP/application/TeaPot.hpp"

namespace TP
{
    void MenuBarRenderer::Render(TeaPot& teaPot)
    {
        RenderMenuBar(teaPot);
    }

    void MenuBarRenderer::RenderMenuBar(TeaPot& teaPot)
    {
        static int counter = 0;
        
        if (ImGui::BeginMainMenuBar())
        {
            if (ImGui::BeginMenu("Project"))
            {
                if (ImGui::MenuItem("Load")) teaPot.CreateView<View::ProjectLoader>(std::string("Project Loader"));

                if (ImGui::MenuItem("Save"  ));//teaPot.m_project.Save  ();
                if (ImGui::MenuItem("Reload"));//teaPot.m_project.Reload();

                ImGui::Separator();

                if (ImGui::MenuItem("Exit")) teaPot.Stop();

                ImGui::EndMenu();
            }

            if (ImGui::BeginMenu("Build & Run"))
            {
                if (ImGui::MenuItem("Build")) teaPot.CreateView<View::Build>(std::string("Build"));

                ImGui::Separator();

                if (ImGui::MenuItem("Run"  ));
                if (ImGui::MenuItem("Debug"));

                ImGui::EndMenu();
            }

            if (ImGui::BeginMenu("View"))
            {
                if (ImGui::MenuItem("Console"         )) teaPot.CreateView<View::Console        >(std::string("Console"         ));
                if (ImGui::MenuItem("Explorer"        )) teaPot.CreateView<View::Explorer       >(std::string("Explorer"        ));
                if (ImGui::MenuItem("Inspector"       )) teaPot.CreateView<View::Inspector      >(std::string("Inspector"       ));
                if (ImGui::MenuItem("Project Loader"  )) teaPot.CreateView<View::ProjectLoader  >(std::string("Project Loader"  ));
                if (ImGui::MenuItem("Project Settings")) teaPot.CreateView<View::ProjectSettings>(std::string("Project Settings"));
                if (ImGui::MenuItem("Scene Editor"    )) teaPot.CreateView<View::SceneEditor    >(std::string("Scene Editor"    ));
                if (ImGui::MenuItem("Scene View"      )) teaPot.CreateView<View::SceneView      >(std::string("Scene View"      ));
                if (ImGui::MenuItem("Settings"        )) teaPot.CreateView<View::Settings       >(std::string("Settings"        ));
                if (ImGui::MenuItem("Performance"     )) teaPot.CreateView<View::Performance    >(std::string("Performance"     ));
            
                ImGui::EndMenu();
            }

            ImGui::EndMainMenuBar();
        }
    }
}