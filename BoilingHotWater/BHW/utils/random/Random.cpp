#include "BHW/utils/random/Random.hpp"

namespace BHW
{
    void Splitmix64::InitState()
    {
        m_state = m_seed;
    }

    uint64_t Splitmix64::operator()()
    {
        return PRNGAlgorithms::Splitmix64(m_state);
    }

    void Xoshiro256ss::InitState()
    {
        m_seed.CreateState(m_state);
    }

    uint64_t Xoshiro256ss::operator()()
    {
        return PRNGAlgorithms::Xoshiro256ss(m_state);
    }
}
