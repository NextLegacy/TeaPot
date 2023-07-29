#pragma once

#include <string>
#include "utils/Time.hpp"

namespace BHW
{
    class Seed
    {
    public:
        Seed(uint64_t seed);
        Seed();
        Seed(std::string seed);
        Seed(const char* seed);

        operator uint64_t() const;
        operator std::string() const;
        operator const char*() const;

        template <unsigned int N>
        void CreateState(uint64_t (&state)[N]) const;

    private:
        uint64_t m_seed;
    };

    class Prng
    {
    public:
        Prng();
        Prng(Seed seed);

        void SetSeed(Seed seed);

        int64_t Int64();
        int64_t Int64(int64_t bound);
        int64_t Int64(int64_t min, int64_t max);

        int32_t Int32();
        int32_t Int32(int32_t bound);
        int32_t Int32(int32_t min, int32_t max);

        int16_t Int16();
        int16_t Int16(int16_t bound);
        int16_t Int16(int16_t min, int16_t max);

        int8_t Int8();
        int8_t Int8(int8_t bound);
        int8_t Int8(int8_t min, int8_t max);

        uint64_t UInt64();
        uint64_t UInt64(uint64_t bound);
        uint64_t UInt64(uint64_t min, uint64_t max);

        uint32_t UInt32();
        uint32_t UInt32(uint32_t bound);
        uint32_t UInt32(uint32_t min, uint32_t max);
        
        uint16_t UInt16();
        uint16_t UInt16(uint16_t bound);
        uint16_t UInt16(uint16_t min, uint16_t max);
        
        uint8_t UInt8();
        uint8_t UInt8(uint8_t bound);
        uint8_t UInt8(uint8_t min, uint8_t max);

        float Float32();
        float Float32(float min, float max);
        
        double Float64();
        double Float64(double min, double max);

        bool Bool();
        bool Bool(double probability);

        std::string String(unsigned int length);
        std::string String(unsigned int length, const std::string& charset);

        virtual uint64_t operator()() = 0;

    protected:
        Seed m_seed;
    };

    namespace PrngAlgorithms
    {
        uint64_t Splitmix64  (uint64_t  &state    );
        uint64_t Xoshiro256ss(uint64_t (&state)[4]);
    }

    class Splitmix64 : public Prng
    {
    public:
        Splitmix64();
        Splitmix64(Seed seed);
        
        void SetSeed(Seed seed);
        uint64_t operator()() override;
    
    private:
        uint64_t m_state;
    };

    class Xoshiro256ss : public Prng
    {
    public:
        Xoshiro256ss();
        Xoshiro256ss(Seed seed);
        
        void SetSeed(Seed seed);
        uint64_t operator()() override;
    
    private:
        uint64_t m_state[4];
    };

    class Random : public Xoshiro256ss { };
}