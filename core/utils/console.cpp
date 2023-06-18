#include "Console.hpp"

namespace DigitalTea
{
    void InitConsole()
    {
        #ifdef _WIN32

        AllocConsole();
        freopen("CONOUT$", "w", stdout);
        freopen("CONOUT$", "w", stderr);
        freopen("CONIN$", "r", stdin);

        #endif
    }

    void ShutdownConsole()
    {
        #ifdef _WIN32

        fclose(stdout);
        fclose(stderr);
        fclose(stdin);
        FreeConsole();
    
        #endif
    }
}