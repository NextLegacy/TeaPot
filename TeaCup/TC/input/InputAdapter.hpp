#pragma once

#include "TC/input/Input.hpp"

namespace TC
{
    class InputAdapter
    {
    public:
        InputAdapter(TC::Input* input);

        void KeyboardKey(int    key    , int    scancode, int action, int mods);
        void MouseButton(int    button ,                  int action, int mods);
        void MouseScroll(double xoffset, double yoffset                       );
        void MouseMove  (double xpos   , double ypos                          );



        void GetKey        (int    key    , int& action    , int& mods);
        void GetMouseButton(int    button , int& action    , int& mods);
        void GetMouseScroll(double xoffset, double& yoffset           );
        void GetMouseMove  (double xpos   , double& ypos              );

    private:
        TC::Input* m_input;
    };
}