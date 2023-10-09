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

        m_window = glfwCreateWindow(m_size.x, m_size.y, "TeaCup", nullptr, nullptr);

        if (m_window == nullptr)
            return 1;

        return 0;
    }

    void GLFW::InitializeInput()
    {
        glfwSetWindowUserPointer(m_window, this);

        glfwSetKeyCallback(m_window, [](GLFWwindow* window, int key, int scancode, int action, int mods)
        {
            GLFW& glfw = *static_cast<GLFW*>(glfwGetWindowUserPointer(window));
            Input& input = glfw.GetInput();

            input.KeyboardKeyCallback(key, scancode, action, mods);
        });

        glfwSetMouseButtonCallback(m_window, [](GLFWwindow* window, int button, int action, int mods)
        {
            GLFW& glfw = *static_cast<GLFW*>(glfwGetWindowUserPointer(window));
            Input& input = glfw.GetInput();

            input.MouseButtonCallback(button, action, mods);
        });

        glfwSetCursorPosCallback(m_window, [](GLFWwindow* window, double xpos, double ypos)
        {
            GLFW& glfw = *static_cast<GLFW*>(glfwGetWindowUserPointer(window));
            Input& input = glfw.GetInput();

            input.MouseMoveCallback(xpos, ypos);
        });

        glfwSetScrollCallback(m_window, [](GLFWwindow* window, double xoffset, double yoffset)
        {
            GLFW& glfw = *static_cast<GLFW*>(glfwGetWindowUserPointer(window));
            Input& input = glfw.GetInput();

            input.MouseScrollCallback(xoffset, yoffset);
        });

        glfwSetWindowRefreshCallback(m_window, [](GLFWwindow* window)
        {
            GLFW& glfw = *static_cast<GLFW*>(glfwGetWindowUserPointer(window));

            //glfwSwapBuffers(glfw.m_window);
            //glfw.RenderFrame();
        });
    }

    void GLFW::ProcessEvents()
    {
        glfwWaitEvents();
    }

    void GLFW::RenderFrame()
    {
        glfwGetFramebufferSize(m_window, &m_size.x, &m_size.y);

        Frame();

        glfwSwapBuffers(m_window);
    }

    void GLFW::TerminateWindow()
    {
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