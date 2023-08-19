#include "BHW/utils/random/Seed.hpp"

namespace BHW
{
    Seed::Seed() : Seed(Time::NanoSeconds()) { }

    Seed::Seed(uint64_t seed) : m_seed(seed) { }

    Seed::Seed(std::string seed) : Seed(std::hash<std::string>{ }(seed)) { }

    template <unsigned int N>
    void Seed::CreateState(uint64_t (&state)[N]) const
    {
        uint64_t seed = m_seed;

        for (unsigned int i = 1; i < N; i++)
        {
            state[i] = PRNGAlgorithms::Splitmix64(seed);
        }
    }
}