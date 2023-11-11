#pragma once

//#define GLFW_INCLUDE_NONE

#ifdef _WIN32

#include <windows.h>
    
#endif // _WIN32

//#include <GL/glew.h>
//#include <GL/GL.h>

#define GLFW_EXPOSE_NATIVE_WIN32

#include <GLFW/glfw3.h>
#include <GLFW/glfw3native.h>

#include "TC/gapi/GAPI.hpp"

namespace TC
{
    class GLFW : public GAPI
    {
    public:
        virtual int  InitializeWindow() override;
        virtual void InitializeInput () override;
        
        virtual void ProcessEvents  () override;
        virtual void RenderFrame    () override;

        virtual void TerminateWindow() override;

        virtual bool IsWindowOpen() override;

        virtual void SetTitle(const std::string& title) override;
        virtual void SetSize (const glm::ivec2&  size ) override;

        virtual void SetWindowHints() = 0;

        inline HWND GetWindowHandle()
        {
            return glfwGetWin32Window(m_window);
        }

    public:
        GLFWwindow* m_window;
    };
}