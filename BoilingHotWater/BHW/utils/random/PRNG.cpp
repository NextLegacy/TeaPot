#include "BHW/utils/random/PRNG.hpp"

namespace BHW
{
    template <typename TState> Prng<TState>::Prng() : Prng(Seed()) { }

    template <typename TState> Prng<TState>::Prng(Seed seed) : m_seed(seed) { }

    template <typename TState> int64_t  Prng<TState>::Int64  () { return (int64_t ) UInt64 (   )                      ; }
    template <typename TState> int32_t  Prng<TState>::Int32  () { return (int32_t ) Int64  (   )                      ; }
    template <typename TState> int16_t  Prng<TState>::Int16  () { return (int16_t ) Int64  (   )                      ; }
    template <typename TState> int8_t   Prng<TState>::Int8   () { return (int8_t  ) Int64  (   )                      ; }
    template <typename TState> uint64_t Prng<TState>::UInt64 () { return (uint64_t) (*this)(   )                      ; }
    template <typename TState> uint32_t Prng<TState>::UInt32 () { return (uint32_t) UInt64 (   )                      ; }
    template <typename TState> uint16_t Prng<TState>::UInt16 () { return (uint16_t) UInt64 (   )                      ; }
    template <typename TState> uint8_t  Prng<TState>::UInt8  () { return (uint8_t ) UInt64 (   )                      ; }
    template <typename TState> double   Prng<TState>::Float64() { return (double  ) UInt64 (   ) / (double) UINT64_MAX; }
    template <typename TState> float    Prng<TState>::Float32() { return (float   ) Float64(   )                      ; }
    template <typename TState> bool     Prng<TState>::Bool   () { return (bool    ) Bool   (0.5)                      ; }

    template <typename TState> int64_t  Prng<TState>::Int64  (int64_t  bound) { return (int64_t ) Int64() % bound; } // TODO: I really don't like modulo
    template <typename TState> int32_t  Prng<TState>::Int32  (int32_t  bound) { return (int32_t ) Int64(bound); }
    template <typename TState> int16_t  Prng<TState>::Int16  (int16_t  bound) { return (int16_t ) Int64(bound); }
    template <typename TState> int8_t   Prng<TState>::Int8   (int8_t   bound) { return (int8_t  ) Int64(bound); }

    template <typename TState> uint64_t Prng<TState>::UInt64 (uint64_t bound) { return (uint64_t) UInt64() % bound; } // TODO: I really don't like modulo
    template <typename TState> uint32_t Prng<TState>::UInt32 (uint32_t bound) { return (uint32_t) UInt64(bound); }
    template <typename TState> uint16_t Prng<TState>::UInt16 (uint16_t bound) { return (uint16_t) UInt64(bound); }
    template <typename TState> uint8_t  Prng<TState>::UInt8  (uint8_t  bound) { return (uint8_t ) UInt64(bound); }

    template <typename TState> int64_t  Prng<TState>::Int64  (int64_t  min, int64_t  max) { return Int64 (max - min) + min; }
    template <typename TState> int32_t  Prng<TState>::Int32  (int32_t  min, int32_t  max) { return Int32 (max - min) + min; }
    template <typename TState> int16_t  Prng<TState>::Int16  (int16_t  min, int16_t  max) { return Int16 (max - min) + min; }
    template <typename TState> int8_t   Prng<TState>::Int8   (int8_t   min, int8_t   max) { return Int8  (max - min) + min; }
    template <typename TState> uint64_t Prng<TState>::UInt64 (uint64_t min, uint64_t max) { return UInt64(max - min) + min; }
    template <typename TState> uint32_t Prng<TState>::UInt32 (uint32_t min, uint32_t max) { return UInt32(max - min) + min; }
    template <typename TState> uint16_t Prng<TState>::UInt16 (uint16_t min, uint16_t max) { return UInt16(max - min) + min; }
    template <typename TState> uint8_t  Prng<TState>::UInt8  (uint8_t  min, uint8_t  max) { return UInt8 (max - min) + min; }

    template <typename TState> double   Prng<TState>::Float64(double   min, double   max) { return Float64() * (max - min) + min;}
    template <typename TState> float    Prng<TState>::Float32(float    min, float    max) { return Float32() * (max - min) + min;}

    template <typename TState> bool     Prng<TState>::Bool   (double probability) { return Float64() < probability;}

    template <typename TState> 
    std::string Prng<TState>::String(unsigned int length, const std::string& charset)
    {
        std::string result;

        for (unsigned int i = 0; i < length; i++)
        {
            result += charset[UInt64(0, charset.length() - 1)];
        }

        return result;
    }
}