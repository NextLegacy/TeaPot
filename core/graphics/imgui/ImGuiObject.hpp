#pragma once

#include "ImGuiWindow.hpp"

namespace DigitalTea
{
    class ImGuiObject
    {
    private:
        ImGuiWindow* m_window;
    public:
        ImGuiFrame(ImGuiWindow* window);
        ~ImGuiFrame();
    };
}