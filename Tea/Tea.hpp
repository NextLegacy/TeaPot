#pragma once

#include <BHW/utils/console/Console.hpp>
#include <BHW/ecs/ECS.hpp>
#include <BHW/utils/event/EventSystem.hpp>

#include <TC/TeaCup.hpp>

namespace Tea
{
    template <typename TEventSubscriber, typename ...TSystems>
    class ECSSystem : public BHW::EventSystem<TEventSubscriber, TSystems...> { };

    template <typename TGAPI, typename TEventSubscriber, typename TECSSystem, typename TSelf, typename ...TComponents>
    class Tea : 
        public TC::TeaCup<TGAPI, TEventSubscriber, TSelf>, 
        public BHW::ECS<TECSSystem, TComponents...>, 
        public TEventSubscriber
    {
    public:
    using TTeaCup      = TC::TeaCup<TGAPI, TEventSubscriber, Tea>;
    using TEventSystem = TECSSystem;
    using TECS         = BHW::ECS<TEventSystem, TComponents...>;
    using TTea         = Tea<TGAPI, TEventSubscriber, TECSSystem, TComponents...>;

    public:
        inline Tea() : TECS() // TTeaCup(), TECS(), TEventSubscriber()
        {
            this->template SubscribeEventSystem<TSelf>(static_cast<TSelf*>(this));
        }

        virtual void Start      (TSelf& tea) override { static_cast<TECS&>(tea).ForEachEventSystem(&TEventSubscriber::Start      , tea); }
        virtual void Update     (TSelf& tea) override { static_cast<TECS&>(tea).ForEachEventSystem(&TEventSubscriber::Update     , tea); }
        virtual void FixedUpdate(TSelf& tea) override { static_cast<TECS&>(tea).ForEachEventSystem(&TEventSubscriber::FixedUpdate, tea); }
        virtual void Render     (TSelf& tea) override { static_cast<TECS&>(tea).ForEachEventSystem(&TEventSubscriber::Render     , tea); }
        virtual void End        (TSelf& tea) override { static_cast<TECS&>(tea).ForEachEventSystem(&TEventSubscriber::End        , tea); }
    };
}