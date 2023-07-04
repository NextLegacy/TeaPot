#include "time.hpp"

namespace Engine
{
    namespace Time
    {
        uint64_t Now()
        {
            return std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::system_clock::now().time_since_epoch()).count();
        }
    }
}