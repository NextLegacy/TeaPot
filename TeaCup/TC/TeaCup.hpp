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

        inline bool RuntimeIsRunning() override { return TGAPI::IsWindowOpen(); }

    private:
        inline void ApplicationEntryPoint() override
        {
            BHW::DynamicEventSystem<TBaseEvent>::ForEachEventSystem(&TBaseEvent::Start);

            ApplicationRuntime::StartRuntime();

            BHW::DynamicEventSystem<TBaseEvent>::ForEachEventSystem(&TBaseEvent::End);
        }

        inline void RuntimeMainThreadStart       () override { TGAPI::InitializeWindow     (); }
        inline void RuntimeFixedUpdateThreadStart() override {                                 }
        inline void RuntimeRenderThreadStart     () override { TGAPI::InitializeFrameThread(); }

        inline void RuntimeMainThreadEnd         () override { TGAPI::TerminateWindow      (); }
        inline void RuntimeFixedUpdateThreadEnd  () override {                                 }
        inline void RuntimeRenderThreadEnd       () override {                                 }

        inline void RuntimeUpdate() override
        {
            TGAPI::ProcessEvents();
            BHW::DynamicEventSystem<TBaseEvent>::ForEachEventSystem(&TBaseEvent::Update);
        }

        inline void RuntimeFixedUpdate() override
        {
            BHW::DynamicEventSystem<TBaseEvent>::ForEachEventSystem(&TBaseEvent::FixedUpdate);
        }

        inline void RuntimeRender() override
        {
            TGAPI::RenderFrame();
        }

        inline void Frame() override
        {
            BHW::DynamicEventSystem<TBaseEvent>::ForEachEventSystem(&TBaseEvent::Render);
        }
    };
}