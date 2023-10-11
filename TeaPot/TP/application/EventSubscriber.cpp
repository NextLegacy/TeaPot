#include "TP/application/EventSubscriber.hpp"

namespace TP
{
    void EventSubscriber::Initialize(std::shared_ptr<TeaPot> teaPot)
    {
        m_teaPot = teaPot;
    }
}