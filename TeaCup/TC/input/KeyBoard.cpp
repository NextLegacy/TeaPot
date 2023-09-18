#include "TC/input/KeyBoard.hpp"

namespace TC
{
    KeyBoard::KeyBoard()
    {
        Initialize();
    }
    
    Key& KeyBoard::operator()(KeyCode code)
    {
        return m_keys[code];
    }
    
    void KeyBoard::Initialize()
    {
        for (int i = 0; i < KeyCodeCount; ++i)
        {
            m_keys[static_cast<KeyCode>(i)] = Key();
        }
    }
    
    void KeyBoard::Clear()
    {
        for (int i = 0; i < m_keys.size(); ++i)
        {
            //m_keys[i].Release();
        }
    }
}