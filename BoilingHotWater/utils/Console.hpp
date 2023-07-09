#pragma once

#ifdef _WIN32

    #include <Windows.h>
    
#endif

#include <iostream>

namespace BHW
{
    namespace Console
    {
        void InitConsole    ();
        void ShutdownConsole();

        void Log            (const std::string& message);
        void LogLn          (const std::string& message);
    }
}