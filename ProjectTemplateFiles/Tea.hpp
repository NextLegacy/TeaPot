#include <Tea/Tea.hpp>

#include <{}> // Graphics API include
#include "Headers.hpp"

namespace Tea
{{
    class Application : public Tea
    <        
{}, // Graphics API
        System, 
        ECSSystem
        <
            System,

            // ECS Systems
{}
        >,
        Application,

        // ECS Components
{}
    >
    {{
    }};
}}
