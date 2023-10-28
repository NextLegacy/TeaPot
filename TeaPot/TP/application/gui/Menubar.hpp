#pragma once

#include "TP/application/EventSubscriber.hpp"
#include <BHW/utils/console/Console.hpp>

namespace TP
{
    class MenuBarRenderer : public EventSubscriber
    {
    public:
        void Render(TeaPot& teaPot);

    private:
        void RenderMenuBar(TeaPot& teaPot);
    };
}