#pragma once

#ifdef _WIN32

#include <Windows.h>

#endif

#include <string>   

#include <GL/glew.h>
#include <GL/GL.h>
#include <GLFW/glfw3.h>
#include <glm/glm.hpp>

namespace DigitalTea
{
    struct WindowProperties // TODO: split to RenderProperties and WindowProperties
    {
    public:
        const char* GLSLVersion;
        glm::vec4 ClearColor;
        std::string Title;
        glm::ivec2 Size;
        glm::ivec2 Position;
        bool VSync;
        bool Fullscreen;
        bool Resizable;

    public:
        WindowProperties() : 
            GLSLVersion("#version 130"), 
            ClearColor (glm::vec4(0.0f, 0.0f, 0.0f, 1.0f)), 
            Title      ("DigitalTea"), 
            Size       (glm::ivec2(1080, 620)), 
            Position   (glm::ivec2(0, 0)), 
            VSync      (true), 
            Fullscreen (false), 
            Resizable  (false)
        {
        }
    };

    class Window
    {
    protected:
        GLFWwindow* m_window;

        WindowProperties m_properties;
        WindowProperties m_previous_properties;

    public:
        Window();
        ~Window();

        int virtual Init();

        void virtual StartFrame();
        void virtual EndFrame  ();

        void virtual Shutdown();

        inline GLFWwindow* GetWindow() const { return m_window; }

        inline const WindowProperties& GetProperties() const { return m_properties; }
        inline void SetProperties(const WindowProperties& properties) { m_properties = properties; }
    
        inline glm::vec4   GetClearColor () const { return m_properties.ClearColor ; }
        inline const char* GetGLSLVersion() const { return m_properties.GLSLVersion; }
        inline std::string GetTitle      () const { return m_properties.Title      ; }
        inline glm::ivec2  GetSize       () const { return m_properties.Size       ; }
        inline int         GetWidth      () const { return m_properties.Size.x     ; }
        inline int         GetHeight     () const { return m_properties.Size.y     ; }
        inline glm::ivec1  GetPosition   () const { return m_properties.Position   ; }
        inline int         GetX          () const { return m_properties.Position.x ; }
        inline int         GetY          () const { return m_properties.Position.y ; }
        inline bool        IsVSync       () const { return m_properties.VSync      ; }
        inline bool        IsFullscreen  () const { return m_properties.Fullscreen ; }
        inline bool        IsResizable   () const { return m_properties.Resizable  ; }

        inline bool        ShouldClose   () const { return glfwWindowShouldClose(m_window); }

        inline void SetClearColor(const glm::vec4  & color     ) { m_properties.ClearColor = color     ; }
        inline void SetTitle     (const std::string& title     ) { m_properties.Title      = title     ; }
        inline void SetSize      (const glm::ivec2 & size      ) { m_properties.Size       = size      ; }
        inline void SetPosition  (const glm::ivec2 & position  ) { m_properties.Position   = position  ; }
        inline void SetVSync     (const bool       & vsync     ) { m_properties.VSync      = vsync     ; }
        inline void SetFullscreen(const bool       & fullscreen) { m_properties.Fullscreen = fullscreen; }
        inline void SetResizable (const bool       & resizable ) { m_properties.Resizable  = resizable ; }
    
        void Center();
    };
}