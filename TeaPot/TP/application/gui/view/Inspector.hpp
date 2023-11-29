#pragma once

#include <BHW/utils/reflection/Reflection.hpp>

#include "TP/application/EventSubscriber.hpp"

namespace TP
{
    namespace View
    {
        struct Inspector
        {   
            std::string m_name;
            bool        m_open;

            //void (*m_render)(void* data);

            Inspector(std::string name, bool open = true);
        };

        class InspectorRenderer : public EventSubscriber
        {
        public:
            void Render(TeaPot& teaPot);

        private:
            void RenderInspector(TeaPot& teaPot, Inspector& data);

            template<typename T>
            void RenderField(const std::string& name, T& value);

            template<> void RenderField<std::string>(const std::string& name, std::string& value);
        };
    }
}