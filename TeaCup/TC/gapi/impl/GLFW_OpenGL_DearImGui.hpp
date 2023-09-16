#pragma once

#include <imgui_impl_glfw.h>
#include <imgui_impl_opengl3.h>

#include "TC/gapi/impl/GLFW_OpenGL.hpp"
#include "TC/gapi/impl/DearImGui.hpp"

namespace TC
{
    class GLFW_OpenGL_DearImGui : public GLFW_OpenGL, public DearImGui
    {
    public:
        virtual int InitializeWindow() override;

        virtual void RenderFrame() override;

        virtual void TerminateWindow() override;
    };
}