#pragma once

namespace BHW
{
    // is determined at compile time | determines the index of a componentType
    typedef uint32_t ComponentTypeUUID;

    // changes undeterministically at runtime | determines the index of a component
    typedef uint32_t ComponentIndex;

    // combines of ComponentTypeUUID and ComponentIndex | (ComponentTypeUUID << 32) | (ComponentIndex
    typedef uint64_t ComponentUUID;

    template <typename ...TComponents>
    struct ComponentBitMask;

    template <typename ...TComponents>
    struct ECSComponents 
    {
        using TComponentBitMask = ComponentBitMask<TComponents...>;

        constexpr static size_t size = sizeof...(TComponents);

        template <typename TComponent>
        static constexpr ComponentTypeUUID ComponentTypeUUID()
        {
            return (std::is_same_v<TComponent, TComponents> + ...);
        }
    };
}