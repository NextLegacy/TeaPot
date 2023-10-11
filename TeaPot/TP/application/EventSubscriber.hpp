#pragma once

#include <memory>

#include <TC/TeaCupBaseEventSystem.hpp>

namespace TP
{
    struct TeaPot;

    struct EventSubscriber : public TC::TeaCupBaseEvent
    {
        void Initialize(std::shared_ptr<TeaPot> teaPot);

    protected:
        std::shared_ptr<TeaPot> m_teaPot;
    };
}