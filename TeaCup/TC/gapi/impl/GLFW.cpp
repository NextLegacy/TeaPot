#include "TC/gapi/impl/GLFW.hpp"

namespace TC
{
    int GLFW::InitializeWindow()
    {
        glfwSetErrorCallback([](int error, const char* description)
        {
            std::cout << "GLFW Error: " << error << " - " << description << std::endl;
        });

        if (!glfwInit())
            return 1;

        SetWindowHints();

        m_window = glfwCreateWindow(m_size.x, m_size.y, "Boiling Hot Water", nullptr, nullptr);

        if (m_window == nullptr)
            return 1;

        glfwMakeContextCurrent(m_window);

        InitializeInput();

        glfwSwapInterval(0);

        return 0;
    }

    void GLFW::InitializeInput()
    {
        glfwSetWindowUserPointer(m_window, GetInput());

        glfwSetKeyCallback(m_window, [](GLFWwindow* window, int key, int scancode, int action, int mods)
        {
            auto& input = *static_cast<Input*>(glfwGetWindowUserPointer(window));

            input.KeyboardKeyCallback(key, scancode, action, mods);
        });

        glfwSetMouseButtonCallback(m_window, [](GLFWwindow* window, int button, int action, int mods)
        {
            auto& input = *static_cast<Input*>(glfwGetWindowUserPointer(window));

            input.MouseButtonCallback(button, action, mods);
        });

        glfwSetCursorPosCallback(m_window, [](GLFWwindow* window, double xpos, double ypos)
        {
            auto& input = *static_cast<Input*>(glfwGetWindowUserPointer(window));

            input.MouseMoveCallback(xpos, ypos);
        });

        glfwSetScrollCallback(m_window, [](GLFWwindow* window, double xoffset, double yoffset)
        {
            auto& input = *static_cast<Input*>(glfwGetWindowUserPointer(window));

            input.MouseScrollCallback(xoffset, yoffset);
        });
    }

    void GLFW::ProcessEvents()
    {
        glfwPollEvents();
    }

    void GLFW::RenderFrame()
    {
        glfwGetFramebufferSize(m_window, &m_size.x, &m_size.y);

        Frame();

        glfwSwapBuffers(m_window);
    }

    void GLFW::TerminateWindow()
    {
        glfwMakeContextCurrent(nullptr);

        glfwDestroyWindow(m_window);
        glfwTerminate();
    }

    bool GLFW::IsWindowOpen()
    {
        return !glfwWindowShouldClose(m_window);
    }

    void GLFW::SetTitle(const std::string& title)
    {
        glfwSetWindowTitle(m_window, title.c_str());
    }
    
    void GLFW::SetSize(const glm::ivec2& size)
    {
        m_size = size;

        glfwSetWindowSize(m_window, m_size.x, m_size.y);
    }
}