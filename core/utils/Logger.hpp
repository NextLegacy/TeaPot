#pragma once

#include "Engine"

namespace Engine
{
    namespace Logger
    {
        class Logger
        {
        private:
            std::function<void(const std::string&)> m_logFunction;
        public:
            Logger(std::function<void(const std::string&)> logFunction);

            void Log(const std::string& message);
        };
    }
}