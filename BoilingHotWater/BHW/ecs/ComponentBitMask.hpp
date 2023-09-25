#pragma once

#include <bitset>
#include "BHW/ecs/Component.hpp"

namespace BHW
{
    template <typename ...TComponents>
    struct ComponentBitMask
    {
    public:
    using bitset = std::bitset<TComponents::size>;

    public:
        static constexpr uint64_t s_Mask = (1 << s_Bits) - 1;

        static constexpr auto Hash = []<typename ...TComponents>(const ComponentBitMask<TComponents...>& componentBitMask)
        {
            return componentBitMask.m_bitset.to_ullong();
        };

    public:
        constexpr ComponentBitMask() : m_bitset(0) { }
        constexpr ComponentBitMask(bitset bitset) : m_bitset(bitset) { }
        constexpr ComponentBitMask(const ComponentBitMask& componentBitMask) : m_bitset(componentBitMask.m_bitset) { }
        constexpr ComponentBitMask(ComponentBitMask&& componentBitMask) : m_bitset(componentBitMask.m_bitset) { }

    public:
        template <typename ...TEnabledComponents>
        static constexpr ComponentBitMask<TComponents...> BitMask()
        {
            return ComponentBitMask().Enable<TEnabledComponents...>();
        }

    public:
        template <typename ...TComponents>
        constexpr ComponentBitMask<TComponents...>& Enable() 
        {
            (m_bitset.set(TComponents::ComponentTypeUUID<TComponents>()), ...);
            return *this;
        }

        template <typename ...TComponents>
        constexpr ComponentBitMask<TComponents...>& Disable() 
        {
            (m_bitset.reset(TComponents::ComponentTypeUUID<TComponents>()), ...);
            return *this;
        }

        template <typename TComponent>
        constexpr bool IsEnabled() const
        {
            return m_bitset.test(TComponents::ComponentTypeUUID<TComponent>());
        }

        template <typename TComponent>
        constexpr ComponentIndex IndexInEnabled() const
        {
            return m_bitset.count() - 1 - m_bitset.flip().find_first();
        }

    private:
        bitset m_bitset;
    };
}