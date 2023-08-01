#pragma once

#include <vector>

#include "window/input/Key.hpp"

#include <map>

namespace BHW
{
    class KeyBoard
    {
    public:
        KeyBoard(std::map<int, KeyCode> keyMap);
        
        Key operator()(KeyCode code);
        inline Key operator()(int code) { return operator()(m_keyMap[code]); }
    
    private:
        void Initialize();
        void Clear     ();
        
    private:
        std::vector<Key> m_keys;

        std::map<int, KeyCode> m_keyMap;
    };
}