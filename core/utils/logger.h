#pragma once

#include "core.h"

namespace engine
{
    namespace logger
    {
        class Logger 
        {
        public:
            void        (*out)(std::string);
            std::string (*in )(std::string);
        };

        struct Message
        {

        };
    }
}