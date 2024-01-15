#pragma once

#include <TC/EventSubscriber.hpp>
#include <BHW/utils/reflection/Reflection.hpp>

namespace Tea
{
    class Application;

    typedef TC::EventSubscriber<Application> System;

    struct Component { };
}

BHW_REFLECT(Tea::Component);
BHW_REFLECT(Tea::System   );