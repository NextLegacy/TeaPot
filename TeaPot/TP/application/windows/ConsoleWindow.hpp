#pragma once

#include <TC/gapi/impl/ImGui/ImGuiWindow.hpp>
#include <BHW/utils/console/Console.hpp>

namespace TP
{
    class ConsoleWindow : public TC::ImGuiWindow
    {
    public:
        ConsoleWindow(std::string name);

    protected:
        virtual void RenderImpl() override;
    };
}