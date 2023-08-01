#pragma once

#include <glm/glm.hpp>

#include "window/input/Key.hpp"

#include "window/input/MouseButton.hpp"
#include "window/input/MouseWheel.hpp"

namespace BHW
{
    class Mouse
    {
    public:
        Mouse();

        inline glm::vec2 Position() const { return m_position; }

        inline MouseButton Left () const { return m_left    ; }
        inline MouseButton Right() const { return m_right   ; }
        inline MouseWheel  Wheel() const { return m_middle  ; }

    private:
        void SetPosition(const glm::vec2& position);

        friend class Input;
    private:

        glm::vec2 m_position;
        
        MouseButton m_left  ;
        MouseButton m_right ;
        MouseWheel  m_middle;
    };
}