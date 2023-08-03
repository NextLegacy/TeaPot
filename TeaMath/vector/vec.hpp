#pragma once

#pragma once

#include <type_traits>
#include <cstddef>

namespace TeaMath
{
    template <size_t N, typename T, typename = std::enable_if_t<std::is_arithmetic_v<T>>>
    struct vec;
}