#include "window/Window_OpenGL_Impl.hpp"

namespace BHW
{
    int OpenGLImpl::API_InitializeWindow()
    {
        glfwSetErrorCallback([](int error, const char* description)
        {
            std::cout << "GLFW Error: " << error << " - " << description << std::endl;
        });

        if (!glfwInit())
            return 1;

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        m_window = glfwCreateWindow(Width(), Height(), "Boiling Hot Water", nullptr, nullptr);

        if (m_window == nullptr)
            return 1;

        glfwMakeContextCurrent(m_window);
        glfwSwapInterval(0);

        glfwSetWindowUserPointer(m_window, &Input());

        glfwSetKeyCallback(m_window, [](GLFWwindow* window, int key, int scancode, int action, int mods)
        {
            auto& input = *static_cast<BHW::Input*>(glfwGetWindowUserPointer(window));

            if      (action == GLFW_PRESS  ) input.PressKey  (key);
            else if (action == GLFW_RELEASE) input.ReleaseKey(key);
        });

        glfwSetMouseButtonCallback(m_window, [](GLFWwindow* window, int button, int action, int mods)
        {
            auto& input = *static_cast<BHW::Input*>(glfwGetWindowUserPointer(window));

            if (action == GLFW_PRESS  )
                if      (button == GLFW_MOUSE_BUTTON_LEFT  ) input.PressLeft  ();
                else if (button == GLFW_MOUSE_BUTTON_RIGHT ) input.PressRight ();
                else if (button == GLFW_MOUSE_BUTTON_MIDDLE) input.PressMiddle();
            else if (action == GLFW_RELEASE)
                if      (button == GLFW_MOUSE_BUTTON_LEFT  ) input.ReleaseLeft  ();
                else if (button == GLFW_MOUSE_BUTTON_RIGHT ) input.ReleaseRight ();
                else if (button == GLFW_MOUSE_BUTTON_MIDDLE) input.ReleaseMiddle();
        });

        glfwSetScrollCallback(m_window, [](GLFWwindow* window, double xoffset, double yoffset)
        {
            auto& input = *static_cast<BHW::Input*>(glfwGetWindowUserPointer(window));

            input.SetMouseWheelDelta(yoffset);
        });

        glfwSetCursorPosCallback(m_window, [](GLFWwindow* window, double xpos, double ypos)
        {
            auto& input = *static_cast<BHW::Input*>(glfwGetWindowUserPointer(window));

            input.SetMousePosition({ xpos, ypos });
        });

        return 0;
    }

    void OpenGLImpl::API_StartUpdate()
    {
    }

    void OpenGLImpl::API_EndUpdate()
    {

    }

    void OpenGLImpl::API_StartFrame()
    {
        glfwPollEvents();
    }

    void OpenGLImpl::API_EndFrame()
    {

    }

    void OpenGLImpl::API_TerminateWindow()
    {
        glfwDestroyWindow(m_window);
        glfwTerminate();
    }

