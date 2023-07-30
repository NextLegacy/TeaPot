#include "utils/event/Event.hpp"

namespace BHC
{
    template <typename... Args>
    Event<Args...>::Event() : m_handlers() { }

    template <typename... Args>
    void Event<Args...>::operator()(Args... args)
    {
        for (auto& handler : m_handlers)
        {
            handler(args...);
        }
    }

    template <typename... Args>
    void Event<Args...>::operator+=(std::function<void(Args...)> handler)
    {
        m_handlers.push_back(handler);
    }

    template <typename... Args>
    void Event<Args...>::operator-=(std::function<void(Args...)> handler)
    {
        m_handlers.erase(std::remove(m_handlers.begin(), m_handlers.end(), handler), m_handlers.end());
    }
}