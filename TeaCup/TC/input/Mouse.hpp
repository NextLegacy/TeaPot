#pragma once

#include <vector>

#include <glm/glm.hpp>

#include "TC/input/Key.hpp"

#include "TC/input/MouseButton.hpp"
#include "TC/input/MouseWheel.hpp"

namespace TC
{
    class Mouse
    {
    public:
        Mouse();

        inline glm::vec2 Position() const { return m_position; }

        inline MouseButton& Button(MouseButtonCode code) { return *m_buttons[static_cast<int>(code)]; }
        
        inline MouseButton& Left   () { return Button(MouseButtonCode::Left  ); }
        inline MouseButton& Right  () { return Button(MouseButtonCode::Right ); }
        inline MouseWheel & Wheel  () { return static_cast<MouseWheel&>(Button(MouseButtonCode::Wheel)); }
        inline MouseButton& Button1() { return Button(MouseButtonCode::Button1); }
        inline MouseButton& Button2() { return Button(MouseButtonCode::Button2); }
        inline MouseButton& Button3() { return Button(MouseButtonCode::Button3); }
        inline MouseButton& Button4() { return Button(MouseButtonCode::Button4); }
        inline MouseButton& Button5() { return Button(MouseButtonCode::Button5); }
        inline MouseButton& Button6() { return Button(MouseButtonCode::Button6); }
        inline MouseButton& Button7() { return Button(MouseButtonCode::Button7); }
        inline MouseButton& Button8() { return Button(MouseButtonCode::Button8); }

        inline MouseButton& operator()(MouseButtonCode code) { return Button(code); }

    private:
        void Initialize();

        void SetPosition(int x, int y);

        friend class Input;

    private:
        glm::vec2 m_position;
        
        std::vector<MouseButton*> m_buttons;
    };
}