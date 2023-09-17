#include "TC/input/Input.hpp"

namespace TC
{
    Input::Input() : m_keyBoard(), m_mouse()
    {

    }

    void Input::KeyboardKeyCallback(int key, int scancode, int action, int mods)
    {
        Key(static_cast<KeyCode>(key)).SetAction(static_cast<ActionCode>(action));
    }

    void Input::MouseButtonCallback(int button, int action, int mods)
    {
        MouseButton(static_cast<MouseButtonCode>(button)).SetAction(static_cast<ActionCode>(action));
    }

    void Input::MouseScrollCallback(double xoffset, double yoffset)
    {
        MouseWheel().SetDelta(xoffset, yoffset);
    }

    void Input::MouseMoveCallback(double xpos, double ypos)
    {
        m_mouse.SetPosition(xpos, ypos);
    }

    void Input::UpdateKey(int key, int action)
    {
        Key(static_cast<KeyCode>(key)).SetAction(static_cast<ActionCode>(action));
    }

    void Input::UpdateMouseButton(int button, int action)
    {
        MouseButton(static_cast<MouseButtonCode>(button)).SetAction(static_cast<ActionCode>(action));
    }

    void Input::UpdateMouseScroll(double xoffset, double yoffset)
    {
        MouseWheel().SetDelta(xoffset, yoffset);
    }

    void Input::UpdateMouseMove(double xpos, double ypos)
    {
        Mouse().SetPosition(xpos, ypos);
    }

    void Input::GetKey(int key, int& action, int& mods)
    {
        
    }

    void Input::GetMouseButton(int button, int& action, int& mods)
    {
        
    }

    void Input::GetMouseScroll(double xoffset, double& yoffset)
    {
        
    }

    void Input::GetMouseMove(double xpos, double& ypos)
    {
        
    }
}