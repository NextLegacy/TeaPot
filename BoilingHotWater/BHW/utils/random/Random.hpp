#pragma once

#include <string>
#include "BHW/utils/Time.hpp"
#include "BHW/utils/random/PRNG.hpp"

namespace BHW
{
    class Splitmix64 : public Prng<uint64_t>
    {
    public:
        Splitmix64() : Prng() { InitState(); }
        Splitmix64(Seed seed) : Prng(seed) { InitState(); }
        
        void InitState() override;

        uint64_t operator()() override;
    };

    class Xoshiro256ss : public Prng<uint64_t[4]>
    {
    public:
        Xoshiro256ss() : Prng() { InitState(); }
        Xoshiro256ss(Seed seed) : Prng(seed) { InitState(); }
        
        void InitState() override;

        uint64_t operator()() override;
    };

    typedef Xoshiro256ss Random;
}