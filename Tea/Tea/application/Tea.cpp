#include "Tea/application/Tea.hpp"

namespace Tea
{
    void ECSEventSystemSubscriber::Start      (Tea& tea) { static_cast<ECS&>(tea).ForEachEventSystem(&EventSubscriber::Start      , tea); }
    void ECSEventSystemSubscriber::Update     (Tea& tea) { static_cast<ECS&>(tea).ForEachEventSystem(&EventSubscriber::Update     , tea); }
    void ECSEventSystemSubscriber::FixedUpdate(Tea& tea) { static_cast<ECS&>(tea).ForEachEventSystem(&EventSubscriber::FixedUpdate, tea); }
    void ECSEventSystemSubscriber::Render     (Tea& tea) { static_cast<ECS&>(tea).ForEachEventSystem(&EventSubscriber::Render     , tea); }
    void ECSEventSystemSubscriber::End        (Tea& tea) { static_cast<ECS&>(tea).ForEachEventSystem(&EventSubscriber::End        , tea); }
}