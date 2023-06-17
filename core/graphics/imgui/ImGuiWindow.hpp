#pragma once

#include "graphics/Window.hpp"

#include <imgui.h>
#include <imgui_impl_glfw.h>
#include <imgui_impl_opengl3.h>

namespace DigitalTea
{
    class ImGuiWindow : public Window
    {
    private:
        ImGuiContext* m_imgui_context;
        ImGuiIO* m_imgui_io;
        ImGuiStyle m_imgui_style;
    public:
        int  Init      () override;
        void StartFrame() override;
        void EndFrame  () override;
        void Shutdown  () override;
    };
}