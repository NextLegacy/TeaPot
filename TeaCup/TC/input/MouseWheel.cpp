#include "TC/input/MouseWheel.hpp"

namespace TC
{
    MouseWheel::MouseWheel() : m_delta(0)
    {
    }

    void MouseWheel::SetDelta(double x, double y)
    {
        m_delta.x += x;
        m_delta.y += y;
    }
}