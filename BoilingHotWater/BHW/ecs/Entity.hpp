#pragma once

#include "BHW/ecs/Component.hpp"

namespace BHW
{
    typedef uint64_t EntityUUID;

    template <typename TECS>
    struct Entity
    {
        Entity(EntityUUID entityUUID, std::shared_ptr<TECS> ecs) : m_entityUUID(entityUUID), m_ecs(ecs) { }

        template <typename TComponent>
        inline void AddComponent()
        {
            m_ecs->AddComponent<TComponent>(m_entityUUID);
        }

        template <typename TComponent>
        inline void RemoveComponent()
        {
            m_ecs->RemoveComponent<TComponent>(m_entityUUID);
        }

        template <typename TComponent>
        inline bool HasComponent()
        {
            return m_ecs->HasComponent<TComponent>(m_entityUUID);
        }

        template <typename TComponent>
        inline TComponent& GetComponent()
        {
            return m_ecs->GetComponent<TComponent>(m_entityUUID);
        }


        EntityUUID m_entityUUID;
        std::shared_ptr<TECS> m_ecs;
    };

    template <typename TECS>
    class EntityComponents
    {
        using TComponents = typename TECS::TComponents;
        using TComponentBitMask = ComponentBitMask<TECS>;

        inline EntityComponents(TComponentBitMask componentBitMask) : m_componentBitMask(componentBitMask), m_componentIndices()
        {

        }

        template <typename TComponent>
        inline void AddComponent(ComponentIndex componentIndex)
        {
            m_componentIndices[componentIndex] = TComponents::ComponentTypeUUID<TComponent>();
            m_componentBitMask.Enable<TComponent>();
        }

        template <typename TComponent>
        inline void RemoveComponent(ComponentIndex componentIndex)
        {
            m_componentIndices[componentIndex] = TComponents::ComponentTypeUUID<TComponent>();
            m_componentBitMask.Disable<TComponent>();
        }

        template <typename TComponent>
        inline bool HasComponent()
        {
            return m_componentBitMask.IsEnabled<TComponent>();
        }

        template <typename TComponent>
        inline ComponentIndex GetComponentIndex()
        {
            return m_componentIndices[TComponents::ComponentTypeUUID<TComponent>()];
        }
        
    
    private:
        TComponentBitMask m_componentBitMask;

        std::vector<ComponentIndex> m_componentIndices;
    };
}