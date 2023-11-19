#include "BHW/utils/Hash.hpp"

namespace BHW
{
    constexpr unsigned long long Hash(const char* str)
    {
        unsigned long long hash = 0xcbf29ce484222325ULL;
        while (*str)
        {
            hash ^= *str;
            hash *= 0x100000001b3ULL;
            ++str;
        }
        return hash;
    }

    constexpr unsigned long long Hash(const char* str, unsigned long long length)
    {
        unsigned long long hash = 0xcbf29ce484222325ULL;
        for (unsigned long long i = 0; i < length; ++i)
        {
            hash ^= str[i];
            hash *= 0x100000001b3ULL;
        }
        return hash;
    }
}