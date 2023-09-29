#pragma once

#include <string>

namespace BHW
{
    template<typename... TArgs>
    void Assert(bool condition, std::string format, TArgs... messages);
}

#define BHW_STATIC_ASSERT(condition, origin, format, ...) static_assert(condition, std::format("[STATIC ASSERT FAILED] [{}:{}] [{}]" + format, __FILE__, __LINE__, origin, __VA_ARGS__))
#define BHW_ASSERT(condition, origin, format, ...) BHW::Assert(condition, std::string("[ASSERT FAILED] [{}:{}] [{}] ") + format, __FILE__, __LINE__, origin, __VA_ARGS__)