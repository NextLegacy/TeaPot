#include "TeaPot/application/gui/view/Performance.hpp"

#include "TeaPot/application/TeaPot.hpp"

#include "implot.h"

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
            ImGui::Text("Fifxed Update Time: %f", teaPot.GetFixedDeltaTime());

            //ImPlot::CreateContext();

            /*ImPlot::ShowDemoWindow();

            if (ImPlot::BeginPlot("Times", "Frame", "Time", ImVec2(-1, 200)))
            {
                static float frameTime[100] = { 0 };
                static int frameTimeOffset = 0;

                frameTime[frameTimeOffset] = teaPot.GetFrameDeltaTime();
                frameTimeOffset = (frameTimeOffset + 1) % 100;

                //ImPlot::PlotLine("Frame Time", frameTime, 100);

                static float updateTime[100] = { 0 };
                static int updateTimeOffset = 0;

                updateTime[updateTimeOffset % 100] = teaPot.GetDeltaTime();
                updateTimeOffset = (updateTimeOffset + 1);

                ImPlot::PlotLine("Update Time", updateTime, 100, {}, updateTimeOffset);

                static float fixedUpdateTime[100] = { 0 };
                static int fixedUpdateTimeOffset = 0;

                fixedUpdateTime[fixedUpdateTimeOffset] = teaPot.GetFixedDeltaTime();
                fixedUpdateTimeOffset = (fixedUpdateTimeOffset + 1) % 100;

                //ImPlot::PlotLine("Fixed Update Time", fixedUpdateTime, 100, {}, fixedUpdateTimeOffset);

                ImPlot::EndPlot();
            }
    */
            ImGui::End();
        }
    }
}