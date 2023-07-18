#pragma once

#ifdef _WIN32

    #include <Windows.h>
    
#endif

#include <iostream>

namespace DigitalTea
{
    void InitConsole    ();
    void ShutdownConsole();
}