#pragma once

#include "vector/vec.hpp"

namespace TeaMath
{
    template <typename T>
    struct vec<1, T>
    {
    public:
        union { T x, r, s; };

        vec (                );
        vec (const vec<1, T>&);
        vec (vec<1, T>&&     );
        vec (T x             );
        ~vec(                );

        vec<1, T>& operator=(const vec<1, T>&);
        vec<1, T>& operator=(vec<1, T>&&     );

        vec<1, T>& operator++(   );
        vec<1, T>& operator--(   );
        vec<1, T>  operator++(int);
        vec<1, T>  operator--(int);

        vec<1, T>& operator+=(const vec<1, T>& vec);
        vec<1, T>& operator-=(const vec<1, T>& vec);
        vec<1, T>& operator*=(const vec<1, T>& vec);
        vec<1, T>& operator/=(const vec<1, T>& vec);

        vec<1, T>& operator+=(T num);
        vec<1, T>& operator-=(T num);
        vec<1, T>& operator*=(T num);
        vec<1, T>& operator/=(T num);

        vec<1, T> operator+(const vec<1, T>& vec) const;
        vec<1, T> operator-(const vec<1, T>& vec) const;
        vec<1, T> operator*(const vec<1, T>& vec) const;
        vec<1, T> operator/(const vec<1, T>& vec) const;

        vec<1, T> operator+(T num) const;
        vec<1, T> operator-(T num) const;
        vec<1, T> operator*(T num) const;
        vec<1, T> operator/(T num) const;

        bool operator==(const vec<1, T>& vec) const;
        bool operator!=(const vec<1, T>& vec) const;

              T& operator[](size_t index)      ;
        const T& operator[](size_t index) const;
    };
}