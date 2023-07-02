#pragma once

#include "Engine"

#define RENDER_WINDOW_START(window) while (window->IsOpen()) { window->StartNewFrame();
#define RENDER_WINDOW_END(window) window->EndFrame(); }

namespace Engine
{
    class Window
    {
    private:
        GLFWwindow* m_window;

        ImGuiContext* m_imgui_context;
        ImGuiIO m_imgui_io;    
        ImGuiStyle m_imgui_style;

        const char* m_glsl_version = "#version 330";

        glm::vec4 m_clear_color = glm::vec4(0.45f, 0.55f, 0.60f, 1.00f);


        glm::ivec2 m_size;
        std::string m_title;
    public:
        Window(std::string title, glm::ivec2 size);
        ~Window();

        uint8_t Init();
        uint8_t InitImGui();

        void StartFrame();
        void EndFrame();

        void Shutdown();

        inline glm::ivec2 GetSize() const { return m_size; }
        inline std::string GetTitle() const { return m_title; }
        inline bool IsOpen() const { return !glfwWindowShouldClose(m_window); }

        inline void SetSize(const glm::ivec2& size) { m_size = size; }
        inline void SetTitle(const std::string& title) { m_title = title; }
    };
}