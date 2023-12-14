#pragma once

#include "TP/application/EventSubscriber.hpp"

namespace TP
{
    class DebugSystem : public EventSubscriber
    {
        void Start (TeaPot& teaPot) override;
        void Update(TeaPot& teaPot) override;
        void Render(TeaPot& teaPot) override;
    };
}