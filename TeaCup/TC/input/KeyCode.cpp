#include "TC/input/KeyCode.hpp"

namespace TC
{
    int KeyCodeCount         = 120;
    int ModifierCodeCount    = 6  ;
    int ActionCodeCount      = 3  ;
    int MouseButtonCodeCount = 8  ;

    std::map<KeyCode, std::string> KeyCodeToStringMap =
    {
        { KeyCode::Space       , "Space"        },
        { KeyCode::Apostrophe  , "Apostrophe"   },
        { KeyCode::Comma       , "Comma"        },
        { KeyCode::Minus       , "Minus"        },
        { KeyCode::Period      , "Period"       },
        { KeyCode::Slash       , "Slash"        },
        { KeyCode::Num0        , "Num0"         },
        { KeyCode::Num1        , "Num1"         },
        { KeyCode::Num2        , "Num2"         },
        { KeyCode::Num3        , "Num3"         },
        { KeyCode::Num4        , "Num4"         },
        { KeyCode::Num5        , "Num5"         },
        { KeyCode::Num6        , "Num6"         },
        { KeyCode::Num7        , "Num7"         },
        { KeyCode::Num8        , "Num8"         },
        { KeyCode::Num9        , "Num9"         },
        { KeyCode::Semicolon   , "Semicolon"    },
        { KeyCode::Equal       , "Equal"        },
        { KeyCode::A           , "A"            },
        { KeyCode::B           , "B"            },
        { KeyCode::C           , "C"            },
        { KeyCode::D           , "D"            },
        { KeyCode::E           , "E"            },
        { KeyCode::F           , "F"            },
        { KeyCode::G           , "G"            },
        { KeyCode::H           , "H"            },
        { KeyCode::I           , "I"            },
        { KeyCode::J           , "J"            },
        { KeyCode::K           , "K"            },
        { KeyCode::L           , "L"            },
        { KeyCode::M           , "M"            },
        { KeyCode::N           , "N"            },
        { KeyCode::O           , "O"            },
        { KeyCode::P           , "P"            },
        { KeyCode::Q           , "Q"            },
        { KeyCode::R           , "R"            },
        { KeyCode::S           , "S"            },
        { KeyCode::T           , "T"            },
        { KeyCode::U           , "U"            },
        { KeyCode::V           , "V"            },
        { KeyCode::W           , "W"            },
        { KeyCode::X           , "X"            },
        { KeyCode::Y           , "Y"            },
        { KeyCode::Z           , "Z"            },
        { KeyCode::LeftBracket , "LeftBracket"  },
        { KeyCode::Backslash   , "Backslash"    },
        { KeyCode::RightBracket, "RightBracket" },
        { KeyCode::GraveAccent , "GraveAccent"  },
        { KeyCode::World1      , "World1"       },
        { KeyCode::World2      , "World2"       },
        { KeyCode::Escape      , "Escape"       },
        { KeyCode::Enter       , "Enter"        },
        { KeyCode::Tab         , "Tab"          },
        { KeyCode::Backspace   , "Backspace"    },
        { KeyCode::Insert      , "Insert"       },
        { KeyCode::Delete      , "Delete"       },
        { KeyCode::Right       , "Right"        },
        { KeyCode::Left        , "Left"         },
        { KeyCode::Down        , "Down"         },
        { KeyCode::Up          , "Up"           },
        { KeyCode::PageUp      , "PageUp"       },
        { KeyCode::PageDown    , "PageDown"     },
        { KeyCode::Home        , "Home"         },
        { KeyCode::End         , "End"          },
        { KeyCode::CapsLock    , "CapsLock"     },
        { KeyCode::ScrollLock  , "ScrollLock"   },
        { KeyCode::NumLock     , "NumLock"      },
        { KeyCode::PrintScreen , "PrintScreen"  },
        { KeyCode::Pause       , "Pause"        },
        { KeyCode::F1          , "F1"           },
        { KeyCode::F2          , "F2"           },
        { KeyCode::F3          , "F3"           },
        { KeyCode::F4          , "F4"           },
        { KeyCode::F5          , "F5"           },
        { KeyCode::F6          , "F6"           },
        { KeyCode::F7          , "F7"           },
        { KeyCode::F8          , "F8"           },
        { KeyCode::F9          , "F9"           },
        { KeyCode::F10         , "F10"          },
        { KeyCode::F11         , "F11"          },
        { KeyCode::F12         , "F12"          },
        { KeyCode::F13         , "F13"          },
        { KeyCode::F14         , "F14"          },
        { KeyCode::F15         , "F15"          },
        { KeyCode::F16         , "F16"          },
        { KeyCode::F17         , "F17"          },
        { KeyCode::F18         , "F18"          },
        { KeyCode::F19         , "F19"          },
        { KeyCode::F20         , "F20"          },
        { KeyCode::F21         , "F21"          },
        { KeyCode::F22         , "F22"          },
        { KeyCode::F23         , "F23"          },
        { KeyCode::F24         , "F24"          },
        { KeyCode::F25         , "F25"          },
        { KeyCode::Kp0         , "Kp0"          },
        { KeyCode::Kp1         , "Kp1"          },
        { KeyCode::Kp2         , "Kp2"          },
        { KeyCode::Kp3         , "Kp3"          },
        { KeyCode::Kp4         , "Kp4"          },
        { KeyCode::Kp5         , "Kp5"          },
        { KeyCode::Kp6         , "Kp6"          },
        { KeyCode::Kp7         , "Kp7"          },
        { KeyCode::Kp8         , "Kp8"          },
        { KeyCode::Kp9         , "Kp9"          },
        { KeyCode::KpDecimal   , "KpDecimal"    },
        { KeyCode::KpDivide    , "KpDivide"     },
        { KeyCode::KpMultiply  , "KpMultiply"   },
        { KeyCode::KpSubtract  , "KpSubtract"   },
        { KeyCode::KpAdd       , "KpAdd"        },
        { KeyCode::KpEnter     , "KpEnter"      },
        { KeyCode::KpEqual     , "KpEqual"      },
        { KeyCode::LeftShift   , "LeftShift"    },
        { KeyCode::LeftControl , "LeftControl"  },
        { KeyCode::LeftAlt     , "LeftAlt"      },
        { KeyCode::LeftSuper   , "LeftSuper"    },
        { KeyCode::RightShift  , "RightShift"   },
        { KeyCode::RightControl, "RightControl" },
        { KeyCode::RightAlt    , "RightAlt"     },
        { KeyCode::RightSuper  , "RightSuper"   },
        { KeyCode::Menu        , "Menu"         },
    };

