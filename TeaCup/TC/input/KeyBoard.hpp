#pragma once

#include <vector>

#include <map>

#include <BHW/utils/Assert.hpp>

#include "TC/input/Key.hpp"

namespace TC
{
    class KeyBoard
    {
    public:
        KeyBoard();
        
        Key& operator()(KeyCode code);
    
    private:
        void Initialize();
        void Clear     ();
        
    private:
        std::map<KeyCode, Key> m_keys;
    };
}