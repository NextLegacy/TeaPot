#pragma once

#include <string>

namespace TC
{
    extern int KeyCodeCount        ;
    extern int ModifierCodeCount   ;
    extern int ActionCodeCount     ;
    extern int MouseButtonCodeCount;

    enum class KeyCode
    {
        Space               = 32,
        Apostrophe          = 39 ,
        Comma               = 44 ,
        Minus               = 45 ,
        Period              = 46 ,
        Slash               = 47 ,
        Num0                = 48,
        Num1                = 49,
        Num2                = 50,
        Num3                = 51,
        Num4                = 52,
        Num5                = 53,
        Num6                = 54,
        Num7                = 55,
        Num8                = 56,
        Num9                = 57,
        Semicolon           = 59 ,
        Equal               = 61 ,
        A                   = 65,
        B                   = 66,
        C                   = 67,
        D                   = 68,
        E                   = 69,
        F                   = 70,
        G                   = 71,
        H                   = 72,
        I                   = 73,
        J                   = 74,
        K                   = 75,
        L                   = 76,
        M                   = 77,
        N                   = 78,
        O                   = 79,
        P                   = 80,
        Q                   = 81,
        R                   = 82,
        S                   = 83,
        T                   = 84,
        U                   = 85,
        V                   = 86,
        W                   = 87,
        X                   = 88,
        Y                   = 89,
        Z                   = 90,
        LeftBracket         = 91 ,
        Backslash           = 92 ,
        RightBracket        = 93 ,
        GraveAccent         = 96 ,
        World1              = 161,
        World2              = 162,
        Escape              = 256,
        Enter               = 257,
        Tab                 = 258,
        Backspace           = 259,
        Insert              = 260,
        Delete              = 261,
        Right               = 262,
        Left                = 263,
        Down                = 264,
        Up                  = 265,
        PageUp              = 266,
        PageDown            = 267,
        Home                = 268,
        End                 = 269,
        CapsLock            = 280,
        ScrollLock          = 281,
        NumLock             = 282,
        PrintScreen         = 283,
        Pause               = 284,
        F1                  = 290,
        F2                  = 291,
        F3                  = 292,
        F4                  = 293,
        F5                  = 294,
        F6                  = 295,
        F7                  = 296,
        F8                  = 297,
        F9                  = 298,
        F10                 = 299,
        F11                 = 300,
        F12                 = 301,
        F13                 = 302,
        F14                 = 303,
        F15                 = 304,
        F16                 = 305,
        F17                 = 306,
        F18                 = 307,
        F19                 = 308,
        F20                 = 309,
        F21                 = 310,
        F22                 = 311,
        F23                 = 312,
        F24                 = 313,
        F25                 = 314,
        Kp0                 = 320,
        Kp1                 = 321,
        Kp2                 = 322,
        Kp3                 = 323,
        Kp4                 = 324,
        Kp5                 = 325,
        Kp6                 = 326,
        Kp7                 = 327,
        Kp8                 = 328,
        Kp9                 = 329,
        KpDecimal           = 330,
        KpDivide            = 331,
        KpMultiply          = 332,
        KpSubtract          = 333,
        KpAdd               = 334,
        KpEnter             = 335,
        KpEqual             = 336,
        LeftShift           = 340,
        LeftControl         = 341,
        LeftAlt             = 342,
        LeftSuper           = 343,
        RightShift          = 344,
        RightControl        = 345,
        RightAlt            = 346,
        RightSuper          = 347,
        Menu                = 348,
        Last                = 348
    };

    enum class ModifierCode
    {
        Shift               = 0x0001,
        Control             = 0x0002,
        Alt                 = 0x0004,
        Super               = 0x0008,
        CapsLock            = 0x0010,
        NumLock             = 0x0020
    };

    enum class ActionCode
    {
        Release             = 0,
        Press               = 1,
        Repeat              = 2
    };

    enum class MouseButtonCode
    {
        Button1             = 0,
        Button2             = 1,
        Button3             = 2,
        Button4             = 3,
        Button5             = 4,
        Button6             = 5,
        Button7             = 6,
        Button8             = 7,
        Left                = 0,
        Right               = 1,
        Wheel               = 2,
    };

