#pragma once

#include <type_traits>

#define TEAMATH_ARITHMETIC_TYPE(T) typename = std::enable_if_t<std::is_arithmetic_v<T>>

#define TEAMATH_TEMPLATE_ARITHMETIC_TYPE(T) template <typename T, TEAMATH_ARITHMETIC_TYPE(T)>

#define TEAMATH_FN_DECL template <typename T, TEAMATH_ARITHMETIC_TYPE(T)> constexpr

namespace TeaMath
{
    TEAMATH_FN_DECL T abs    (const T                  );
    TEAMATH_FN_DECL T sign   (const T                  );

    TEAMATH_FN_DECL T min    (const T, const T         );
    TEAMATH_FN_DECL T max    (const T, const T         );

    TEAMATH_FN_DECL T clamp  (const T, const T, const T);

    TEAMATH_FN_DECL T lerp   (const T, const T, const float);
    TEAMATH_FN_DECL T slerp  (const T, const T, const float);

    TEAMATH_FN_DECL T floor  (const T);
    TEAMATH_FN_DECL T round  (const T);
    TEAMATH_FN_DECL T ceil   (const T);

    TEAMATH_FN_DECL T sqrt   (const T);
    TEAMATH_FN_DECL T invSqrt(const T);

    TEAMATH_FN_DECL T pow    (const T, const T);
    TEAMATH_FN_DECL T log    (const T         );
    TEAMATH_FN_DECL T exp    (const T         );

    TEAMATH_FN_DECL T mod    (const T, const T);
}