#pragma once

#include <imgui.h>

#include "TC/gapi/GAPI.hpp"

namespace TC
{
    class DearImGui
    {
    protected:
        ImGuiContext* m_imguiContext;
        ImGuiIO*      m_imguiIO;
        ImGuiStyle*   m_imguiStyle;
    };
}