    /*
    enum class CursorCode
    {
        Arrow               = GLFW_ARROW_CURSOR,
        IBeam               = GLFW_IBEAM_CURSOR,
        Crosshair           = GLFW_CROSSHAIR_CURSOR,
        Hand                = GLFW_HAND_CURSOR,
        HResize             = GLFW_HRESIZE_CURSOR,
        VResize             = GLFW_VRESIZE_CURSOR
    };


    enum class JoystickCode
    {
        Joystick1           = GLFW_JOYSTICK_1,
        Joystick2           = GLFW_JOYSTICK_2,
        Joystick3           = GLFW_JOYSTICK_3,
        Joystick4           = GLFW_JOYSTICK_4,
        Joystick5           = GLFW_JOYSTICK_5,
        Joystick6           = GLFW_JOYSTICK_6,
        Joystick7           = GLFW_JOYSTICK_7,
        Joystick8           = GLFW_JOYSTICK_8,
        Joystick9           = GLFW_JOYSTICK_9,
        Joystick10          = GLFW_JOYSTICK_10,
        Joystick11          = GLFW_JOYSTICK_11,
        Joystick12          = GLFW_JOYSTICK_12,
        Joystick13          = GLFW_JOYSTICK_13,
        Joystick14          = GLFW_JOYSTICK_14,
        Joystick15          = GLFW_JOYSTICK_15,
        Joystick16          = GLFW_JOYSTICK_16,
        JoystickLast        = GLFW_JOYSTICK_LAST
    };

    enum class GamepadCode
    {
        GamepadA            = GLFW_GAMEPAD_BUTTON_A,
        GamepadB            = GLFW_GAMEPAD_BUTTON_B,
        GamepadX            = GLFW_GAMEPAD_BUTTON_X,
        GamepadY            = GLFW_GAMEPAD_BUTTON_Y,
        GamepadLeftBumper   = GLFW_GAMEPAD_BUTTON_LEFT_BUMPER,
        GamepadRightBumper  = GLFW_GAMEPAD_BUTTON_RIGHT_BUMPER,
        GamepadBack         = GLFW_GAMEPAD_BUTTON_BACK,
        GamepadStart        = GLFW_GAMEPAD_BUTTON_START,
        GamepadGuide        = GLFW_GAMEPAD_BUTTON_GUIDE,
        GamepadLeftThumb    = GLFW_GAMEPAD_BUTTON_LEFT_THUMB,
        GamepadRightThumb   = GLFW_GAMEPAD_BUTTON_RIGHT_THUMB,
        GamepadDPadUp       = GLFW_GAMEPAD_BUTTON_DPAD_UP,
        GamepadDPadRight    = GLFW_GAMEPAD_BUTTON_DPAD_RIGHT,
        GamepadDPadDown     = GLFW_GAMEPAD_BUTTON_DPAD_DOWN,
        GamepadDPadLeft     = GLFW_GAMEPAD_BUTTON_DPAD_LEFT,
        GamepadLast         = GLFW_GAMEPAD_BUTTON_LAST,
        GamepadCross        = GLFW_GAMEPAD_BUTTON_CROSS,
        GamepadCircle       = GLFW_GAMEPAD_BUTTON_CIRCLE,
        GamepadSquare       = GLFW_GAMEPAD_BUTTON_SQUARE,
        GamepadTriangle     = GLFW_GAMEPAD_BUTTON_TRIANGLE
    };

    enum class GamepadAxisCode
    {
        GamepadLeftX        = GLFW_GAMEPAD_AXIS_LEFT_X,
        GamepadLeftY        = GLFW_GAMEPAD_AXIS_LEFT_Y,
        GamepadRightX       = GLFW_GAMEPAD_AXIS_RIGHT_X,
        GamepadRightY       = GLFW_GAMEPAD_AXIS_RIGHT_Y,
        GamepadLeftTrigger  = GLFW_GAMEPAD_AXIS_LEFT_TRIGGER,
        GamepadRightTrigger = GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER
    };
    */

    extern std::string ToString(KeyCode         keyCode     );
    extern std::string ToString(ModifierCode    modifierCode);
    extern std::string ToString(ActionCode      actionCode  );
    extern std::string ToString(MouseButtonCode mouseCode   );

    /*
    std::string ToString(CursorCode      cursorCode     );
    std::string ToString(JoystickCode    joystickCode   );
    std::string ToString(GamepadCode     gamepadCode    );
    std::string ToString(GamepadAxisCode gamepadAxisCode);
    */
}