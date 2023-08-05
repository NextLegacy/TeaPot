#pragma once

#include <BoilingHotWater>

#include "editor/imgui/ImGuiWindow.hpp"

#include "editor/imgui/windows/Console.hpp"

#include "imgui_internal.h"

namespace TeaPot
{
    class Editor : public BHW::Application
    {
    private:
        std::vector<BHW::ImGuiWindow*> m_ImGuiWindows;

        static Editor* s_Instance;

    public:
        Editor();

        void AddImGuiWindow   (BHW::ImGuiWindow* window);
        void RemoveImGuiWindow(BHW::ImGuiWindow* window);

    protected:
        void Start () override;
        void Update() override;
        void Render() override;
    };
}