    std::map<int, KeyCode> OpenGLImpl::API_CreateKeyMap()
    {
        return 
        {
            { GLFW_KEY_A, KeyCode::A },
            { GLFW_KEY_B, KeyCode::B },
            { GLFW_KEY_C, KeyCode::C },
            { GLFW_KEY_D, KeyCode::D },
            { GLFW_KEY_E, KeyCode::E },
            { GLFW_KEY_F, KeyCode::F },
            { GLFW_KEY_G, KeyCode::G },
            { GLFW_KEY_H, KeyCode::H },
            { GLFW_KEY_I, KeyCode::I },
            { GLFW_KEY_J, KeyCode::J },
            { GLFW_KEY_K, KeyCode::K },
            { GLFW_KEY_L, KeyCode::L },
            { GLFW_KEY_M, KeyCode::M },
            { GLFW_KEY_N, KeyCode::N },
            { GLFW_KEY_O, KeyCode::O },
            { GLFW_KEY_P, KeyCode::P },
            { GLFW_KEY_Q, KeyCode::Q },
            { GLFW_KEY_R, KeyCode::R },
            { GLFW_KEY_S, KeyCode::S },
            { GLFW_KEY_T, KeyCode::T },
            { GLFW_KEY_U, KeyCode::U },
            { GLFW_KEY_V, KeyCode::V },
            { GLFW_KEY_W, KeyCode::W },
            { GLFW_KEY_X, KeyCode::X },
            { GLFW_KEY_Y, KeyCode::Y },
            { GLFW_KEY_Z, KeyCode::Z },

            { GLFW_KEY_0, KeyCode::Zero  },
            { GLFW_KEY_1, KeyCode::One   },
            { GLFW_KEY_2, KeyCode::Two   },
            { GLFW_KEY_3, KeyCode::Three },
            { GLFW_KEY_4, KeyCode::Four  },
            { GLFW_KEY_5, KeyCode::Five  },
            { GLFW_KEY_6, KeyCode::Six   },
            { GLFW_KEY_7, KeyCode::Seven },
            { GLFW_KEY_8, KeyCode::Eight },
            { GLFW_KEY_9, KeyCode::Nine  },

            { GLFW_KEY_ESCAPE       , KeyCode::Escape       },
            { GLFW_KEY_MENU         , KeyCode::Menu         },
            { GLFW_KEY_LEFT_CONTROL , KeyCode::ControlLeft  },
            { GLFW_KEY_LEFT_SHIFT   , KeyCode::ShiftLeft    },
            { GLFW_KEY_LEFT_ALT     , KeyCode::AltLeft      },
            { GLFW_KEY_LEFT_SUPER   , KeyCode::SystemLeft   },
            { GLFW_KEY_RIGHT_CONTROL, KeyCode::ControlRight },
            { GLFW_KEY_RIGHT_SHIFT  , KeyCode::ShiftRight   },
            { GLFW_KEY_RIGHT_ALT    , KeyCode::AltRight     },
            { GLFW_KEY_RIGHT_SUPER  , KeyCode::SystemRight  },
            { GLFW_KEY_LEFT_BRACKET , KeyCode::Bracket_Left },

            { GLFW_KEY_RIGHT_BRACKET, KeyCode::Bracket_Right },
            { GLFW_KEY_SEMICOLON    , KeyCode::Semicolon     },
            { GLFW_KEY_COMMA        , KeyCode::Comma         },
            { GLFW_KEY_PERIOD       , KeyCode::Period        },
            { GLFW_KEY_APOSTROPHE   , KeyCode::Quote         },
            { GLFW_KEY_SLASH        , KeyCode::Slash         },
            { GLFW_KEY_BACKSLASH    , KeyCode::Backslash     },
            { GLFW_KEY_GRAVE_ACCENT , KeyCode::Tilde         },
            { GLFW_KEY_EQUAL        , KeyCode::Equal         },
            { GLFW_KEY_MINUS        , KeyCode::Hyphen        },
            { GLFW_KEY_SPACE        , KeyCode::Space         },
            { GLFW_KEY_ENTER        , KeyCode::Enter         },
            { GLFW_KEY_BACKSPACE    , KeyCode::Backspace     },
            { GLFW_KEY_TAB          , KeyCode::Tab           },
            { GLFW_KEY_PAGE_UP      , KeyCode::PageUp        },
            { GLFW_KEY_PAGE_DOWN    , KeyCode::PageDown      },
            { GLFW_KEY_END          , KeyCode::End           },
            { GLFW_KEY_HOME         , KeyCode::Home          },
            { GLFW_KEY_INSERT       , KeyCode::Insert        },
            { GLFW_KEY_DELETE       , KeyCode::Delete        },
            
            { GLFW_KEY_KP_ADD     , KeyCode::Add      },
            { GLFW_KEY_KP_SUBTRACT, KeyCode::Subtract },
            { GLFW_KEY_KP_MULTIPLY, KeyCode::Multiply },
            { GLFW_KEY_KP_DIVIDE  , KeyCode::Divide   },
            { GLFW_KEY_PAUSE      , KeyCode::Pause    },

            { GLFW_KEY_LEFT , KeyCode::Left  },
            { GLFW_KEY_RIGHT, KeyCode::Right },
            { GLFW_KEY_UP   , KeyCode::Up    },
            { GLFW_KEY_DOWN , KeyCode::Down  },

            { GLFW_KEY_KP_0, KeyCode::ZeroNumpad  },
            { GLFW_KEY_KP_1, KeyCode::OneNumpad   },
            { GLFW_KEY_KP_2, KeyCode::TwoNumpad   },
            { GLFW_KEY_KP_3, KeyCode::ThreeNumpad },
            { GLFW_KEY_KP_4, KeyCode::FourNumpad  },
            { GLFW_KEY_KP_5, KeyCode::FiveNumpad  },
            { GLFW_KEY_KP_6, KeyCode::SixNumpad   },
            { GLFW_KEY_KP_7, KeyCode::SevenNumpad },
            { GLFW_KEY_KP_8, KeyCode::EightNumpad },
            { GLFW_KEY_KP_9, KeyCode::NineNumpad  },

            { GLFW_KEY_F1 , KeyCode::F1  },
            { GLFW_KEY_F2 , KeyCode::F2  },
            { GLFW_KEY_F3 , KeyCode::F3  },
            { GLFW_KEY_F4 , KeyCode::F4  },
            { GLFW_KEY_F5 , KeyCode::F5  },
            { GLFW_KEY_F6 , KeyCode::F6  },
            { GLFW_KEY_F7 , KeyCode::F7  },
            { GLFW_KEY_F8 , KeyCode::F8  },
            { GLFW_KEY_F9 , KeyCode::F9  },
            { GLFW_KEY_F10, KeyCode::F10 },
            { GLFW_KEY_F11, KeyCode::F11 },
            { GLFW_KEY_F12, KeyCode::F12 },
            { GLFW_KEY_F13, KeyCode::F13 },
            { GLFW_KEY_F14, KeyCode::F14 },
            { GLFW_KEY_F15, KeyCode::F15 },
        };
    }
}