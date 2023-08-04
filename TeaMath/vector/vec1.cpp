#include "vector/vec1.hpp"

namespace TeaMath
{
    template <typename T> vec<1, T>::vec(                    ) : x(0    ) { }
    template <typename T> vec<1, T>::vec(const vec<1, T>& vec) : x(vec.x) { }
    template <typename T> vec<1, T>::vec(vec<1, T>&& vec     ) : x(vec.x) { }
    template <typename T> vec<1, T>::vec(T x                 ) : x(x    ) { }

    template <typename T> vec<1, T>::~vec() { }

    template <typename T> vec<1, T>& vec<1, T>::operator=(const vec<1, T>&  vec) { x = vec.x; return *this; }
    template <typename T> vec<1, T>& vec<1, T>::operator=(      vec<1, T>&& vec) { x = vec.x; return *this; }
    
    template <typename T> vec<1, T>& vec<1, T>::operator++() { ++x; return *this; }
    template <typename T> vec<1, T>& vec<1, T>::operator--() { --x; return *this; }
    
    template <typename T> vec<1, T> vec<1, T>::operator++(int) { vec<1, T> temp = *this; ++x; return temp; }
    template <typename T> vec<1, T> vec<1, T>::operator--(int) { vec<1, T> temp = *this; --x; return temp; }
    
    template <typename T> vec<1, T>& vec<1, T>::operator+=(const vec<1, T>& vec) { x += vec.x; return *this; }
    template <typename T> vec<1, T>& vec<1, T>::operator-=(const vec<1, T>& vec) { x -= vec.x; return *this; }
    template <typename T> vec<1, T>& vec<1, T>::operator*=(const vec<1, T>& vec) { x *= vec.x; return *this; }
    template <typename T> vec<1, T>& vec<1, T>::operator/=(const vec<1, T>& vec) { x /= vec.x; return *this; }
    
    template <typename T> vec<1, T>& vec<1, T>::operator+=(T num) { x += num; return *this; }
    template <typename T> vec<1, T>& vec<1, T>::operator-=(T num) { x -= num; return *this; }
    template <typename T> vec<1, T>& vec<1, T>::operator*=(T num) { x *= num; return *this; }
    template <typename T> vec<1, T>& vec<1, T>::operator/=(T num) { x /= num; return *this; }
    
    template <typename T> vec<1, T> vec<1, T>::operator+(const vec<1, T>& vec) const { return vec<1, T>(x + vec.x); }
    template <typename T> vec<1, T> vec<1, T>::operator-(const vec<1, T>& vec) const { return vec<1, T>(x - vec.x); }
    template <typename T> vec<1, T> vec<1, T>::operator*(const vec<1, T>& vec) const { return vec<1, T>(x * vec.x); }
    template <typename T> vec<1, T> vec<1, T>::operator/(const vec<1, T>& vec) const { return vec<1, T>(x / vec.x); }
    
    template <typename T> vec<1, T> vec<1, T>::operator+(T num) const { return vec<1, T>(x + num); }
    template <typename T> vec<1, T> vec<1, T>::operator-(T num) const { return vec<1, T>(x - num); }
    template <typename T> vec<1, T> vec<1, T>::operator*(T num) const { return vec<1, T>(x * num); }
    template <typename T> vec<1, T> vec<1, T>::operator/(T num) const { return vec<1, T>(x / num); }
    
    template <typename T> bool vec<1, T>::operator==(const vec<1, T>& vec) const { return x == vec.x; }
    template <typename T> bool vec<1, T>::operator!=(const vec<1, T>& vec) const { return x != vec.x; }
    
    template <typename T>       T& vec<1, T>::operator[](size_t index)       { return (&x)[index]; }
    template <typename T> const T& vec<1, T>::operator[](size_t index) const { return (&x)[index]; }
}