    std::map<ModifierCode, std::string> ModifierCodeToStringMap =
    {
        { ModifierCode::Shift  , "Shift"  },
        { ModifierCode::Control, "Control"},
        { ModifierCode::Alt    , "Alt"    },
        { ModifierCode::Super  , "Super"  },
    };

    std::map<ActionCode, std::string> ActionCodeToStringMap =
    {
        { ActionCode::Press  , "Press"  },
        { ActionCode::Release, "Release"},
        { ActionCode::Repeat , "Repeat" },
    };

    std::map<MouseButtonCode, std::string> MouseButtonCodeToStringMap =
    {
        { MouseButtonCode::Button1, "Button1"},
        { MouseButtonCode::Button2, "Button2"},
        { MouseButtonCode::Button3, "Button3"},
        { MouseButtonCode::Button4, "Button4"},
        { MouseButtonCode::Button5, "Button5"},
        { MouseButtonCode::Button6, "Button6"},
        { MouseButtonCode::Button7, "Button7"},
        { MouseButtonCode::Button8, "Button8"},
    };

    std::string ToString(KeyCode         keyCode    ) { return KeyCodeToStringMap        [keyCode    ]; }
    std::string ToString(ModifierCode    keyModifier) { return ModifierCodeToStringMap   [keyModifier]; }
    std::string ToString(ActionCode      keyAction  ) { return ActionCodeToStringMap     [keyAction  ]; }
    std::string ToString(MouseButtonCode mouseButton) { return MouseButtonCodeToStringMap[mouseButton]; }

    /*
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