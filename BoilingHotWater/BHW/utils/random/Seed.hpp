#pragma once

#include <string>

#include "BHW/utils/Time.hpp"

#include "BHW/utils/random/PRNGAlgorithms.hpp"

namespace BHW
{
    class Seed
    {
    public:
        Seed();
        Seed(uint64_t seed);
        Seed(std::string seed);

        inline operator uint64_t() const { return m_seed; }
        inline operator std::string() const { return std::to_string(m_seed); }

        template <unsigned int N>
        void CreateState(uint64_t (&state)[N]) const;

    private:
        uint64_t m_seed;
    };
}