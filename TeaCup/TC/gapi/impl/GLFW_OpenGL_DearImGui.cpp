#include "TC/gapi/impl/GLFW_OpenGL_DearImGui.hpp"

namespace TC
{
    int GLFW_OpenGL_DearImGui::InitializeWindow()
    {
        if (GLFW_OpenGL::InitializeWindow() != 0) return 1;

        m_imguiContext = ImGui::CreateContext();
        m_imguiIO      = &ImGui::GetIO(); (void)m_imguiIO;
        //m_imguiStyle   = &ImGui::GetStyle();

        m_imguiIO->ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard;     // Enable Keyboard Controls
        m_imguiIO->ConfigFlags |= ImGuiConfigFlags_NavEnableGamepad;      // Enable Gamepad Controls
        m_imguiIO->ConfigFlags |= ImGuiConfigFlags_DockingEnable;         // Enable Docking
        m_imguiIO->ConfigFlags |= ImGuiConfigFlags_ViewportsEnable;       // Enable Multi-Viewport / Platform Windows
        //m_imgui_io.ConfigViewportsNoAutoMerge = true;
        //m_imgui_io.ConfigViewportsNoTaskBarIcon = true;

        ImGui::StyleColorsDark();
        //ImGui::StyleColorsLight();

        // When viewports are enabled we tweak WindowRounding/WindowBg so platform windows can look identical to regular ones.
        m_imguiStyle = &ImGui::GetStyle();
        if (m_imguiIO->ConfigFlags & ImGuiConfigFlags_ViewportsEnable)
        {
            m_imguiStyle->WindowRounding = 0.0f;
            m_imguiStyle->Colors[ImGuiCol_WindowBg].w = 1.0f;
        }

        ImGui_ImplGlfw_InitForOpenGL(m_window, true);
        ImGui_ImplOpenGL3_Init("#version 130");

        return 0;
    }

    void GLFW_OpenGL_DearImGui::RenderFrame()
    {
        glfwGetFramebufferSize(m_window, &m_size.x, &m_size.y);

        ImGui_ImplOpenGL3_NewFrame();
        ImGui_ImplGlfw_NewFrame();
        ImGui::NewFrame();

        Frame();

        ImGui::EndFrame();

        ImGui::Render();

        glViewport(0, 0, m_size.x, m_size.y);

        glClearColor(0, 0, 0, 1);
        glClear(GL_COLOR_BUFFER_BIT);

        ImGui_ImplOpenGL3_RenderDrawData(ImGui::GetDrawData());

        if (m_imguiIO->ConfigFlags & ImGuiConfigFlags_ViewportsEnable)
        {
            GLFWwindow* backup_current_context = glfwGetCurrentContext();
            
            ImGui::UpdatePlatformWindows();
            ImGui::RenderPlatformWindowsDefault();
            
            glfwMakeContextCurrent(backup_current_context);
        }

        glfwSwapBuffers(m_window);
    }

    void GLFW_OpenGL_DearImGui::TerminateWindow()
    {
        ImGui_ImplOpenGL3_Shutdown();
        ImGui_ImplGlfw_Shutdown();
        ImGui::DestroyContext(m_imguiContext);

        GLFW_OpenGL::TerminateWindow();
    }
}