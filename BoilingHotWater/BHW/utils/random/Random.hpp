#pragma once

#include <string>
#include "BHW/utils/Time.hpp"
#include "BHW/utils/random/PRNG.hpp"

namespace BHW
{
    class Splitmix64 : public Prng<uint64_t>
    {
    public:
        using Prng<uint64_t>::Prng;
        
        void InitState() override;

        uint64_t operator()() override;
    };

    class Xoshiro256ss : public Prng<uint64_t[4]>
    {
    public:
        using Prng<uint64_t[4]>::Prng;

        void InitState() override;

        uint64_t operator()() override;
    };

    typedef Xoshiro256ss Random;
}