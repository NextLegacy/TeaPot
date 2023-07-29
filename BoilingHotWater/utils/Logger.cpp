#include "Logger.hpp"

namespace BHW
{
    Logger::Logger(std::function<void(const LogMessage&)> logFunction) 
        : m_logFunction(logFunction), m_messages() { }
    
    void Logger::Log(const std::string& message, const std::string& level)
    {
        LogMessage logMessage = { std::chrono::system_clock::now(), level, message };
        
        Log(logMessage);
    }

    void Logger::Log(const LogMessage& message)
    {
        m_messages.push_back(message);
        
        m_logFunction(message);
    }

    void Logger::DumpAll()
    {
        for (const LogMessage& message : m_messages)
        {
            m_logFunction(message);
        }
    }

    void Logger::Clear()
    {
        m_messages.clear();
    }
}