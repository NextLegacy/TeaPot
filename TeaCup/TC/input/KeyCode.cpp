#include "TC/input/KeyCode.hpp"

namespace TC
{
    int KeyCodeCount         = 120;
    int ModifierCodeCount    = 6  ;
    int ActionCodeCount      = 3  ;
    int MouseButtonCodeCount = 8  ;

    std::string ToString(KeyCode keyCode)
    {
        switch (keyCode)
        {
            case KeyCode::Space        : return "Space"        ;
            case KeyCode::Apostrophe   : return "Apostrophe"   ;
            case KeyCode::Comma        : return "Comma"        ;
            case KeyCode::Minus        : return "Minus"        ;
            case KeyCode::Period       : return "Period"       ;
            case KeyCode::Slash        : return "Slash"        ;
            case KeyCode::Num0         : return "Num0"         ;
            case KeyCode::Num1         : return "Num1"         ;
            case KeyCode::Num2         : return "Num2"         ;
            case KeyCode::Num3         : return "Num3"         ;
            case KeyCode::Num4         : return "Num4"         ;
            case KeyCode::Num5         : return "Num5"         ;
            case KeyCode::Num6         : return "Num6"         ;
            case KeyCode::Num7         : return "Num7"         ;
            case KeyCode::Num8         : return "Num8"         ;
            case KeyCode::Num9         : return "Num9"         ;
            case KeyCode::Semicolon    : return "Semicolon"    ;
            case KeyCode::Equal        : return "Equal"        ;
            case KeyCode::A            : return "A"            ;
            case KeyCode::B            : return "B"            ;
            case KeyCode::C            : return "C"            ;
            case KeyCode::D            : return "D"            ;
            case KeyCode::E            : return "E"            ;
            case KeyCode::F            : return "F"            ;
            case KeyCode::G            : return "G"            ;
            case KeyCode::H            : return "H"            ;
            case KeyCode::I            : return "I"            ;
            case KeyCode::J            : return "J"            ;
            case KeyCode::K            : return "K"            ;
            case KeyCode::L            : return "L"            ;
            case KeyCode::M            : return "M"            ;
            case KeyCode::N            : return "N"            ;
            case KeyCode::O            : return "O"            ;
            case KeyCode::P            : return "P"            ;
            case KeyCode::Q            : return "Q"            ;
            case KeyCode::R            : return "R"            ;
            case KeyCode::S            : return "S"            ;
            case KeyCode::T            : return "T"            ;
            case KeyCode::U            : return "U"            ;
            case KeyCode::V            : return "V"            ;
            case KeyCode::W            : return "W"            ;
            case KeyCode::X            : return "X"            ;
            case KeyCode::Y            : return "Y"            ;
            case KeyCode::Z            : return "Z"            ;
            case KeyCode::LeftBracket  : return "LeftBracket"  ;
            case KeyCode::Backslash    : return "Backslash"    ;
            case KeyCode::RightBracket : return "RightBracket" ;
            case KeyCode::GraveAccent  : return "GraveAccent"  ;
            case KeyCode::World1       : return "World1"       ;
            case KeyCode::World2       : return "World2"       ;
            case KeyCode::Escape       : return "Escape"       ;
            case KeyCode::Enter        : return "Enter"        ;
            case KeyCode::Tab          : return "Tab"          ;
            case KeyCode::Backspace    : return "Backspace"    ;
            case KeyCode::Insert       : return "Insert"       ;
            case KeyCode::Delete       : return "Delete"       ;
            case KeyCode::Right        : return "Right"        ;
            case KeyCode::Left         : return "Left"         ;
            case KeyCode::Down         : return "Down"         ;
            case KeyCode::Up           : return "Up"           ;
            case KeyCode::PageUp       : return "PageUp"       ;
            case KeyCode::PageDown     : return "PageDown"     ;
            case KeyCode::Home         : return "Home"         ;
            case KeyCode::End          : return "End"          ;
            case KeyCode::CapsLock     : return "CapsLock"     ;
            case KeyCode::ScrollLock   : return "ScrollLock"   ;
            case KeyCode::NumLock      : return "NumLock"      ;
            case KeyCode::PrintScreen  : return "PrintScreen"  ;
            case KeyCode::Pause        : return "Pause"        ;
            case KeyCode::F1           : return "F1"           ;
            case KeyCode::F2           : return "F2"           ;
            case KeyCode::F3           : return "F3"           ;
            case KeyCode::F4           : return "F4"           ;
            case KeyCode::F5           : return "F5"           ;
            case KeyCode::F6           : return "F6"           ;
            case KeyCode::F7           : return "F7"           ;
            case KeyCode::F8           : return "F8"           ;
            case KeyCode::F9           : return "F9"           ;
            case KeyCode::F10          : return "F10"          ;
            case KeyCode::F11          : return "F11"          ;
            case KeyCode::F12          : return "F12"          ;
            case KeyCode::F13          : return "F13"          ;
            case KeyCode::F14          : return "F14"          ;
            case KeyCode::F15          : return "F15"          ;
            case KeyCode::F16          : return "F16"          ;
            case KeyCode::F17          : return "F17"          ;
            case KeyCode::F18          : return "F18"          ;
            case KeyCode::F19          : return "F19"          ;
            case KeyCode::F20          : return "F20"          ;
            case KeyCode::F21          : return "F21"          ;
            case KeyCode::F22          : return "F22"          ;
            case KeyCode::F23          : return "F23"          ;
            case KeyCode::F24          : return "F24"          ;
            case KeyCode::F25          : return "F25"          ;
            case KeyCode::Kp0          : return "Kp0"          ;
            case KeyCode::Kp1          : return "Kp1"          ;
            case KeyCode::Kp2          : return "Kp2"          ;
            case KeyCode::Kp3          : return "Kp3"          ;
            case KeyCode::Kp4          : return "Kp4"          ;
            case KeyCode::Kp5          : return "Kp5"          ;
            case KeyCode::Kp6          : return "Kp6"          ;
            case KeyCode::Kp7          : return "Kp7"          ;
            case KeyCode::Kp8          : return "Kp8"          ;
            case KeyCode::Kp9          : return "Kp9"          ;
            case KeyCode::KpDecimal    : return "KpDecimal"    ;
            case KeyCode::KpDivide     : return "KpDivide"     ;
            case KeyCode::KpMultiply   : return "KpMultiply"   ;
            case KeyCode::KpSubtract   : return "KpSubtract"   ;
            case KeyCode::KpAdd        : return "KpAdd"        ;
            case KeyCode::KpEnter      : return "KpEnter"      ;
            case KeyCode::KpEqual      : return "KpEqual"      ;
            case KeyCode::LeftShift    : return "LeftShift"    ;
            case KeyCode::LeftControl  : return "LeftControl"  ;
            case KeyCode::LeftAlt      : return "LeftAlt"      ;
            case KeyCode::LeftSuper    : return "LeftSuper"    ;
            case KeyCode::RightShift   : return "RightShift"   ;
            case KeyCode::RightControl : return "RightControl" ;
            case KeyCode::RightAlt     : return "RightAlt"     ;
            case KeyCode::RightSuper   : return "RightSuper"   ;
            case KeyCode::Menu         : return "Menu"         ;
            default                    : return "Unknown"      ;
        }
    }

