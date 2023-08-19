#pragma once

#include <cstdint>

namespace BHW
{
    namespace PRNGAlgorithms
    {
        extern uint64_t Splitmix64  (uint64_t  &state    );
        extern uint64_t Xoshiro256ss(uint64_t (&state)[4]);
    }
}