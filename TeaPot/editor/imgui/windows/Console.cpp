#include "editor/imgui/windows/Console.hpp"

namespace TeaPot
{
    void Console::OnRender()
    {
        ImGui::Begin("Console");
        
        for (auto& log : m_logs)
        {
            ImGui::Text(log.message.c_str());
        }
        
        ImGui::End();
    }

    void Console::Log(const std::string& message)
    {
        m_logs.push_back({ message });
    }
}