#include "TP/application/windows/ConsoleWindow.hpp"

namespace TP
{
    ConsoleWindow::ConsoleWindow(std::string name) : TC::ImGuiWindow(name)
    {
    }

    void ConsoleWindow::RenderImpl()
    {
        ImGui::Begin(GetCompleteName().c_str(), &m_isVisible);

        ImGui::Text("Hello, world!");

        ImGui::End();
    }
}