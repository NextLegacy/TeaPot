#pragma once

#include <TC/TeaCupBaseEventSystem.hpp>

namespace Tea
{
    template <typename T>
    struct EventSubscriber : public TC::TeaCupBaseEvent<T> { };
}