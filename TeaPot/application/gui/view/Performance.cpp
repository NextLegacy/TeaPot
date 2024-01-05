#include "TeaPot/application/gui/view/Performance.hpp"

#include "TeaPot/application/TeaPot.hpp"

namespace TP
{
    namespace View
    {
        Performance::Performance(std::string name, bool open) : m_name(name), m_open(open) { }

        void PerformanceRenderer::Render(TeaPot& teaPot)
        {
            teaPot.ForEachComponent<Performance>([&](BHW::EntityUUID uuid, Performance& data)
            {
                RenderPerformance(teaPot, data);
            });
        }

        void PerformanceRenderer::RenderPerformance(TeaPot& teaPot, Performance& data)
        {
            if (!data.m_open) return;

            ImGui::Begin(data.m_name.c_str(), &data.m_open);

            ImGui::Text("FPS: %f", teaPot.GetFPS());
            ImGui::Text("Frame Time: %f", teaPot.GetFrameDeltaTime());

            ImGui::Separator();

            ImGui::Text("UPS: %f", teaPot.GetUPS());
            ImGui::Text("Update Time: %f", teaPot.GetDeltaTime());
            
            ImGui::Separator();

            ImGui::Text("Fixed UPS: %f", teaPot.GetFUPS());
            ImGui::Text("Fixed Update Time: %f", teaPot.GetFixedDeltaTime());

            ImGui::End();
        }
    }
}