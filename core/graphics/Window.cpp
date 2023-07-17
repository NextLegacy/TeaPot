#include "Window.hpp"

#include <iostream>



namespace DigitalTea 
{
    Window::Window() : m_properties(), m_previous_properties()
    {
    }

    Window::~Window()
    {
        glfwTerminate();
    }

    int Window::Init()
    {
        glfwSetErrorCallback([](int error, const char* description)
        {
            std::cout << "GLFW Error: " << error << " - " << description << std::endl;
        });

        if (!glfwInit())
            return 1;

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
        //glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);  // 3.2+ only
        //glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);            // 3.0+ only

        // Create window with graphics context
        m_window = glfwCreateWindow(GetWidth(), GetHeight(), GetTitle().c_str(), nullptr, nullptr);  
    
        if (m_window == nullptr)
            return 1;

        glfwMakeContextCurrent(m_window);
        glfwSwapInterval(1); // Enable vsync

        //glfwSetWindowAttrib(m_window, GLFW_DECORATED, GLFW_FALSE);
        //glfwSetWindowAttrib(m_window, GLFW_TRANSPARENT_FRAMEBUFFER, GLFW_TRUE);

        return 0;
    }

    void Window::StartFrame()
    {
        glfwPollEvents();
    }

    void Window::EndFrame()
    {
        int display_w, display_h;
        glfwGetFramebufferSize(m_window, &display_w, &display_h);
        glViewport(0, 0, display_w, display_h);
        glClearColor(GetClearColor().x, GetClearColor().y, GetClearColor().z, GetClearColor().w);
        glClear(GL_COLOR_BUFFER_BIT);

        glfwMakeContextCurrent(m_window);

        glfwSwapBuffers(m_window);
    }

    void Window::Shutdown()
    {
        glfwDestroyWindow(m_window);
        glfwTerminate();
    }

    void Window::Center()
    {
        const GLFWvidmode* mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int x = (mode->width - GetWidth()) / 2;
        int y = (mode->height - GetHeight()) / 2;
        glfwSetWindowPos(m_window, x, y);
    }
}