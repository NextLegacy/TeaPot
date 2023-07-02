#pragma once

#include "Engine"

namespace Engine
{
    class Engine : public Activateable
    {
    public:
        Engine();
        ~Engine();

        void Start();
    };
}