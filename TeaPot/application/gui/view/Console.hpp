#pragma once

#include <BHW/utils/console/Console.hpp>
#include <BHW/utils/logger/Logger.hpp>

#include "TeaPot/application/EventSubscriber.hpp"

namespace TP
{
    namespace View
    {
        struct Console
        {
            std::string m_name;
            bool        m_open;

            Console(std::string& name, bool open = true);
        };

        class ConsoleRenderer : public EventSubscriber
        {
        public:
            void Render(TeaPot& teaPot);

        private:
            void RenderConsole(TeaPot& teaPot, Console& data);
        };
    }
}