    std::string ToString(ActionCode keyAction)
    {
        switch (keyAction)
        {
            case ActionCode::Press  : return "Press"  ;
            case ActionCode::Release: return "Release";
            case ActionCode::Repeat : return "Repeat" ;
            default                 : return "Unknown";
        }
    }

    std::string ToString(ModifierCode keyModifier)
    {
        switch (keyModifier)
        {
            case ModifierCode::Shift  : return "Shift"  ;
            case ModifierCode::Control: return "Control";
            case ModifierCode::Alt    : return "Alt"    ;
            case ModifierCode::Super  : return "Super"  ;
            default                   : return "Unknown";
        }
    }

    std::string ToString(MouseButtonCode mouseButton)
    {
        switch (mouseButton)
        {
            case MouseButtonCode::Button1: return "Button1";
            case MouseButtonCode::Button2: return "Button2";
            case MouseButtonCode::Button3: return "Button3";
            case MouseButtonCode::Button4: return "Button4";
            case MouseButtonCode::Button5: return "Button5";
            case MouseButtonCode::Button6: return "Button6";
            case MouseButtonCode::Button7: return "Button7";
            case MouseButtonCode::Button8: return "Button8";
            default                      : return "Unknown";
        }
    }

    /*
    std::string ToString(CursorCode cursorCode)
    {
        switch (cursorCode)
        {
            case CursorCode::Arrow    : return "Arrow"    ;
            case CursorCode::IBeam    : return "IBeam"    ;
            case CursorCode::Crosshair: return "Crosshair";
            case CursorCode::Hand     : return "Hand"     ;
            case CursorCode::HResize  : return "HResize"  ;
            case CursorCode::VResize  : return "VResize"  ;
            default                   : return "Unknown"  ;
        }
    }

    std::string ToString(JoystickCode joystickCode)
    {
        return glfwGetJoystickName(static_cast<int>(joystickCode));
    }

    std::string ToString(GamepadCode joystickAxisCode)
    {
        return glfwGetGamepadName(static_cast<int>(joystickAxisCode));
    }

    std::string ToString(GamepadAxisCode joystickAxisCode)
    {
        switch (joystickAxisCode)
        {
            case GamepadAxisCode::GamepadLeftX       : return "GamepadLeftX"        ;
            case GamepadAxisCode::GamepadLeftY       : return "GamepadLeftY"        ;
            case GamepadAxisCode::GamepadRightX      : return "GamepadRightX"       ;
            case GamepadAxisCode::GamepadRightY      : return "GamepadRightY"       ;
            case GamepadAxisCode::GamepadRightTrigger: return "GamepadRightTrigger" ;
            case GamepadAxisCode::GamepadLeftTrigger : return "GamepadLeftTrigger"  ;
            default                                  : return "Unknown"             ;
        }
    }
    */
}