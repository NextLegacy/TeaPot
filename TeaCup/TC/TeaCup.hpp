#pragma once

#include <type_traits>

#include <BHW/application/Application.hpp>

#include "TC/gapi/GAPI.hpp"

#include "TC/TeaCupRuntime.hpp"

#include <BHW/utils/event/DynamicEventSystem.hpp>

#include "TC/TeaCupBaseEventSystem.hpp"

namespace TC
{
    template<typename TGAPI, typename TBaseEvent>
    class TeaCup : public BHW::Application, public ApplicationRuntime, public TGAPI, public BHW::DynamicEventSystem<TBaseEvent>
    {
    static_assert(std::is_base_of<GAPI, TGAPI>::value, "T must be a GAPI");
    //static_assert(std::is_base_of<TeaCupBaseEvent, TBaseEvent>::value, "TBaseEvent must be a TeaCupBaseEvent");

    public:
        TeaCup() : BHW::Application(), ApplicationRuntime(), TGAPI(), BHW::DynamicEventSystem<TBaseEvent>() { }

        inline bool ApplicationRuntime::IsRunning() { return TGAPI::IsWindowOpen(); }

    private:
        inline void ApplicationEntryPoint() override
        {
            TGAPI::InitializeWindow();

            BHW::Console::WriteLine("Run!");
            BHW::DynamicEventSystem<TBaseEvent>::ForEachEventSystem(&TBaseEvent::Start);

            ApplicationRuntime::StartRuntime();

            BHW::DynamicEventSystem<TBaseEvent>::ForEachEventSystem(&TBaseEvent::End);
            
            TGAPI::TerminateWindow();
        }

        inline void Tick() override
        {
            BHW::DynamicEventSystem<TBaseEvent>::ForEachEventSystem(&TBaseEvent::Update);
        }

        inline void FixedTick() override
        {
            BHW::DynamicEventSystem<TBaseEvent>::ForEachEventSystem(&TBaseEvent::FixedUpdate);
        }

        inline void ApplicationRuntime::Frame() override
        {
            TGAPI::ProcessEvents();
            TGAPI::RenderFrame();
        }

        inline void TGAPI::Frame() override
        {
            BHW::DynamicEventSystem<TBaseEvent>::ForEachEventSystem(&TBaseEvent::Render);
        }
    };
}