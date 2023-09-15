#pragma once

#include <type_traits>

#include <BHW/application/Application.hpp>

#include "TC/gapi/GAPI.hpp"

#include "TC/TeaCupRuntime.hpp"

#include <BHW/utils/event/DynamicEventSystem.hpp>

#include "TC/TeaCupBaseEventSystem.hpp"

namespace TC
{
    template<typename T, typename TBaseEventSystem>
    class TeaCup : public BHW::Application, public ApplicationRuntime, public T, public BHW::DynamicEventSystem<TBaseEventSystem>
    {
    static_assert(std::is_base_of<GAPI, T>::value, "T must be a GAPI");
    //static_assert(std::is_base_of<TeaCupBaseEvent, TBaseEventSystem>::value, "TBaseEventSystem must be a TeaCupBaseEvent");

    public:
        TeaCup() : BHW::Application(), ApplicationRuntime(), T(), BHW::DynamicEventSystem<TBaseEventSystem>() { }

        inline bool ApplicationRuntime::IsRunning() { return T::IsWindowOpen(); }

    private:
        inline void ApplicationEntryPoint() override
        {
            T::InitializeWindow();

            BHW::Console::WriteLine("Run!");
            BHW::DynamicEventSystem<TBaseEventSystem>::ForEachEventSystem(&TBaseEventSystem::Start);

            ApplicationRuntime::StartRuntime();

            BHW::DynamicEventSystem<TBaseEventSystem>::ForEachEventSystem(&TBaseEventSystem::End);
            
            T::TerminateWindow();
        }

        inline void ApplicationRuntime::Tick() override
        {
            BHW::DynamicEventSystem<TBaseEventSystem>::ForEachEventSystem(&TBaseEventSystem::Update);
        }

        inline void ApplicationRuntime::FixedTick() override
        {
            BHW::DynamicEventSystem<TBaseEventSystem>::ForEachEventSystem(&TBaseEventSystem::FixedUpdate);
        }

        inline void ApplicationRuntime::Frame() override
        {
            T::ProcessEvents();
            T::RenderFrame();
        }

        inline void T::Frame() override
        {
            BHW::DynamicEventSystem<TBaseEventSystem>::ForEachEventSystem(&TBaseEventSystem::Render);
        }
    };
}