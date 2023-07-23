#include "editor/imgui/ImGuiWindow.hpp"

namespace BHW
{
    void ImGuiWindow::Render()
    {
        Begin();

        OnRender();

        End();
    }

    void ImGuiWindow::Begin()
    {
    }

    void ImGuiWindow::End()
    {
    }
}