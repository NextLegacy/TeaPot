#pragma once

#include <{}>
#include <Tea/Tea.hpp>

namespace Tea
{{
    class Application : public Tea::Tea
    <
        {},
        EventSubscriber, 
        Tea::ECSSystem
        <
            EventSubscriber,
{}
        >,
        Application,
{}
    > {{ }};
}}