#include "window/input/Key.hpp"

namespace BHW
{
    Key::Key() : m_pressed(false), m_timeStamp(0)
    {
    }

    bool Key::Pressed(uint64_t thresholdMin, uint64_t thresholdMax)
    {
        uint64_t time = Time::Now() - m_timeStamp;

        return Pressed() && time >= thresholdMin && time <= thresholdMax; 
    }

    bool Key::Released(uint64_t thresholdMin, uint64_t thresholdMax)
    { 
        uint64_t time = Time::Now() - m_timeStamp;

        return Released() && time >= thresholdMin && time <= thresholdMax; 
    }

    void Key::Press()
    {
        m_pressed = true;
        m_timeStamp = Time::Now();
    }

    void Key::Release()
    {
        m_pressed = false;
        m_timeStamp = Time::Now();
    }
}