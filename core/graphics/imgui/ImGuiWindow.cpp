#include "ImGuiWindow.hpp"

#include <iostream>

namespace DigitalTea
{
    int ImGuiWindow::Init()
    {
        int result = Window::Init();

        IMGUI_CHECKVERSION();

        m_imgui_context = ImGui::CreateContext();

        m_imgui_io = &ImGui::GetIO(); (void)m_imgui_io;

        m_imgui_io->ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard;     // Enable Keyboard Controls
        m_imgui_io->ConfigFlags |= ImGuiConfigFlags_NavEnableGamepad;      // Enable Gamepad Controls
        m_imgui_io->ConfigFlags |= ImGuiConfigFlags_DockingEnable;         // Enable Docking
        m_imgui_io->ConfigFlags |= ImGuiConfigFlags_ViewportsEnable;       // Enable Multi-Viewport / Platform Windows
        //m_imgui_io.ConfigViewportsNoAutoMerge = true;
        //m_imgui_io.ConfigViewportsNoTaskBarIcon = true;

        ImGui::StyleColorsDark();
        //ImGui::StyleColorsLight();

        // When viewports are enabled we tweak WindowRounding/WindowBg so platform windows can look identical to regular ones.
        m_imgui_style = ImGui::GetStyle();
        if (m_imgui_io->ConfigFlags & ImGuiConfigFlags_ViewportsEnable)
        {
            m_imgui_style.WindowRounding = 0.0f;
            m_imgui_style.Colors[ImGuiCol_WindowBg].w = 1.0f;
        }

        // Setup Platform/Renderer backends
        ImGui_ImplGlfw_InitForOpenGL(m_window, true);
        ImGui_ImplOpenGL3_Init("#version 130");

        return result;
    }

    void ImGuiWindow::StartFrame()
    {   
        Window::StartFrame();

        ImGui_ImplOpenGL3_NewFrame();
        ImGui_ImplGlfw_NewFrame();
        ImGui::NewFrame();
    }

    void ImGuiWindow::EndFrame()
    {
        ImGui::Render();
        int display_w, display_h;
        glfwGetFramebufferSize(m_window, &display_w, &display_h);
        glViewport(0, 0, display_w, display_h);
        glClearColor(GetClearColor().x, GetClearColor().y, GetClearColor().z, GetClearColor().w);
        glClear(GL_COLOR_BUFFER_BIT);
        ImGui_ImplOpenGL3_RenderDrawData(ImGui::GetDrawData());

        if (m_imgui_io->ConfigFlags & ImGuiConfigFlags_ViewportsEnable)
        {
            GLFWwindow* backup_current_context = glfwGetCurrentContext();
            ImGui::UpdatePlatformWindows();
            ImGui::RenderPlatformWindowsDefault();
            glfwMakeContextCurrent(backup_current_context);
        }

        glfwSwapBuffers(m_window);
    }

    void ImGuiWindow::Shutdown()
    {
        ImGui_ImplOpenGL3_Shutdown();
        ImGui_ImplGlfw_Shutdown();
        ImGui::DestroyContext();

        Window::Shutdown();
    }
}