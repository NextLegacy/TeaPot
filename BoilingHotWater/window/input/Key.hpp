#pragma once

#include <cstdint>

#include "utils/Time.hpp"

namespace BHW
{
    enum class KeyCode
    {
        A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z,
        
        Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine,
        
        Escape, Menu,
        ControlLeft , ShiftLeft , AltLeft , SystemLeft ,
        ControlRight, ShiftRight, AltRight, SystemRight,
        Bracket_Left, Bracket_Right, Semicolon, Comma, Period, 
        Quote, Slash, Backslash, Tilde, Equal, Hyphen,
        Space, Enter, Backspace, Tab, PageUp, PageDown, End, Home, 
        Insert, Delete, Add, Subtract, Multiply, Divide,
        
        Left, Right, Up, Down,
        
        ZeroNumpad, OneNumpad, TwoNumpad, ThreeNumpad, FourNumpad, FiveNumpad, SixNumpad, SevenNumpad, EightNumpad, NineNumpad,
        
        F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12, F13, F14, F15, Pause,

        Count
    };

    struct Key
    {
    public:
        Key();

        inline bool Pressed () const { return  m_pressed; }
        inline bool Released() const { return !m_pressed; }

        bool Pressed (uint64_t thresholdMin, uint64_t thresholdMax = 9e9);
        bool Released(uint64_t thresholdMin, uint64_t thresholdMax = 9e9);

    private:
        void Press  ();
        void Release();

        friend class Input;
        friend class KeyBoard;
    
    private:
        bool m_pressed;

        uint64_t m_timeStamp;
    };
}