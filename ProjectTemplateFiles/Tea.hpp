#include <Tea/Tea.hpp>

#include <{}> // Graphics API include

namespace Tea
{
    class Application : public Tea::Tea
    <        
        {}, // Graphics API
        EventSubscriber, 
        Tea::ECSSystem
        <
            EventSubscriber,

            // ECS Systems
            {},
        >,
        TeaPot,

        // ECS Components
        {}
    >
    {{
    }}
}