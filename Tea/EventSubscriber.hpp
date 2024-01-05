#pragma once

#include <TC/TeaCupBaseEventSystem.hpp>

namespace Tea
{
    class Application;

    template <typename T = Application>
    struct EventSubscriber : public TC::TeaCupBaseEvent<T> { };

    struct EventStorage { };
}