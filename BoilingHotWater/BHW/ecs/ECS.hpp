#pragma once

#include <array>
#include <vector>
#include <unordered_map>
#include <functional>

#include "BHW/utils/event/EventSystem.hpp"

#include "BHW/utils/data/Data.hpp"
#include "BHW/ecs/Entity.hpp"
#include "BHW/ecs/Component.hpp"
#include "BHW/ecs/ComponentBitMask.hpp"
#include "BHW/ecs/EventSystem.hpp"

//#include "BHW/utils/random/Random.hpp"

#include <memory>

namespace BHW
{
    template <typename TECSComponents, typename TECSSystems>
    class ECS : public std::enable_shared_from_this<ECS<TECSComponents, TECSSystems>>, public TECSSystems
    {
    using TComponents       = typename TECSComponents::TComponents;
    using TComponentBitMask = typename TECSComponents::TComponentBitMask;

    using TSystems          = typename TECSSystems   ::TSystems;

    using TEntity           = Entity          <TECSComponents>;
    using TEntityComponents = EntityComponents<TECSComponents>;

    public:
        inline ECS() : m_components(), m_entityComponentBitMasks(), m_entityComponents() { }

        inline ECS() : TECSSystems(this->shared_from_this()), m_components(), m_entityComponentBitMasks(), m_entityComponents() 
        {
            m_components           .reserve(1000); // too much? noooo this is fine, the more the merrier :)
            m_entityComponents     .reserve(1000);
            m_componentEntityOwners.reserve(1000);
        }

    public:
        inline EntityUUID CreateEntity()
        {
            static uint64_t entityUUID = 0; // TODO: Replace with random UUID
            EntityUUID entityUUID = entityUUID++;

            m_entityComponents[entityUUID] = TEntityComponents(entityUUID, TComponentBitMask());

            return entityUUID;
        }

        template <typename TComponent>
        inline void AddComponent(EntityUUID entityUUID)
        {
            if (!EntityExists(entityUUID)) return;

            if (HasComponent<TComponent>(entityUUID)) return;

            // add to m_components
            // enable on m_entityComponents bitmask
            // add to m_entityComponents componentIndices
            // add to m_componentEntityOwners
        }

        template <typename TComponent>
        inline bool HasComponent(EntityUUID entityUUID)
        {
            if (!EntityExists(entityUUID)) return false;

            return m_entityComponents[entityUUID].m_componentBitMask.IsEnabled<TComponent>();
        }

    private:
        std::tuple<std::vector<TComponents>...> m_components;

        std::unordered_map<EntityUUID, TEntityComponents> m_entityComponents;
        
        std::unordered_map<ComponentUUID, EntityUUID> m_componentEntityOwners;
    };
}