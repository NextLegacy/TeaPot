#pragma once

#include "core.h"

namespace engine
{
    class Console
    {
    public:
        Console();
        ~Console();

        void print  (std::string str);
        void println(std::string str);

        void clear();
        
        std::string getInput();
    };
}