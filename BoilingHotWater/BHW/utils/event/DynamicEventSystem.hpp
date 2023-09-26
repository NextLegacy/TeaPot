#pragma once

#include "BHW/utils/TypeTraits.hpp"

#include <ranges>
#include <algorithm>
#include <iostream>

#include "BHW/utils/event/EventSystem.hpp"

namespace BHW
{
    template <typename TBaseEvent>
    struct DynamicEventSystem
    {
        template <typename ...TArgs>
        inline void ForEachEventSystem(void (TBaseEvent::*function)(TArgs...), TArgs... args)
        {
            std::for_each(m_systems.begin(), m_systems.end(), [function, args...](TBaseEvent* system)
            { 
                (system->*function)(args...);
            });
        }

        template <typename TEvent, typename ...TArgs>
        inline void SubscribeEventSystem(TArgs... args)
        {
            m_systems.push_back(new TEvent(args...));
        }

    private:
        std::vector<TBaseEvent*> m_systems;
    };
}
