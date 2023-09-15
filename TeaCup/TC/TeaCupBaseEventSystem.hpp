#pragma once

#include <BHW/utils/event/DynamicEventSystem.hpp>
#include <BHW/utils/TypeTraits.hpp>

#include "TC/TeaCup.hpp"

namespace TC
{
    template <typename TBaseEventSystem>
    struct TeaCupBaseEvent : public BHW::DynamicBaseEvent<TBaseEventSystem>
    {
        inline virtual void Start      () { }

        inline virtual void Update     () { }
        inline virtual void FixedUpdate() { }
        inline virtual void Render     () { }

        inline virtual void End        () { }
    };
}