#pragma once

#include "TP/application/EventSubscriber.hpp"

namespace TP
{
    namespace View
    {
        struct ProjectLoader
        {
            std::string m_name;
            bool        m_open;

            std::string m_path;

            ProjectLoader(std::string name, bool open = true);
        };

        class ProjectLoaderRenderer : public EventSubscriber
        {
        public:
            void Render(TeaPot& teaPot);

        private:
            void RenderProjectLoader(TeaPot& teaPot, ProjectLoader& data);
        };
    }
}