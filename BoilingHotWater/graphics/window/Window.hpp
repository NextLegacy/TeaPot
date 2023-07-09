#pragma once

#ifdef _WIN32

#include <Windows.h>

#endif

#include <string>   

#include <imgui.h>
#include <imgui_impl_glfw.h>
#include <imgui_impl_opengl3.h>

#include <GL/glew.h>
#include <GL/GL.h>
#include <GLFW/glfw3.h>
#include <glm/glm.hpp>

namespace BHW
{
    class Window
    {
    protected:
        GLFWwindow* m_window;

        ImGuiContext* m_imgui_context;
        ImGuiIO*      m_imgui_io     ;
        ImGuiStyle    m_imgui_style  ;

        glm::ivec2  m_size;

    public:
        Window();
        ~Window();

        int  virtual Initialize();

        void virtual StartFrame();

        void virtual EndFrame  ();

        void virtual Terminate ();

        inline GLFWwindow* GetWindow() const { return m_window; }

        inline ImGuiContext* GetImGuiContext() const { return m_imgui_context; }
        inline ImGuiIO*      GetImGuiIO     () const { return m_imgui_io     ; }
        inline ImGuiStyle    GetImGuiStyle  () const { return m_imgui_style  ; }

        void SetTitle(const std::string& title);

        inline glm::ivec2 Size       () const { return m_size                         ; }
        inline uint32_t   GetWidth   () const { return m_size.x                       ; }
        inline uint32_t   GetHeight  () const { return m_size.y                       ; }
        inline bool       ShouldClose() const { return glfwWindowShouldClose(m_window); }

        void Center();
    };
}