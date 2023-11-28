#pragma once

#include <Tea/EventSubscriber.hpp>
#include "TP/application/EventSubscriber.hpp"

namespace TP
{
    class TeaPot;

    typedef Tea::EventSubscriber<TeaPot> EventSubscriber;
}