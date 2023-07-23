#pragma once

#include "editor/imgui/ImGuiWindow.hpp"

#include <vector>
#include <string>

namespace TeaPot
{
    struct Log
    {
        std::string message;
    };

    class Console : public BHW::ImGuiWindow
    {
    private:
        std::vector<Log> m_logs;
    
    public:
        void OnRender() override;
        
        void Log(const std::string& message);
    };
}