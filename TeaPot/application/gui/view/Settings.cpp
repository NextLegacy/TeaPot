#include "TeaPot/application/gui/view/Settings.hpp"

#include "TeaPot/application/TeaPot.hpp"

#include "TeaPot/application/gui/Inspector.hpp"

namespace TP
{
    namespace View
    {
        Settings::Settings(std::string name, bool open) : m_name(name), m_open(open) { }

        void SettingsRenderer::Render(TeaPot& teaPot)
        {
            teaPot.ForEachComponent<Settings>([&](BHW::EntityUUID uuid, Settings& data)
            {
                RenderSettings(teaPot, data);
            });
        }

        void SettingsRenderer::RenderSettings(TeaPot& teaPot, Settings& data)
        {
            if (!data.m_open) return;

            ImGui::Begin(data.m_name.c_str(), &data.m_open);

            ImGuiInputField("Project", teaPot.m_project);

            ImGui::Separator();

            if (ImGui::Button("Reload")) teaPot.ReloadProject();

            ImGui::End();
        }
    }
}