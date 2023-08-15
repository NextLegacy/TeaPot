#pragma once

#include <array>
#include <vector>

#include "BHW/utils/event/EventSystem.hpp"

#include "BHW/utils/data/Data.hpp"

namespace BHW
{
    template <typename TData>
    struct ECSBaseEventSystem
    {
        inline ECSBaseEventSystem(TData* data) : m_data(data) { }

        TData* m_data;
    };

    template <typename TBaseEventSystem, typename TData>
    struct ECS : public EventSystem<TBaseEventSystem>, public DataStorage<TData>
    {
    public:
        inline ECS() : DataStorage<TData>(), EventSystem<TBaseEventSystem>(DataStorage<TData>::m_data) { }
    };
}