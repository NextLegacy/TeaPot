#pragma once

#include "BHW/utils/console/Console.hpp"
#include "BHW/utils/TypeTraits.hpp"

#include <ranges>
#include <algorithm>
#include <iostream>


namespace BHW
{
    template <typename TBaseEventSystem, typename ...TEventSubscribers>
    struct EventSystem
    {
        static_assert((std::is_base_of_v<TBaseEventSystem, TEventSubscribers> && ...), "All event systems must derive from the base event system");

        template <typename ...TArgs>
        inline EventSystem(TArgs... args) : m_systems(TEventSubscribers(args...)...) { }

        template <typename ...TArgs>
        inline void ForEachEventSystem(void (TBaseEventSystem::*function)(TArgs...), TArgs... args)
        {
            std::for_each(m_systems.begin(), m_systems.end(), [&](auto& system) { (system->function)(args...); });
        }

    private:
        std::tuple<TEventSubscribers...> m_systems;
    };
}
