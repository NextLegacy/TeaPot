#include "window/input/KeyBoard.hpp"

namespace BHW
{
    KeyBoard::KeyBoard(std::map<int, KeyCode> keyMap) : m_keyMap(keyMap)
    {
        Initialize();
    }
    
    Key KeyBoard::operator()(KeyCode code)
    {
        return m_keys[static_cast<int>(code)];
    }
    
    void KeyBoard::Initialize()
    {
        m_keys.resize(static_cast<int>(KeyCode::Count));

        for (int i = 0; i < static_cast<int>(KeyCode::Count); ++i)
        {
            m_keys[i] = Key();
        }
    }
    
    void KeyBoard::Clear()
    {
        for (int i = 0; i < m_keys.size(); ++i)
        {
            m_keys[i].Release();
        }
    }
}