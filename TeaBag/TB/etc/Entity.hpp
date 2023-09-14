#pragma once

#include <string>
#include <vector>

#include "TB/etc/Component.hpp"

namespace TP
{
    class Entity
    {
    public:
        Entity();
        ~Entity();

        void Update();
        void Render();

    private:
        std::string m_name;

        Entity* m_parent;
        std::vector<Entity*> m_children;

        std::vector<Component*> m_components;

        bool m_isActive;
    };
}