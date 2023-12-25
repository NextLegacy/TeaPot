#pragma once

#include <string>

#include "TP/application/EventSubscriber.hpp"

namespace TP
{
    namespace View
    {
        struct SceneView
        {
            std::string m_name;
            bool        m_open;

            SceneView(std::string name, bool open = true);
        };

        class SceneViewRenderer : public EventSubscriber
        {
        public:
            void Render(TeaPot& teaPot);

        private:
            void RenderSceneView(TeaPot& teaPot, SceneView& data);
        };
    }
}