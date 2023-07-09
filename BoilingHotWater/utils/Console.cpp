#include "Console.hpp"

namespace BHW
{
    void Console::InitConsole()
    {
        #ifdef _WIN32

        AllocConsole();
    
        freopen("CONOUT$", "w", stdout);
        freopen("CONOUT$", "w", stderr);
        freopen("CONIN$", "r", stdin);

        HWND consoleWindow = GetConsoleWindow();
        SetForegroundWindow(consoleWindow);
        SetFocus(consoleWindow);

        #endif
    }

    void Console::ShutdownConsole()
    {
        #ifdef _WIN32

        fclose(stdout);
        fclose(stderr);
        fclose(stdin);
        FreeConsole();
    
        #endif
    }

    void Console::Log(const std::string& message)
    {
        std::cout << message;
    }

    void Console::LogLn(const std::string& message)
    {
        std::cout << message << std::endl;
    }
}