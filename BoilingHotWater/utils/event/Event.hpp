#pragma once

#include <vector>
#include <functional>

namespace BHC
{
    template <typename... Args>
    class Event
    {
    public:
        Event();

        void operator()(Args... args);

        void operator+=(std::function<void(Args...)> handler);

        void operator-=(std::function<void(Args...)> handler);

    private:
        std::vector<std::function<void(Args...)>> m_handlers;
    };
}