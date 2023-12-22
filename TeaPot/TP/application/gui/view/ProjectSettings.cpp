#include "TP/application/gui/view/ProjectSettings.hpp"

#include "TP/application/TeaPot.hpp"

#include "TP/application/gui/Inspector.hpp"

namespace TP
{
    namespace View
    {
        ProjectSettings::ProjectSettings(std::string name, bool open) : m_name(name), m_open(open) { }

        void ProjectSettingsRenderer::Render(TeaPot& teaPot)
        {
            teaPot.ForEachComponent<ProjectSettings>([&](BHW::EntityUUID uuid, ProjectSettings& data)
            {
                RenderProjectSettings(teaPot, data);
            });
        }

        void ProjectSettingsRenderer::RenderProjectSettings(TeaPot& teaPot, ProjectSettings& data)
        {
            if (!data.m_open) return;

            ImGui::Begin(data.m_name.c_str(), &data.m_open);

            ImGui::Text("Project Settings");

            ImGuiInputField("Project", teaPot.m_project);
            
            ImGui::End();
        }
    }
}