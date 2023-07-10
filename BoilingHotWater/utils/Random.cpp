#include "random.hpp"

namespace BHW
{
    Seed::Seed(uint64_t seed) : m_seed(seed)
    {
    }

    Seed::Seed() : Seed(Time::Now())
    {
    }

    Seed::Seed(std::string seed) : Seed(std::hash<std::string>{}(seed))
    {
    }

    Seed::Seed(const char* seed) : Seed(std::string(seed))
    {
    }

    Seed::operator uint64_t() const
    {
        return m_seed;
    }

    Seed::operator std::string() const
    {
        return std::to_string(m_seed);
    }

    Seed::operator const char*() const
    {
        return std::to_string(m_seed).c_str();
    }

    template <unsigned int N>
    void Seed::CreateState(uint64_t (&state)[N]) const
    {
        uint64_t seed = m_seed;

        for (unsigned int i = 1; i < N; i++)
        {
            state[i] = PrngAlgorithms::Splitmix64(seed);
        }
    }

    Prng::Prng() : Prng(Seed())
    {
    }

    Prng::Prng(Seed seed) : m_seed(seed)
    {
    }

    void Prng::SetSeed(Seed seed)
    {
        m_seed = seed;
    }

    int64_t Prng::Int64() 
    { 
        return (int64_t) UInt64();
    }

    int64_t Prng::Int64(int64_t bound) 
    {
        return Int64() % bound; // TODO: I really don't like modulo bias
    }

    int64_t Prng::Int64(int64_t min, int64_t max) 
    {
        return Int64(max - min) + min;
    }


    int32_t Prng::Int32() 
    {
        return (int32_t) Int64();
    }

    int32_t Prng::Int32(int32_t bound) 
    {
        return (int32_t) Int64(bound);
    }

    int32_t Prng::Int32(int32_t min, int32_t max) 
    {
        return Int32(max - min) + min;
    }

    
    int16_t Prng::Int16() 
    {
        return (int16_t) Int64();
    }

    int16_t Prng::Int16(int16_t bound) 
    {
        return (int16_t) Int64(bound);
    }

    int16_t Prng::Int16(int16_t min, int16_t max) 
    {
        return Int16(max - min) + min;
    }


    int8_t Prng::Int8() 
    {
        return (int8_t) Int64();
    }

    int8_t Prng::Int8(int8_t bound) 
    {
        return (int8_t) Int64(bound);
    }

    int8_t Prng::Int8(int8_t min, int8_t max) 
    {
        return Int8(max - min) + min;
    }


    uint64_t Prng::UInt64() 
    { 
        return (*this)(); 
    }

    uint64_t Prng::UInt64(uint64_t bound) 
    {
        return (*this)() % bound; // TODO: I really don't like modulo bias
    }

    uint64_t Prng::UInt64(uint64_t min, uint64_t max) 
    {
        return UInt64(max - min) + min;
    }


    uint32_t Prng::UInt32() 
    {
        return (uint32_t) UInt64();
    }

    uint32_t Prng::UInt32(uint32_t bound) 
    {
        return (uint32_t) UInt64(bound);
    }

    uint32_t Prng::UInt32(uint32_t min, uint32_t max) 
    {
        return UInt32(max - min) + min;
    }


    uint16_t Prng::UInt16() 
    {
        return (uint16_t) UInt64();
    }

    uint16_t Prng::UInt16(uint16_t bound) 
    {
        return (uint16_t) UInt64(bound);
    }

    uint16_t Prng::UInt16(uint16_t min, uint16_t max) 
    {
        return UInt16(max - min) + min;
    }


    uint8_t Prng::UInt8() 
    {
        return (uint8_t) UInt64();
    }

    uint8_t Prng::UInt8(uint8_t bound) 
    {
        return (uint8_t) UInt64(bound);
    }

    uint8_t Prng::UInt8(uint8_t min, uint8_t max) 
    {
        return UInt8(max - min) + min;
    }

    double Prng::Float64() 
    {
        return (double) UInt64() / (double) UINT64_MAX;
    }

    double Prng::Float64(double min, double max) 
    {
        return Float64() * (max - min) + min;
    }

    float Prng::Float32() 
    {
        return (float) Float64();
    }

    float Prng::Float32(float min, float max) 
    {
        return Float32() * (max - min) + min;
    }


    bool Prng::Bool()
    {
        return Bool(0.5);
    }

    bool Prng::Bool(double probability) 
    { 
        return Float64() < probability;
    }


    std::string Prng::String(unsigned int length) 
    { 
        return String(length, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"); 
    }

    std::string Prng::String(unsigned int length, const std::string& charset)
    {
        std::string result;

        for (unsigned int i = 0; i < length; i++)
        {
            result += charset[UInt64(0, charset.length() - 1)];
        }

        return result;
    }

    namespace PrngAlgorithms
    {
        uint64_t Splitmix64(uint64_t &state)
        {
            state += 0x9E3779B97F4A7C15UL;

            uint64_t random = state;

            random = (random ^ (random >> 30)) * 0xBF58476D1CE4E5B9UL;
            random = (random ^ (random >> 27)) * 0x94D049BB133111EBUL;

            return random ^ (random >> 31);
        }

        uint64_t Xoshiro256ss(uint64_t (&state)[4])
        {
            uint64_t* s = state;
            uint64_t const result = s[0] + s[3];

            uint64_t const t = s[1] << 17;

            s[2] ^= s[0];
            s[3] ^= s[1];
            s[1] ^= s[2];
            s[0] ^= s[3];

            s[2] ^= t;

            s[3] = (s[3] << 45) | (s[3] >> (64 - 45)); // rotl(s[3], 45);

            return result;
        }
    }

    Splitmix64::Splitmix64() : Splitmix64(Seed())
    {
    }

    Splitmix64::Splitmix64(Seed seed) : Prng(seed)
    {
        m_state = seed;
    }

    void Splitmix64::SetSeed(Seed seed)
    {
        Prng::SetSeed(seed);
        m_state = seed;
    }

    uint64_t Splitmix64::operator()()
    {
        return PrngAlgorithms::Splitmix64(m_state);
    }

    Xoshiro256ss::Xoshiro256ss() : Xoshiro256ss(Seed())
    {
    }

    Xoshiro256ss::Xoshiro256ss(Seed seed) : Prng(seed)
    {
        seed.CreateState(m_state);
    }

    void Xoshiro256ss::SetSeed(Seed seed)
    {
        Prng::SetSeed(seed);
        seed.CreateState(m_state);
    }

    uint64_t Xoshiro256ss::operator()()
    {
        return PrngAlgorithms::Xoshiro256ss(m_state);
    }
}
