#pragma once

#include <string>

#include "TeaPot/application/EventSubscriber.hpp"

namespace TP
{
    namespace View
    {
        struct Settings
        {
            std::string m_name;
            bool        m_open;

            Settings(std::string name, bool open = true);
        };

        class SettingsRenderer : public EventSubscriber
        {
        public:
            void Render(TeaPot& teaPot);

        private:
            void RenderSettings(TeaPot& teaPot, Settings& data);
        };
    }
}