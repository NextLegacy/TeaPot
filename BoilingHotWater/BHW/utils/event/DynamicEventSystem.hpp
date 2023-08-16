#pragma once

#include "BHW/utils/TypeTraits.hpp"

#include <ranges>
#include <algorithm>
#include <iostream>

#include "BHW/utils/event/EventSystem.hpp"

namespace BHW
{
    template <typename TEvent>
    struct DynamicBaseEvent
    {
        template <typename ...TArgs>
        inline void Call(void (TEvent::*function)(TArgs...), TArgs... args)
        {
            (static_cast<TEvent*>(this)->*function)(args...);
        }
    };

    template <typename TBaseEventSystem>
    struct DynamicEventSystem
    {
        template <typename ...TArgs>
        inline void ForEachEventSystem(void (TBaseEventSystem::*function)(TArgs...), TArgs... args)
        {
            
            std::for_each(m_systems.begin(), m_systems.end(), [function, args...](TBaseEventSystem& system)
            { 
                system.Call(function, args...);
            });
        }

        template <typename TBaseEventSystem, typename ...TArgs>
        inline void SubscribeEventSystem(TArgs... args)
        {
            m_systems.push_back(TBaseEventSystem(args...));
        }

    private:
        std::vector<TBaseEventSystem> m_systems;
    };
}
