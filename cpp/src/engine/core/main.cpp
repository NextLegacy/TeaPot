// Win Main
#include "main.h"
#include <iostream>
#include <Windows.h>

namespace Engine
{
    class Application
    {
    public:
        void Run()
        {
            AllocConsole();
            freopen("CONOUT$", "w", stdout);
            freopen("CONOUT$", "w", stderr);
            freopen("CONIN$", "r", stdin);

            std::cout << "Hello World!" << std::endl;
        }
    };
}

int main(int argc, char* argv[])
{
    Engine::Application app;
    
    app.Run();

    return 0;
}