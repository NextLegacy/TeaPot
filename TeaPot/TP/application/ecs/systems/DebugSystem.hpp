#pragma once

#include "TP/application/EventSubscriber.hpp"

namespace TP
{
    class DebugSystem : public EventSubscriber
    {
        void Update() override;
    };
}