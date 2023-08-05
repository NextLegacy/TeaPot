#pragma once

#include "vec<4, T>/vec.hpp"

namespace TeaMath
{
    template <typename T>
    struct vec<4, T>
    {
    public:
        union { T x, r, s, width ; };
        union { T y, g, t, height; };
        union { T z, b, p, depth ; };
        union { T w, a, q, time  ; };

        vec (                );
        vec (const vec<4, T>&);
        vec (vec<4, T>&&     );
        vec (T, T, T, T      );
        ~vec(                );

        // Operators

        vec<4, T>& operator=(const vec<4, T>&);
        vec<4, T>& operator=(vec<4, T>&&     );

        vec<4, T>& operator++(   );
        vec<4, T>& operator--(   );
        vec<4, T>  operator++(int);
        vec<4, T>  operator--(int);

        vec<4, T>& operator+=(const vec<4, T>&);
        vec<4, T>& operator-=(const vec<4, T>&);
        vec<4, T>& operator*=(const vec<4, T>&);
        vec<4, T>& operator/=(const vec<4, T>&);

        vec<4, T>& operator+=(T);
        vec<4, T>& operator-=(T);
        vec<4, T>& operator*=(T);
        vec<4, T>& operator/=(T);

        vec<4, T> operator+(const vec<4, T>&) const;
        vec<4, T> operator-(const vec<4, T>&) const;
        vec<4, T> operator*(const vec<4, T>&) const;
        vec<4, T> operator/(const vec<4, T>&) const;

        vec<4, T> operator+(T) const;
        vec<4, T> operator-(T) const;
        vec<4, T> operator*(T) const;
        vec<4, T> operator/(T) const;

        bool operator==(const vec<4, T>&) const;
        bool operator!=(const vec<4, T>&) const;

              T& operator[](size_t)      ;
        const T& operator[](size_t) const;

        // Conversions

        template <typename U>                                                     inline operator vec<1, U>() const { return vec<1, U>(x         ); }
        template <typename U>                                                     inline operator vec<2, U>() const { return vec<2, U>(x, y      ); }
        template <typename U>                                                     inline operator vec<3, U>() const { return vec<3, U>(x, y, z   ); }
        template <typename U, typename = std::enable_if_t<!std::is_same_v<T, U>>> inline operator vec<4, U>() const { return vec<4, U>(x, y, z, w); }

        // Functions

        inline T length() const { return sqrt(x * x + y * y + z * z + w * w); }
        inline vec<4, T> normalize() const { return *this / length(); }
        
        // toString
        inline vec<4, T> inverse  () { return *this * -1;       }
        inline vec<4, T> normalize() { return *this / length(); }

        inline vec<4, T> inverted        () { return *this * -1;                   }
        inline vec<4, T> normalized      () { return *this / magnitude();          }
        inline T         magnitudeSquared() { return dot(this);                    }
        inline T         magnitude       () { return Math.sqrt(magnitudeSquared());}
        inline vec<4, T> angle           () { return Math.atan2(y, x);         }

        inline T angleTo(vec<4, T> vec) 
        { 
            T m = magnitude() * vec.magnitude();
            T a = magnitudeSquared();

            if (m != 0) a /= m;
            
            return Math.acos(a);
        }

        inline T dot(vec<4, T> vec) { return x * vec.x + y * vec.y + z * vec.z; }

        inline vec<4, T> cross(vec<4, T> v) 
        { 
            return new vec<4, T>(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
            );
        }

        inline T distanceTo(vec<4, T> vec) { return (vec - this).magnitude(); }

        inline vec<4, T> directionTo(vec<4, T> vec)
        {
            return (vec - this).normalized();
        }

        inline bool XisInRange(T min, T max) { return x >= min && x <= max; }
        inline bool YisInRange(T min, T max) { return y >= min && y <= max; }
        inline bool ZisInRange(T min, T max) { return z >= min && z <= max; }
        inline bool isInRange(vec<4, T> from, vec<4, T>4 to) { return XisInRange(from.x, to.x) && YisInRange(from.y, to.y) && ZisInRange(from.z, to.z); }

        inline bool XisInBounds(T n, T length) { T halfLength = length / 2; return XisInRange(n - halfLength, n + halfLength); }
        inline bool YisInBounds(T n, T length) { T halfLength = length / 2; return YisInRange(n - halfLength, n + halfLength); }
        inline bool ZisInBounds(T n, T length) { T halfLength = length / 2; return ZisInRange(n - halfLength, n + halfLength); }
        inline bool isInBounds(vec<4, T> position, vec<4, T>4 size) { return XisInBounds(position.x, size.x) && YisInBounds(position.y, size.y) && ZisInBounds(position.z, size.z); }

        inline bool isOutOfRange(vec<4, T> from, vec<4, T>4 to) { return !isInRange(from, to); }
        inline bool isOutOfBounds(vec<4, T> position, vec<4, T>4 size) { return !isInBounds(position, size); }

        inline vec<4, T> clamp(vec<4, T> min, vec<4, T>4 max)
        {
            return new vec<4, T>
            (
                MathUtils.clamp(x, min.x, max.x),
                MathUtils.clamp(y, min.y, max.y),
                MathUtils.clamp(z, min.z, max.z)
            );
        }

        inline vec<4, T> floor()
        {
            return new vec<4, T>
            (
                MathUtils.floor(x),
                MathUtils.floor(y),
                MathUtils.floor(z)
            );
        }

        inline vec<4, T> round()
        {
            return new vec<4, T>
            (
                MathUtils.round(x),
                MathUtils.round(y),
                MathUtils.round(z)
            );
        }

        inline vec<4, T> abs()
        {
            return new vec<4, T>
            (
                MathUtils.abs(x),
                MathUtils.abs(y),
                MathUtils.abs(z)
            );
        }

        inline vec<4, T> lerpTo(vec<4, T> to, T t) 
        { 
            return new vec<4, T>
            (
                MathUtils.lerp(x, to.x, t),
                MathUtils.lerp(y, to.y, t),
                MathUtils.lerp(z, to.z, t)
            );
        }

        inline static vec<4, T> lerp(vec<4, T> from, vec<4, T>4 to, T t) { return vec(from).lerpTo(to, t); }

        inline static vec<4, T> fromAngle(T rotation)
        {
            return new vec<4, T>(Math.cos(rotation), Math.sin(rotation));
        }

        // Constants

        static const vec<4, T> zero;
        static const vec<4, T> one;

        static const vec<4, T> left;
        static const vec<4, T> right;
        static const vec<4, T> up;
        static const vec<4, T> down;
        static const vec<4, T> forward;
        static const vec<4, T> backward;
    };
}