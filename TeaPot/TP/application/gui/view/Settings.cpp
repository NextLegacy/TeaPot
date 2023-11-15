#include "TP/application/gui/view/Settings.hpp"

#include "TP/application/TeaPot.hpp"

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

            ImGui::Text("Settings");
            
            ImGui::End();
        }
    }
}