#include "TP/application/windows/ConsoleWindow.hpp"

#include "TP/application/TeaPot.hpp"

namespace TP
{
    void ConsoleWindowRenderer::Render(TeaPot& teaPot)
    {
        ImGui::ShowDemoWindow();
        teaPot.ForEachComponent<ConsoleWindowData>([&](BHW::EntityUUID uuid, ConsoleWindowData& data)
        {
            RenderConsoleWindow(teaPot, data);
        });
    }

    void ConsoleWindowRenderer::RenderConsoleWindow(TeaPot& teaPot, ConsoleWindowData& data)
    {
        static int i = 5;

        if (!data.m_open) return;

        ImGui::Begin(data.m_name.c_str(), &data.m_open);

        if (ImGui::Button("Create Console"))   
        {
            auto console = teaPot.GetEntity(teaPot.CreateEntity());

            auto consoleData = console.AddComponent<ConsoleWindowData>("Console###1"+std::to_string(i++));
        }

        ImGui::Text("Hello from another window!");

        ImGui::End();
    }
}