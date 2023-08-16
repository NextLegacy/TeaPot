#include "BHW/utils/console/Console.hpp"

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

    void Console::Write(const std::string& message)
    {
        std::cout << message;
    }

    void Console::WriteLine(const std::string& message)
    {
        std::cout << message << std::endl;
    }

    void Console::Pause()
    {
        std::cin.get();
    }

    std::string Console::ReadLine()
    {
        std::string line;
        std::getline(std::cin, line);

        return line;
    }
}