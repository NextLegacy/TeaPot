#pragma once

#include <type_traits>

#include <string>
#include <functional>

#include <chrono>

#include <vector>

#include "BHW/utils/event/DynamicEventSystem.hpp"
#include "BHW/utils/logger/LoggedMessage.hpp"
#include "BHW/utils/logger/LoggerLevel.hpp"

namespace BHW
{
    struct LoggerBaseEvent
    {
        virtual void OnLog(LoggedMessage message) { }
    };

    class Logger : public DynamicEventSystem<LoggerBaseEvent>
    {
    public:
        Logger();

        void Log(std::string message, std::vector<LoggerLevel> levels = { });
        
        void DumpAll();

        void Clear();

    private:
        void Log(LoggedMessage message);

        std::vector<LoggedMessage> m_messages;
    };
}