#pragma once

#include <string>
#include <imgui.h>

extern int id;

namespace TC
{
    class ImGuiWindow
    {
    public:
        ImGuiWindow(const std::string& name);
        ~ImGuiWindow();

        inline void SetVisible(bool               isVisible) { m_isVisible = isVisible; }
        inline void SetName   (const std::string& name     ) { m_name      = name     ; }

        inline const std::string& GetName  () const { return m_name     ; }
        inline uint32_t           GetID    () const { return m_id       ; }
        inline bool               IsVisible() const { return m_isVisible; }

        inline std::string GetCompleteName() const { return m_name + "###" + std::to_string(m_id); }

    public:
        void Render();

    protected:
        virtual void RenderImpl() = 0;

    protected:
        bool m_isVisible = true;
        std::string m_name;
        uint32_t m_id;
    };
}