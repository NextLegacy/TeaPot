#pragma once

#include <string>

#include "TP/application/EventSubscriber.hpp"

namespace TP
{
    namespace View
    {
        struct ProjectSettings
        {
            std::string m_name;
            bool        m_open;

            ProjectSettings(std::string name, bool open = true);
        };

        class ProjectSettingsRenderer : public EventSubscriber
        {
        public:
            void Render(TeaPot& teaPot);

        private:
            void RenderProjectSettings(TeaPot& teaPot, ProjectSettings& data);
        };
    }
}