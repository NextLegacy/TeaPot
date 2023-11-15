#pragma once

#include "TP/application/EventSubscriber.hpp"

namespace TP
{
    namespace View
    {
        struct SceneEditor
        {
            std::string m_name;
            bool        m_open;

            SceneEditor(std::string name, bool open = true);
        };

        class SceneEditorRenderer : public EventSubscriber
        {
        public:
            void Render(TeaPot& teaPot);

        private:
            void RenderSceneEditor(TeaPot& teaPot, SceneEditor& data);
        };
    }
}