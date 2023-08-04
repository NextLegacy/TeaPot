#include "vector/vec4.hpp"

namespace TeaMath
{
    template <typename T> vec<4, T>::vec(                    ) : x(0    ), y(0    ), z(0    ), w(0    ) { }
    template <typename T> vec<4, T>::vec(const vec<4, T>& vec) : x(vec.x), y(vec.y), z(vec.z), w(vec.w) { }
    template <typename T> vec<4, T>::vec(vec<4, T>&& vec     ) : x(vec.x), y(vec.y), z(vec.z), w(vec.w) { }
    template <typename T> vec<4, T>::vec(T x, T y, T z, T w  ) : x(x    ), y(y    ), z(z    ), w(w    ) { }

    template <typename T> vec<4, T>::~vec() { }

    template <typename T> vec<4, T>& vec<4, T>::operator=(const vec<4, T>&  vec) { x = vec.x; y = vec.y; z = vec.z; w = vec.w; return *this; }
    template <typename T> vec<4, T>& vec<4, T>::operator=(      vec<4, T>&& vec) { x = vec.x; y = vec.y; z = vec.z; w = vec.w; return *this; }

    template <typename T> vec<4, T>& vec<4, T>::operator++() { ++x; ++y; ++z; ++w; return *this; }
    template <typename T> vec<4, T>& vec<4, T>::operator--() { --x; --y; --z; --w; return *this; }

    template <typename T> vec<4, T> vec<4, T>::operator++(int) { vec<4, T> temp = *this; ++x; ++y; ++z; ++w; return temp; }
    template <typename T> vec<4, T> vec<4, T>::operator--(int) { vec<4, T> temp = *this; --x; --y; --z; --w; return temp; }

    template <typename T> vec<4, T>& vec<4, T>::operator+=(const vec<4, T>& vec) { x += vec.x; y += vec.y; z += vec.z; w += vec.w; return *this; }
    template <typename T> vec<4, T>& vec<4, T>::operator-=(const vec<4, T>& vec) { x -= vec.x; y -= vec.y; z -= vec.z; w -= vec.w; return *this; }
    template <typename T> vec<4, T>& vec<4, T>::operator*=(const vec<4, T>& vec) { x *= vec.x; y *= vec.y; z *= vec.z; w *= vec.w; return *this; }
    template <typename T> vec<4, T>& vec<4, T>::operator/=(const vec<4, T>& vec) { x /= vec.x; y /= vec.y; z /= vec.z; w /= vec.w; return *this; }

    template <typename T> vec<4, T>& vec<4, T>::operator+=(T num) { x += num; y += num; z += num; w += num; return *this; }
    template <typename T> vec<4, T>& vec<4, T>::operator-=(T num) { x -= num; y -= num; z -= num; w -= num; return *this; }
    template <typename T> vec<4, T>& vec<4, T>::operator*=(T num) { x *= num; y *= num; z *= num; w *= num; return *this; }
    template <typename T> vec<4, T>& vec<4, T>::operator/=(T num) { x /= num; y /= num; z /= num; w /= num; return *this; }

    template <typename T> vec<4, T> vec<4, T>::operator+(const vec<4, T>& vec) const { return vec<4, T>(x + vec.x, y + vec.y, z + vec.z, w + vec.w); }
    template <typename T> vec<4, T> vec<4, T>::operator-(const vec<4, T>& vec) const { return vec<4, T>(x - vec.x, y - vec.y, z - vec.z, w - vec.w); }
    template <typename T> vec<4, T> vec<4, T>::operator*(const vec<4, T>& vec) const { return vec<4, T>(x * vec.x, y * vec.y, z * vec.z, w * vec.w); }
    template <typename T> vec<4, T> vec<4, T>::operator/(const vec<4, T>& vec) const { return vec<4, T>(x / vec.x, y / vec.y, z / vec.z, w / vec.w); }
    
    template <typename T> vec<4, T> vec<4, T>::operator+(T num) const { return vec<4, T>(x + num, y + num, z + num, w + num); }
    template <typename T> vec<4, T> vec<4, T>::operator-(T num) const { return vec<4, T>(x - num, y - num, z - num, w - num); }
    template <typename T> vec<4, T> vec<4, T>::operator*(T num) const { return vec<4, T>(x * num, y * num, z * num, w * num); }
    template <typename T> vec<4, T> vec<4, T>::operator/(T num) const { return vec<4, T>(x / num, y / num, z / num, w / num); }
    
    template <typename T> bool vec<4, T>::operator==(const vec<4, T>& vec) const { return x == vec.x && y == vec.y && z == vec.z && w == vec.w; }
    template <typename T> bool vec<4, T>::operator!=(const vec<4, T>& vec) const { return x != vec.x && y != vec.y && z != vec.z && w != vec.w; }
    
