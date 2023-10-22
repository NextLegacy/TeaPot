#pragma once

#include <memory>

#include <TC/TeaCupBaseEventSystem.hpp>

namespace TP
{
    struct TeaPot;

    struct EventSubscriber : public TC::TeaCupBaseEvent<TeaPot> { };
}