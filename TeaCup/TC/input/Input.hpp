#pragma once

#include <vector>

#include <BHW/utils/console/Console.hpp>
#include <BHW/utils/event/DynamicEventSystem.hpp>

#include "TC/input/KeyBoard.hpp"
#include "TC/input/Mouse.hpp"

namespace TC
{
    class InputListener
    {
        virtual void Keyboard   (int    key    , int    scancode, int action, int mods) { }
        virtual void Mouse      (int    button ,                  int action, int mods) { }
        virtual void MouseScroll(double xoffset, double yoffset                       ) { }
        virtual void MouseMove  (double xpos   , double ypos                          ) { }
    };

    class Input
    {
    public:
        Input();

        inline KeyBoard& KeyBoard() { return m_keyBoard; }

        inline TC::Key&         Key          (KeyCode         code) { return m_keyBoard(code); }
        inline TC::MouseButton& MouseButton  (MouseButtonCode code) { return m_mouse   (code); }

        inline TC::Mouse&       Mouse          () { return m_mouse             ; }
        inline glm::vec2        MousePosition  () { return m_mouse.Position  (); }

        inline TC::MouseWheel&  MouseWheel     () { return m_mouse.Wheel     (); }
        inline glm::vec2        MouseWheelDelta() { return MouseWheel().Delta(); }

        inline TC::MouseButton& MouseLeft      () { return m_mouse.Left      (); }
        inline TC::MouseButton& MouseRight     () { return m_mouse.Right     (); }

    public:
        void KeyboardKeyCallback(int    key    , int    scancode, int action, int mods);
        void MouseButtonCallback(int    button ,                  int action, int mods);
        void MouseScrollCallback(double xoffset, double yoffset                       );
        void MouseMoveCallback  (double xpos   , double ypos                          );

        void UpdateKey        (int    key    , int action);
        void UpdateMouseButton(int    button , int action);
        void UpdateMouseScroll(double xoffset, double yoffset);
        void UpdateMouseMove  (double xpos   , double ypos   );

        void GetKey        (int    key    , int&    action , int& mods);
        void GetMouseButton(int    button , int&    action , int& mods);
        void GetMouseScroll(double xoffset, double& yoffset           );
        void GetMouseMove  (double xpos   , double& ypos              );

    private:
        TC::KeyBoard m_keyBoard;
        TC::Mouse    m_mouse   ;
    };
}