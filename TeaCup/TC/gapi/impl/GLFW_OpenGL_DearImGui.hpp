#pragma once

#include <imgui_impl_glfw.h>
#include <imgui_impl_opengl3.h>

#include "TC/gapi/impl/GLFW_OpenGL.hpp"
#include "TC/gapi/impl/DearImGui.hpp"

namespace TC
{
    template <typename ...TWindows>
    class GLFW_OpenGL_DearImGui : public GLFW_OpenGL, public DearImGui<TWindows...>
    {
    public:
        inline GLFW_OpenGL_DearImGui() : GLFW_OpenGL(), DearImGui<TWindows...>() { }

        inline virtual int  InitializeWindow() override
        {
            if (GLFW_OpenGL::InitializeWindow() != 0) return 1;

            return 0;
        }

        inline virtual void InitializeDearImGui()
        {
            DearImGui<TWindows...>::m_imguiIO->ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard;
            DearImGui<TWindows...>::m_imguiIO->ConfigFlags |= ImGuiConfigFlags_NavEnableGamepad ;
            DearImGui<TWindows...>::m_imguiIO->ConfigFlags |= ImGuiConfigFlags_DockingEnable    ;
            DearImGui<TWindows...>::m_imguiIO->ConfigFlags |= ImGuiConfigFlags_ViewportsEnable  ;

            ImGui::StyleColorsDark();

            if (DearImGui<TWindows...>::m_imguiIO->ConfigFlags & ImGuiConfigFlags_ViewportsEnable)
            {
                DearImGui<TWindows...>::m_imguiStyle->WindowRounding = 0.0f;
                DearImGui<TWindows...>::m_imguiStyle->Colors[ImGuiCol_WindowBg].w = 1.0f;
            }
        }

        inline virtual void InitializeDearImGuiContext ()
        {
            IMGUI_CHECKVERSION();
        
            DearImGui<TWindows...>::m_imguiContext = ImGui::CreateContext();
            DearImGui<TWindows...>::m_imguiIO      = &ImGui::GetIO();
            DearImGui<TWindows...>::m_imguiStyle   = &ImGui::GetStyle();

            InitializeDearImGui();

            ImGui_ImplGlfw_InitForOpenGL(m_window, true);
            ImGui_ImplOpenGL3_Init("#version 410");
        }

        inline virtual void InitializeFrameThread() override
        {
            glfwMakeContextCurrent(m_window);
            //InitializeInput();

            glfwSwapInterval(0);

            InitializeDearImGuiContext();

            isRendererInitialized = true;
        }

        inline virtual void RenderFrame() override
        {
            glfwPollEvents();
            glfwPostEmptyEvent();
            
            ImGui_ImplOpenGL3_NewFrame();
            ImGui_ImplGlfw_NewFrame();
            ImGui::NewFrame();

            Frame();

            DearImGui<TWindows...>::template RenderImGuiWindows();

            ImGui::Render();

            glfwGetFramebufferSize(m_window, &m_size.x, &m_size.y);
        
            glViewport(0, 0, m_size.x, m_size.y);

            glClearColor(0.45f, 0.55f, 0.60f, 1.00f);
            glClear(GL_COLOR_BUFFER_BIT);

            ImGui_ImplOpenGL3_RenderDrawData(ImGui::GetDrawData());


            if (DearImGui<TWindows...>::m_imguiIO->ConfigFlags & ImGuiConfigFlags_ViewportsEnable)
            {
                ImGui::UpdatePlatformWindows();
                ImGui::RenderPlatformWindowsDefault();
                glfwMakeContextCurrent(m_window);
            }

            glfwSwapBuffers(m_window);
        }

        inline virtual void TerminateWindow() override
        {
            ImGui_ImplOpenGL3_Shutdown();
            ImGui_ImplGlfw_Shutdown();
            ImGui::DestroyContext(DearImGui<TWindows...>::m_imguiContext);

            GLFW_OpenGL::TerminateWindow();
        }

        inline virtual void ProcessEvents() override
        {
            glfwWaitEvents();
        }

        bool isRendererInitialized = false;
    };
}