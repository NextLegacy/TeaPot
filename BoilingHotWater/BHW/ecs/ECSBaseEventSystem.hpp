#pragma once

#include "BHW/utils/event/EventSystem.hpp"

namespace BHW
{
    template <typename TData>
    struct ECSBaseEventSystem
    {
        inline ECSSystemBase(TData& data) : m_data(data) { }

        TData& m_data;
    };
}