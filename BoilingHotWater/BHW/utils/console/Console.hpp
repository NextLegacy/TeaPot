#pragma once

#ifdef _WIN32

    #include <Windows.h>
    
#endif

#include <iostream>
#include <string>

namespace BHW
{
    namespace Console
    {
        void InitConsole    ();
        void ShutdownConsole();

        void Write    (const std::string& message);
        void WriteLine(const std::string& message);

        void Pause();

        std::string ReadLine();
    }
}