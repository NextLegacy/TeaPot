#pragma once

#include "window/Window.hpp"

#include <GL/glew.h>
#include <GL/GL.h>
#include <GLFW/glfw3.h>
#include <glm/glm.hpp>

namespace BHW
{
    class OpenGLImpl : public Window
    {
    protected:
        int  API_InitializeWindow() override;

        void API_StartUpdate() override;
        void API_EndUpdate  () override;

        void API_StartFrame   () override;
        void API_EndFrame     () override;

        void API_TerminateWindow() override;
    
        std::map<int, KeyCode> API_CreateKeyMap() override;

    private:
        GLFWwindow* m_window;
    };
}