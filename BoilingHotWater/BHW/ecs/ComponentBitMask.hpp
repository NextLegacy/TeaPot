#pragma once

#include <bitset>
#include "BHW/ecs/Component.hpp"

namespace BHW
{
    template <typename ...TComponents>
    struct ComponentBitMask
    {
    public:
    using bitset = std::bitset<sizeof...(TComponents)>;

    public:
        inline static constexpr auto Hash = []<typename ...TComponents>(const ComponentBitMask<TComponents...>& componentBitMask)
        {
            return componentBitMask.m_bitset.to_ullong();
        };

    public:
        inline constexpr ComponentBitMask() : m_bitset(0) { }
        inline constexpr ComponentBitMask(bitset bitset) : m_bitset(bitset) { }
        inline constexpr ComponentBitMask(const ComponentBitMask& componentBitMask) : m_bitset(componentBitMask.m_bitset) { }
        inline constexpr ComponentBitMask(ComponentBitMask&& componentBitMask) : m_bitset(componentBitMask.m_bitset) { }

    public:
        template <typename TComponent>
        inline static constexpr ComponentTypeUUID ComponentTypeUUID()
        {
            return (std::is_same_v<TComponent, TComponents> + ...) - 1;
        }

        template <typename TComponent>
        inline static constexpr ComponentUUID ComponentUUID(ComponentIndex componentIndex)
        {
            return (ComponentTypeUUID<TComponent>() << 32) | (componentIndex);
        }

        template <typename ...TEnabledComponents>
        inline static constexpr ComponentBitMask<TComponents...> BitMask()
        {
            return ComponentBitMask().Enable<TEnabledComponents...>();
        }

    public:
        template <typename ...TComponents>
        inline constexpr ComponentBitMask<TComponents...>& Enable() 
        {
            (m_bitset.set(ComponentTypeUUID<TComponents>()), ...);
            return *this;
        }

        template <typename ...TComponents>
        inline constexpr ComponentBitMask<TComponents...>& Disable() 
        {
            (m_bitset.reset(ComponentTypeUUID<TComponents>()), ...);
            return *this;
        }

        template <typename TComponent>
        inline constexpr bool IsEnabled() const
        {
            return m_bitset.test(ComponentTypeUUID<TComponent>());
        }

        template <typename TComponent>
        inline constexpr ComponentIndex IndexInEnabled() const
        {
            return m_bitset.count() - 1 - m_bitset.flip().find_first();
        }

    private:
        bitset m_bitset;
    };
}