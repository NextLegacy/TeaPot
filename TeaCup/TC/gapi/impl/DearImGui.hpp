#pragma once

#include <imgui.h>
#include <imgui_internal.h>

#include "TC/gapi/GAPI.hpp"

namespace TC
{
    class DearImGui
    {
    public:
        inline DearImGui() : m_imguiContext(nullptr), m_imguiIO(nullptr), m_imguiStyle(nullptr) { }

        inline std::string CreateUniqueName(const std::string& name) const 
        { 
            static uint32_t m_id = 0;
            return name + "###" + std::to_string(m_id++); 
        }

        inline ImGuiContext* GetContext() const { return m_imguiContext; }
        inline ImGuiIO*      GetIO     () const { return m_imguiIO;      }
        inline ImGuiStyle*   GetStyle  () const { return m_imguiStyle;   }

    protected:
        ImGuiContext* m_imguiContext;
        ImGuiIO*      m_imguiIO;
        ImGuiStyle*   m_imguiStyle;
    };
}