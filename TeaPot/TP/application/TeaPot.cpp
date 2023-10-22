#include "TP/application/TeaPot.hpp"

namespace TP
{
    void ECSEventSystemSubscriber::Start      (TeaPot& teaPot) { static_cast<ECS&>(teaPot).ForEachEventSystem(&EventSubscriber::Start      , teaPot); }
    void ECSEventSystemSubscriber::Update     (TeaPot& teaPot) { static_cast<ECS&>(teaPot).ForEachEventSystem(&EventSubscriber::Update     , teaPot); }
    void ECSEventSystemSubscriber::FixedUpdate(TeaPot& teaPot) { static_cast<ECS&>(teaPot).ForEachEventSystem(&EventSubscriber::FixedUpdate, teaPot); }
    void ECSEventSystemSubscriber::Render     (TeaPot& teaPot) { static_cast<ECS&>(teaPot).ForEachEventSystem(&EventSubscriber::Render     , teaPot); }
    void ECSEventSystemSubscriber::End        (TeaPot& teaPot) { static_cast<ECS&>(teaPot).ForEachEventSystem(&EventSubscriber::End        , teaPot); }
}