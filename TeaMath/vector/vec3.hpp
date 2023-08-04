#pragma once

#include "vector/vec.hpp"

namespace TeaMath
{
    template <typename T>
    struct vec<3, T>
    {
    public:
        union { T x, r, s; };
        union { T y, g, t; };
        union { T z, b, p; };

        vec (                );
        vec (const vec<3, T>&);
        vec (vec<3, T>&&     );
        vec (T x             );
        ~vec(                );

        vec<3, T>& operator=(const vec<3, T>&);
        vec<3, T>& operator=(vec<3, T>&&     );

        vec<3, T>& operator++(   );
        vec<3, T>& operator--(   );
        vec<3, T>  operator++(int);
        vec<3, T>  operator--(int);

        vec<3, T>& operator+=(const vec<3, T>& vec);
        vec<3, T>& operator-=(const vec<3, T>& vec);
        vec<3, T>& operator*=(const vec<3, T>& vec);
        vec<3, T>& operator/=(const vec<3, T>& vec);

        vec<3, T>& operator+=(T num);
        vec<3, T>& operator-=(T num);
        vec<3, T>& operator*=(T num);
        vec<3, T>& operator/=(T num);

        vec<3, T> operator+(const vec<3, T>& vec) const;
        vec<3, T> operator-(const vec<3, T>& vec) const;
        vec<3, T> operator*(const vec<3, T>& vec) const;
        vec<3, T> operator/(const vec<3, T>& vec) const;

        vec<3, T> operator+(T num) const;
        vec<3, T> operator-(T num) const;
        vec<3, T> operator*(T num) const;
        vec<3, T> operator/(T num) const;

        bool operator==(const vec<3, T>& vec) const;
        bool operator!=(const vec<3, T>& vec) const;

              T& operator[](size_t index)      ;
        const T& operator[](size_t index) const;
    };
}