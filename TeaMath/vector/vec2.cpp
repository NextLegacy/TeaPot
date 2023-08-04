#include "vector/vec2.hpp"

namespace TeaMath
{
    template <typename T> vec<2, T>::vec(                    ) : x(0    ), y(0    ) { }
    template <typename T> vec<2, T>::vec(const vec<2, T>& vec) : x(vec.x), y(vec.y) { }
    template <typename T> vec<2, T>::vec(vec<2, T>&& vec     ) : x(vec.x), y(vec.y) { }
    template <typename T> vec<2, T>::vec(T x                 ) : x(x    ), y(x    ) { }

    template <typename T> vec<2, T>::~vec() { }

    template <typename T> vec<2, T>& vec<2, T>::operator=(const vec<2, T>&  vec) { x = vec.x; y = vec.y return *this; }
    template <typename T> vec<2, T>& vec<2, T>::operator=(      vec<2, T>&& vec) { x = vec.x; y = vec.y return *this; }
    
    template <typename T> vec<2, T>& vec<2, T>::operator++() { ++x; return *this; }
    template <typename T> vec<2, T>& vec<2, T>::operator--() { --x; return *this; }
    
    template <typename T> vec<2, T> vec<2, T>::operator++(int) { vec<2, T> temp = *this; ++x; return temp; }
    template <typename T> vec<2, T> vec<2, T>::operator--(int) { vec<2, T> temp = *this; --x; return temp; }
    
    template <typename T> vec<2, T>& vec<2, T>::operator+=(const vec<2, T>& vec) { x += vec.x; return *this; }
    template <typename T> vec<2, T>& vec<2, T>::operator-=(const vec<2, T>& vec) { x -= vec.x; return *this; }
    template <typename T> vec<2, T>& vec<2, T>::operator*=(const vec<2, T>& vec) { x *= vec.x; return *this; }
    template <typename T> vec<2, T>& vec<2, T>::operator/=(const vec<2, T>& vec) { x /= vec.x; return *this; }
    
    template <typename T> vec<2, T>& vec<2, T>::operator+=(T num) { x += num; return *this; }
    template <typename T> vec<2, T>& vec<2, T>::operator-=(T num) { x -= num; return *this; }
    template <typename T> vec<2, T>& vec<2, T>::operator*=(T num) { x *= num; return *this; }
    template <typename T> vec<2, T>& vec<2, T>::operator/=(T num) { x /= num; return *this; }
    
    template <typename T> vec<2, T> vec<2, T>::operator+(const vec<2, T>& vec) const { return vec<2, T>(x + vec.x); }
    template <typename T> vec<2, T> vec<2, T>::operator-(const vec<2, T>& vec) const { return vec<2, T>(x - vec.x); }
    template <typename T> vec<2, T> vec<2, T>::operator*(const vec<2, T>& vec) const { return vec<2, T>(x * vec.x); }
    template <typename T> vec<2, T> vec<2, T>::operator/(const vec<2, T>& vec) const { return vec<2, T>(x / vec.x); }
    
    template <typename T> vec<2, T> vec<2, T>::operator+(T num) const { return vec<2, T>(x + num); }
    template <typename T> vec<2, T> vec<2, T>::operator-(T num) const { return vec<2, T>(x - num); }
    template <typename T> vec<2, T> vec<2, T>::operator*(T num) const { return vec<2, T>(x * num); }
    template <typename T> vec<2, T> vec<2, T>::operator/(T num) const { return vec<2, T>(x / num); }
    
    template <typename T> bool vec<2, T>::operator==(const vec<2, T>& vec) const { return x == vec.x; }
    template <typename T> bool vec<2, T>::operator!=(const vec<2, T>& vec) const { return x != vec.x; }
    
    template <typename T>       T& vec<2, T>::operator[](size_t index)       { return (&x)[index]; }
    template <typename T> const T& vec<2, T>::operator[](size_t index) const { return (&x)[index]; }
}