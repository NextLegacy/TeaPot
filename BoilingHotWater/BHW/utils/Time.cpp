#include "BHW/utils/time.hpp"

namespace BHW
{
    namespace Time
    {
        uint64_t NanoSeconds()
        {
            return std::chrono::duration_cast<std::chrono::nanoseconds>
            (
                std::chrono::system_clock::now().time_since_epoch()
            ).count();
        }
    }
}