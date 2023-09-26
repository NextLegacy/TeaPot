#pragma once

//#include "BHW/ecs/ECS.hpp"

#include <memory>

namespace BHW
{/*
    template <typename TECS>
    class ECSBaseEventSystem
    {
    public:
        inline ECSBaseEventSystem(std::shared_ptr<TECS> ecs) : m_ecs(ecs) { }

    protected:
        std::shared_ptr<TECS> m_ecs;
    };

    template <typename TBaseSystem, typename ...TSystems>
    class ECSSystemArgs { };

    template <typename TECS>
    class ECSSystem : public EventSystem<TECS::TECSSystemArgs::TBaseEventSystem, TECS::TECSSystemArgs::TSystems>
    {
        template <typename ...TArgs>
        inline ECSSystem(TArgs... args) : EventSystem<TECS::TECSSystemArgs::TBaseSystem, TECS::TECSSystemArgs::TSystems...>(args...) { }
    };*/
}