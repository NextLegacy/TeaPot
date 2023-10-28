#pragma once

#include "TP/application/EventSubscriber.hpp"
#include <BHW/utils/console/Console.hpp>

namespace TP
{
    struct ConsoleWindowData
    {
        std::string m_name;
        bool        m_open;

        inline ConsoleWindowData(const std::string& name) : m_name(name), m_open(true) { }
    };

    class ConsoleWindowRenderer : public EventSubscriber
    {
    public:
        void Render(TeaPot& teaPot);

    private:
        void RenderConsoleWindow(TeaPot& teaPot, ConsoleWindowData& data);
    };
}