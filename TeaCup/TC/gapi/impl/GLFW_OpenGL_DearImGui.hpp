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
        inline GLFW_OpenGL_DearImGui() : GLFW_OpenGL(), DearImGui() { }

        inline virtual int  InitializeWindow() override
        {
            if (GLFW_OpenGL::InitializeWindow() != 0) return 1;

            return 0;
        }

        inline virtual void InitializeDearImGui()
        {
            m_imguiIO->ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard;
            m_imguiIO->ConfigFlags |= ImGuiConfigFlags_NavEnableGamepad ;
            m_imguiIO->ConfigFlags |= ImGuiConfigFlags_DockingEnable    ;
            m_imguiIO->ConfigFlags |= ImGuiConfigFlags_ViewportsEnable  ;

            ImGui::StyleColorsDark();

            if (m_imguiIO->ConfigFlags & ImGuiConfigFlags_ViewportsEnable)
            {
                m_imguiStyle->WindowRounding = 0.0f;
                m_imguiStyle->Colors[ImGuiCol_WindowBg].w = 1.0f;
            }
        }

        inline virtual void InitializeDearImGuiContext ()
        {
            IMGUI_CHECKVERSION();
        
            m_imguiContext = ImGui::CreateContext();
            m_imguiIO      = &ImGui::GetIO();
            m_imguiStyle   = &ImGui::GetStyle();

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
            ImGui_ImplOpenGL3_NewFrame();
            ImGui_ImplGlfw_NewFrame();
            ImGui::NewFrame();

            Frame();

            ImGui::Render();

            glfwGetFramebufferSize(m_window, &m_size.x, &m_size.y);
        
            glViewport(0, 0, m_size.x, m_size.y);

            glClearColor(0.45f, 0.55f, 0.60f, 1.00f);
            glClear(GL_COLOR_BUFFER_BIT);

            ImGui_ImplOpenGL3_RenderDrawData(ImGui::GetDrawData());

            if (m_imguiIO->ConfigFlags & ImGuiConfigFlags_ViewportsEnable)
            {
                ImGui::UpdatePlatformWindows();
                ImGui::RenderPlatformWindowsDefault();
                glfwMakeContextCurrent(m_window);
            }

            glfwSwapBuffers(m_window);
            glfwPollEvents();
        }

        inline virtual void TerminateWindow() override
        {
            BHW::Console::WriteLine("Terminating window...");
            ImGui_ImplOpenGL3_Shutdown();
            ImGui_ImplGlfw_Shutdown();
            ImGui::DestroyContext(m_imguiContext);

            GLFW_OpenGL::TerminateWindow();
        }

        inline virtual void ProcessEvents() override
        {
            glfwPollEvents();
        }

        bool isRendererInitialized = false;
    };
}