    template <typename T>       T& vec<4, T>::operator[](size_t index)       { return (&x, &y, &z, &w)[index]; }
    template <typename T> const T& vec<4, T>::operator[](size_t index) const { return (&x, &y, &z, &w)[index]; }

    template <typename T> 
    
        template <typename T> inline T length() const { return sqrt(x * x + y * y + z * z + w * w); }
    inline vec<4, T> normalize() const { return *this / length(); }
    
    
        template <typename T> // toString
    
        template <typename T> inline vec<4, T> inverse  () { return *this * -1;       }
    inline vec<4, T> normalize() { return *this / length(); }

    template <typename T> 
    
        template <typename T> inline vec<4, T> inverted        () { return *this * -1;                   }
    
        template <typename T> inline vec<4, T> normalized      () { return *this / magnitude();          }
    
        template <typename T> inline T         magnitudeSquared() { return dot(this);                    }
    
        template <typename T> inline T         magnitude       () { return Math.sqrt(magnitudeSquared());}
    inline vec<4, T> angle           () { return Math.atan2(y, x);         }

    template <typename T> 
    inline T angleTo(vec<4, T> vec) 
    { 
        T m = magnitude() * vec.magnitude();
        T a = magnitudeSquared();

        if (m != 0) a /= m;
        
        return Math.acos(a);
    }

    template <typename T> 
    inline T dot(vec<4, T> vec) { return x * vec.x + y * vec.y + z * vec.z; }

    template <typename T> 
    inline vec<4, T> cross(vec<4, T> v) 
    { 
        return new vec<4, T>(
            y * v.z - z * v.y,
            z * v.x - x * v.z,
            x * v.y - y * v.x
        );
    }

    template <typename T> 
    inline T distanceTo(vec<4, T> vec) { return directionTo(vec).magnitude(); }

    template <typename T> 
    inline vec<4, T> directionTo(vec<4, T> vec) { return (vec - this); }

    template <typename T> inline bool XisInRange(T min, T max) { return x >= min && x <= max; }
    
    template <typename T> inline bool YisInRange(T min, T max) { return y >= min && y <= max; }
    
    template <typename T> inline bool ZisInRange(T min, T max) { return z >= min && z <= max; }
    template <typename T> inline bool isInRange(vec<4, T> from, vec<4, T>4 to) { return XisInRange(from.x, to.x) && YisInRange(from.y, to.y) && ZisInRange(from.z, to.z); }

    
    template <typename T> inline bool XisInBounds(T n, T length) { T halfLength = length / 2; return XisInRange(n - halfLength, n + halfLength); }

    template <typename T> inline bool YisInBounds(T n, T length) { T halfLength = length / 2; return YisInRange(n - halfLength, n + halfLength); }

    template <typename T> inline bool ZisInBounds(T n, T length) { T halfLength = length / 2; return ZisInRange(n - halfLength, n + halfLength); }
    
    template <typename T> inline bool isInBounds(vec<4, T> position, vec<4, T>4 size) { return XisInBounds(position.x, size.x) && YisInBounds(position.y, size.y) && ZisInBounds(position.z, size.z); }

    template <typename T> 
    
    template <typename T> inline bool isOutOfRange(vec<4, T> from, vec<4, T>4 to) { return !isInRange(from, to); }
    template <typename T> inline bool isOutOfBounds(vec<4, T> position, vec<4, T>4 size) { return !isInBounds(position, size); }

    template <typename T> 
    inline vec<4, T> clamp(vec<4, T> min, vec<4, T>4 max)
    {
        return new vec<4, T>
        (
            MathUtils.clamp(x, min.x, max.x),
            MathUtils.clamp(y, min.y, max.y),
            MathUtils.clamp(z, min.z, max.z)
        );
    }

    template <typename T> 
    inline vec<4, T> floor()
    {
        return new vec<4, T>
        (
            MathUtils.floor(x),
            MathUtils.floor(y),
            MathUtils.floor(z)
        );
    }

    template <typename T> 
    inline vec<4, T> round()
    {
        return new vec<4, T>
        (
            MathUtils.round(x),
            MathUtils.round(y),
            MathUtils.round(z)
        );
    }

    template <typename T> 
    inline vec<4, T> abs()
    {
        return new vec<4, T>
        (
            MathUtils.abs(x),
            MathUtils.abs(y),
            MathUtils.abs(z)
        );
    }

    template <typename T> 
    inline vec<4, T> lerpTo(vec<4, T> to, T t) 
    { 
        return new vec<4, T>
        (
            MathUtils.lerp(x, to.x, t),
            MathUtils.lerp(y, to.y, t),
            MathUtils.lerp(z, to.z, t)
        );
    }

    template <typename T> 
    inline static vec<4, T> lerp(vec<4, T> from, vec<4, T>4 to, T t) { return vec(from).lerpTo(to, t); }

    template <typename T> 
    inline static vec<4, T> fromAngle(T rotation)
    {
        return new vec<4, T>(Math.cos(rotation), Math.sin(rotation));
    }
}