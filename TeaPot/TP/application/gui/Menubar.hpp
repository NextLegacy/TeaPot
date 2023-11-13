#pragma once

#include "TP/application/EventSubscriber.hpp"

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