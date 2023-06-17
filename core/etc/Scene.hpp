#pragma once

#include <vector>

#include "etc/Entity.hpp"
#include "etc/Engine.hpp"

namespace DigitalTea
{
    class Scene
    {
    private:
        Engine* m_engine;

        std::vector<Entity*> m_entities;              // All entities in the scene
        std::vector<Entity*> m_entitiesFromLastFrame; // Unchanged entities from last frame
    public:
        Scene(Engine* engine);
        ~Scene();

        void AddEntity   (Entity* entity);
        void RemoveEntity(Entity* entity);

        inline Engine* GetEngine() const { return m_engine; }
    private:
        friend class Engine;
        
        void Start();
        void Update();
        void Render();
    };
}