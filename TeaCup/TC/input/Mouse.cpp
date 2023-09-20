#include "TC/input/Mouse.hpp"

namespace TC
{
    Mouse::Mouse() : m_position(0.0f, 0.0f), m_buttons()
    {
        Initialize();
    }

    void Mouse::SetPosition(int x, int y)
    {
        m_position.x = x;
        m_position.y = y;
    }

    void Mouse::Initialize()
    {
        for (int i = 0; i < MouseButtonCodeCount; i++)
        {
            if (i == static_cast<int>(MouseButtonCode::Wheel)) m_buttons.push_back(new MouseWheel());
            else m_buttons.push_back(new MouseButton());
        }
    }
}