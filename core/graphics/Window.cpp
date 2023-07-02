#include "Window.hpp"

namespace Engine 
{
    Window::Window(std::string title, glm::ivec2 size)
        : m_title(title), m_size(size)
    {
        Init();
    }

    Window::~Window()
    {
        glfwTerminate();
    }

    uint8_t Window::Init()
    {
        glfwSetErrorCallback([](int error, const char* description)
        {
            std::cout << "GLFW Error: " << error << " - " << description << std::endl;
        });

        if (!glfwInit())
            return 1;

        // GL 3.0 + GLSL 130
        m_glsl_version = "#version 130";
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
        //glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);  // 3.2+ only
        //glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);            // 3.0+ only

        // Create window with graphics context
        m_window = glfwCreateWindow(m_size.x, m_size.y + 50, m_title.c_str(), nullptr, nullptr);    
    
        if (m_window == nullptr)
            return 1;

        glfwMakeContextCurrent(m_window);
        glfwSwapInterval(1); // Enable vsync

        if (InitImGui())
            return 1;

        const GLFWvidmode* mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int x = (mode->width - m_size.x) / 2;
        int y = (mode->height - m_size.y) / 2;
        glfwSetWindowPos(m_window, x, y);

        glfwSetWindowAttrib(m_window, GLFW_DECORATED, GLFW_FALSE);
        glfwSetWindowAttrib(m_window, GLFW_TRANSPARENT_FRAMEBUFFER, GLFW_TRUE);

        return 0;
    }

    uint8_t Window::InitImGui()
    {
        // Setup Dear ImGui context
        IMGUI_CHECKVERSION();

        m_imgui_context = ImGui::CreateContext();

        m_imgui_io = ImGui::GetIO(); (void)m_imgui_io;

        m_imgui_io.ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard;     // Enable Keyboard Controls
        m_imgui_io.ConfigFlags |= ImGuiConfigFlags_NavEnableGamepad;      // Enable Gamepad Controls
        m_imgui_io.ConfigFlags |= ImGuiConfigFlags_DockingEnable;         // Enable Docking
        m_imgui_io.ConfigFlags |= ImGuiConfigFlags_ViewportsEnable;       // Enable Multi-Viewport / Platform Windows
        //m_imgui_io.ConfigViewportsNoAutoMerge = true;
        //m_imgui_io.ConfigViewportsNoTaskBarIcon = true;

        // Setup Dear ImGui style
        ImGui::StyleColorsDark();
        //ImGui::StyleColorsLight();

        // When viewports are enabled we tweak WindowRounding/WindowBg so platform windows can look identical to regular ones.
        m_imgui_style = ImGui::GetStyle();
        if (m_imgui_io.ConfigFlags & ImGuiConfigFlags_ViewportsEnable)
        {
            m_imgui_style.WindowRounding = 0.0f;
            m_imgui_style.Colors[ImGuiCol_WindowBg].w = 1.0f;
        }

        // Setup Platform/Renderer backends
        ImGui_ImplGlfw_InitForOpenGL(m_window, true);
        ImGui_ImplOpenGL3_Init(m_glsl_version);

        return 0;
    }

    void Window::StartFrame()
    {
        ImGui_ImplOpenGL3_NewFrame();
        ImGui_ImplGlfw_NewFrame();
        ImGui::NewFrame();
    }

    void Window::EndFrame()
    {
        ImGui::Render();
        ImGui_ImplOpenGL3_RenderDrawData(ImGui::GetDrawData());

        if (m_imgui_io.ConfigFlags & ImGuiConfigFlags_ViewportsEnable)
        {
            GLFWwindow* backup_current_context = glfwGetCurrentContext();
            ImGui::UpdatePlatformWindows();
            ImGui::RenderPlatformWindowsDefault();
            glfwMakeContextCurrent(backup_current_context);
        }

        glfwSwapBuffers(m_window);
    }

    void Window::Shutdown()
    {
        ImGui_ImplOpenGL3_Shutdown();
        ImGui_ImplGlfw_Shutdown();
        ImGui::DestroyContext();

        glfwDestroyWindow(m_window);
        glfwTerminate();
    }
}