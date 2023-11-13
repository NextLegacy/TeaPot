#include "TP/application/gui/view/Console.hpp"

#include "TP/application/TeaPot.hpp"

namespace TP
{
    namespace View
    {
        Console::Console(std::string& name, bool open) : m_name(name), m_open(open) { }

        void ConsoleRenderer::Render(TeaPot& teaPot)
        {
            teaPot.ForEachComponent<Console>([&](BHW::EntityUUID uuid, Console& data)
            {
                RenderConsole(teaPot, data);
            });
        }

        void ConsoleRenderer::RenderConsole(TeaPot& teaPot, Console& data)
        {
            if (!data.m_open) return;

            ImGui::Begin(data.m_name.c_str(), &data.m_open);

            //for (auto& log : data.m_logger.GetLogs())
            //{
            //    ImGui::Text(log.message.c_str());
            //}

            ImGui::End();
        }   
    }
}