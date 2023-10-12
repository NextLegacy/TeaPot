#include "TP/application/windows/Dockspace.hpp"

#include <TC/gapi/impl/ImGui/ImGuiDockspace.hpp>
#include <BHW/utils/console/Console.hpp>

#include <imgui_internal.h>

#include "TP/application/TeaPot.hpp"

namespace TP
{
    Dockspace::Dockspace(TeaPot& teaPot) : TC::ImGuiDockspace("Dockspace"), m_teaPot(teaPot)
    {
    }

    void Dockspace::DockSpaceImpl()
    {
        auto dock_id_left  = ImGui::DockBuilderSplitNode(m_dockspaceID, ImGuiDir_Left , 0.25f, nullptr, &m_dockspaceID);
        auto dock_id_right = ImGui::DockBuilderSplitNode(m_dockspaceID, ImGuiDir_Right, 0.3f , nullptr, &m_dockspaceID);
        auto dock_id_down  = ImGui::DockBuilderSplitNode(m_dockspaceID, ImGuiDir_Down , 0.35f, nullptr, &m_dockspaceID);

        TP::ConsoleWindow& console1 = m_teaPot.AddImGuiWindow<TP::ConsoleWindow>("Console");
        TP::ConsoleWindow& console2 = m_teaPot.AddImGuiWindow<TP::ConsoleWindow>("Console");
        TP::ConsoleWindow& console3 = m_teaPot.AddImGuiWindow<TP::ConsoleWindow>("Console");
        TP::ConsoleWindow& console4 = m_teaPot.AddImGuiWindow<TP::ConsoleWindow>("Console");

        ImGui::DockBuilderDockWindow(console1.GetCompleteName().c_str(), dock_id_left);
        ImGui::DockBuilderDockWindow(console2.GetCompleteName().c_str(), dock_id_right);
        ImGui::DockBuilderDockWindow(console3.GetCompleteName().c_str(), dock_id_down);

        ImGui::DockBuilderDockWindow(console4.GetCompleteName().c_str(), m_dockspaceID);
 
        BHW::Console::WriteLine("Dockspace");
    }
}