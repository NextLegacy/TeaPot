#pragma once

#include <imgui.h>

#include "TC/gapi/GAPI.hpp"

#include "TC/gapi/impl/ImGui/ImGuiWindow.hpp"

namespace TC
{
    template <typename ...TWindows>
    class DearImGui
    {
    public:
        inline DearImGui() : m_imguiContext(nullptr), m_imguiIO(nullptr), m_imguiStyle(nullptr), m_windows() { }

        void RenderImGuiWindows()
        {
            std::apply([&](auto&... windows)
            {
                ((std::for_each(windows.begin(), windows.end(), [](auto window)
                {
                    window->Render();
                })), ...);
            }, m_windows);
        }

        template <typename TWindow, typename ...TArgs>
        TWindow& AddImGuiWindow(TArgs&&... args)
        {
            static_assert(std::disjunction_v<std::is_same<TWindow, TWindows>...>, "DearImGui: Window type not supported");

            BHW::Console::WriteLine("DearImGui: Adding window of type " + std::string(typeid(TWindow).name()));

            TWindow* window = new TWindow(std::forward<TArgs>(args)...);

            std::get<std::vector<TWindow*>>(m_windows).push_back(window);

            return *window;
        }

        void RemoveImGuiWindow(const std::string& name) 
        {
            std::apply([&](auto&... windows)
            {
                ((windows.erase(std::remove_if(windows.begin(), windows.end(), [&](auto window)
                {
                    return window->GetName() == name;
                }))), ...);
            }, m_windows);
        }

    protected:
        std::tuple<std::vector<TWindows*>...> m_windows;

        ImGuiContext* m_imguiContext;
        ImGuiIO*      m_imguiIO;
        ImGuiStyle*   m_imguiStyle;
    };
}