#pragma once

#include "math/math.hpp"

namespace TeaMath
{
    TEAMATH_FN_DECL T sin  (const T theta);
    TEAMATH_FN_DECL T cos  (const T theta);
    TEAMATH_FN_DECL T tan  (const T theta);

    TEAMATH_FN_DECL T asin (const T theta);
    TEAMATH_FN_DECL T acos (const T theta);
    TEAMATH_FN_DECL T atan (const T theta);

    TEAMATH_FN_DECL T sinh (const T theta);
    TEAMATH_FN_DECL T cosh (const T theta);
    TEAMATH_FN_DECL T tanh (const T theta);

    TEAMATH_FN_DECL T asinh(const T theta);
    TEAMATH_FN_DECL T acosh(const T theta);
    TEAMATH_FN_DECL T atanh(const T theta);

    TEAMATH_FN_DECL T atan2(const T y, const T x);
}