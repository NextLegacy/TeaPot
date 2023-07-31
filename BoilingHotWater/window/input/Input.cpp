#include "window/input/Input.hpp"

namespace BHW
{
    Input::Input(std::map<int, KeyCode> keyMap) : m_keyBoard(keyMap)
    {
    }
}