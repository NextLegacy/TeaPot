#include "TP/application/windows/MenuBar.hpp"

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
                if (ImGui::MenuItem("New"))
                {
                }

                if (ImGui::MenuItem("Open"))
                {
                }

                if (ImGui::MenuItem("Save"))
                {
                }

                if (ImGui::MenuItem("Save As"))
                {
                }

                ImGui::Separator();

                if (ImGui::MenuItem("Exit"))
                {
                    //teaPot.Close();
                }

                ImGui::EndMenu();
            }

            if (ImGui::BeginMenu("Window"))
            {
                if (ImGui::MenuItem("New Console"))
                {
                    auto console     = teaPot.GetEntity(teaPot.CreateEntity());
                    auto consoleData = console.AddComponent<ConsoleWindowData>("Console###v" + std::to_string(counter++));
                }

                ImGui::EndMenu();
            }

            ImGui::EndMainMenuBar();
        }
    }
}