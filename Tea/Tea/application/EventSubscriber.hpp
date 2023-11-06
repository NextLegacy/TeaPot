#pragma once

#include <memory>

#include <TC/TeaCupBaseEventSystem.hpp>

namespace Tea
{
    struct Tea;

    struct EventSubscriber : public TC::TeaCupBaseEvent<Tea> { };
}