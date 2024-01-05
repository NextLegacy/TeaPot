#pragma once

#include "TeaPot/application/EventSubscriber.hpp"

namespace TP
{
    class DockspaceRenderer : public EventSubscriber
    {
    public:
        void Render(TeaPot& teaPot);

    private:
        void RenderDockspace(TeaPot& teaPot);

        void SetupDockspace(TeaPot& teaPot, unsigned int& dockspaceID);
    };
}