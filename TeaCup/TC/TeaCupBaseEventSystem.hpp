#pragma once

#include <BHW/utils/event/DynamicEventSystem.hpp>
#include <BHW/utils/TypeTraits.hpp>

#include "TC/TeaCup.hpp"

namespace TC
{
    template <typename TApplication>
    struct TeaCupBaseEvent
    {
        inline virtual void Start      (TApplication& application) { }

        inline virtual void Update     (TApplication& application) { }
        inline virtual void FixedUpdate(TApplication& application) { }
        inline virtual void Render     (TApplication& application) { }

        inline virtual void End        (TApplication& application) { }
    };
}