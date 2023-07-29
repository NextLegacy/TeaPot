#include "time.hpp"

namespace BHW
{
    namespace Time
    {
        uint64_t Now()
        {
            return std::chrono::duration_cast<std::chrono::nanoseconds>
            (
                std::chrono::system_clock::now().time_since_epoch()
            ).count();
        }
    }
}