#pragma once

#include <BHW/utils/console/Console.hpp>
#include <BHW/ecs/ECS.hpp>
#include <BHW/utils/event/EventSystem.hpp>
#include <BHW/utils/random/Random.hpp>

#include <TC/TeaCup.hpp>
#include <TC/gapi/impl/GLFW_OpenGL_DearImGui.hpp>

#include "Tea/application/EventSubscriber.hpp"

namespace Tea
{
    class Tea;

    typedef TC::TeaCup
    <
        TC::GLFW_OpenGL_DearImGui,
        EventSubscriber, 
        Tea
    > TeaCup;

    typedef BHW::EventSystem<EventSubscriber, 

    > EventSystem;

    typedef BHW::ECS<EventSystem,

    > ECS;

    class ECSEventSystemSubscriber : public EventSubscriber
    {
        virtual void Start      (Tea& tea) override;
        virtual void Update     (Tea& tea) override;
        virtual void FixedUpdate(Tea& tea) override;
        virtual void Render     (Tea& tea) override;
        virtual void End        (Tea& tea) override;
    };

    class Tea : public TeaCup, public ECS
    {
    public:
        inline Tea() : TeaCup(), ECS(), m_random()
        {
            SubscribeEventSystem<ECSEventSystemSubscriber>();
        }

        template <typename TWindow, typename... TArgs>
        inline TWindow& CreateView(std::string name, TArgs&&... args)
        {
            auto entity = GetEntity(CreateEntity());
            auto& window = entity.AddComponent<TWindow>(name + "####" + m_random.UUID().ToString(), std::forward<TArgs>(args)...);
            return window;
        }

    public:
        BHW::Random m_random;
    };
}