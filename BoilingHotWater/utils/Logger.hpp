#pragma once

#include <string>
#include <functional>

#include <chrono>

#include <vector>

namespace BHW
{
    const struct LogMessage
    {
        const std::chrono::system_clock::time_point timestamp;

        const std::string level;

        const std::string message;
    };

    class Logger
    {
    public:
        Logger(std::function<void(const LogMessage&)> logFunction);

        void Log(const std::string& message, const std::string& level = "INFO");

        void DumpAll();

        void Clear();

    private:
        void Log(const LogMessage& message);

        std::function<void(const LogMessage&)> m_logFunction;

        std::vector<LogMessage> m_messages;
    };
}