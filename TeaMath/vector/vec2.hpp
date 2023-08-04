#pragma once

#include "vector/vec.hpp"

namespace TeaMath
{
    template <typename T>
    struct vec<2, T>
    {
    public:
        union { T x, r, s; };
        union { T y, g, t; };

        vec (                );
        vec (const vec<2, T>&);
        vec (vec<2, T>&&     );
        vec (T x             );
        ~vec(                );

        vec<2, T>& operator=(const vec<2, T>&);
        vec<2, T>& operator=(vec<2, T>&&     );

        vec<2, T>& operator++(   );
        vec<2, T>& operator--(   );
        vec<2, T>  operator++(int);
        vec<2, T>  operator--(int);

        vec<2, T>& operator+=(const vec<2, T>& vec);
        vec<2, T>& operator-=(const vec<2, T>& vec);
        vec<2, T>& operator*=(const vec<2, T>& vec);
        vec<2, T>& operator/=(const vec<2, T>& vec);

        vec<2, T>& operator+=(T num);
        vec<2, T>& operator-=(T num);
        vec<2, T>& operator*=(T num);
        vec<2, T>& operator/=(T num);

        vec<2, T> operator+(const vec<2, T>& vec) const;
        vec<2, T> operator-(const vec<2, T>& vec) const;
        vec<2, T> operator*(const vec<2, T>& vec) const;
        vec<2, T> operator/(const vec<2, T>& vec) const;

        vec<2, T> operator+(T num) const;
        vec<2, T> operator-(T num) const;
        vec<2, T> operator*(T num) const;
        vec<2, T> operator/(T num) const;

        bool operator==(const vec<2, T>& vec) const;
        bool operator!=(const vec<2, T>& vec) const;

              T& operator[](size_t index)      ;
        const T& operator[](size_t index) const;
    };
}