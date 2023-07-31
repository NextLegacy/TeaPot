#pragma once

#include <vector>
#include "window/input/KeyBoard.hpp"
#include "window/input/Mouse.hpp"

namespace BHW
{
    class Input
    {
    public:
        Input(std::map<int, KeyCode> keyMap);

        inline KeyBoard& KeyBoard() { return m_keyBoard; }
        inline Mouse&    Mouse   () { return m_mouse   ; }

        inline void PressKey  (int code) { m_keyBoard(code).Press  (); }
        inline void ReleaseKey(int code) { m_keyBoard(code).Release(); }

        inline void PressLeft  () { m_mouse.Left ().Press(); }
        inline void PressRight () { m_mouse.Right().Press(); }
        inline void PressMiddle() { m_mouse.Wheel().Press(); }

        inline void ReleaseLeft  () { m_mouse.Left ().Release(); }
        inline void ReleaseRight () { m_mouse.Right().Release(); }
        inline void ReleaseMiddle() { m_mouse.Wheel().Release(); }

        inline void SetMousePosition(glm::vec2 position) { m_mouse.SetPosition(position); }

        inline void SetMouseWheelDelta(int delta) { m_mouse.Wheel().SetDelta(delta); }

    private:
        BHW::KeyBoard m_keyBoard;
        BHW::Mouse    m_mouse   ;
    };
}