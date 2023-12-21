#pragma once

#include <string>

#include "TP/application/EventSubscriber.hpp"

namespace TP
{
    namespace View
    {
        struct Performance
        {
            std::string m_name;
            bool        m_open;

            Performance(std::string name, bool open = true);
        };

        class PerformanceRenderer : public EventSubscriber
        {
        public:
            void Render(TeaPot& teaPot);

        private:
            void RenderPerformance(TeaPot& teaPot, Performance& data);
        };
    }
}