#pragma once

#include <string>

#include "TeaPot/application/EventSubscriber.hpp"

namespace TP
{
    namespace View
    {
        struct Build
        {
            std::string m_name;
            bool        m_open;

            Build(std::string name, bool open = true);
        };

        class BuildRenderer : public EventSubscriber
        {
        public:
            void Render(TeaPot& teaPot);

        private:
            void RenderBuild(TeaPot& teaPot, Build& data);
        };
    }
}