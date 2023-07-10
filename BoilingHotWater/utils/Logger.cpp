#include "Logger.hpp"

namespace BHW
{
    namespace Logger
    {
        Logger::Logger(std::function<void(const std::string&)> logFunction)
            : m_logFunction(logFunction)
        {
        }
        
        void Logger::Log(const std::string& message)
        {
            m_logFunction(message);
        }
    }
}