#pragma once

#include <TC/TeaCupBaseEventSystem.hpp>

namespace TP
{
    class TeaPot;

    class EventSubscriber : public TC::TeaCupBaseEvent<TeaPot> { };
}