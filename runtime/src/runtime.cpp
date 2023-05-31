#include "runtime.h"

#include "core.h"

int APIENTRY WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine, int nCmdShow)
{
    AllocConsole();
    freopen("conin$","r",stdin);
    freopen("conout$","w",stdout);
    freopen("conout$","w",stderr);

    // enable ansi escape codes
    HANDLE hOut = GetStdHandle(STD_OUTPUT_HANDLE);
    DWORD dwMode = 0;
    GetConsoleMode(hOut, &dwMode);
    dwMode |= ENABLE_VIRTUAL_TERMINAL_PROCESSING;
    SetConsoleMode(hOut, dwMode);

    std::cin.get();

    printf("Hello World!\n");

    std::cout << 0x00FF00 << std::endl;
    engine::ansi ansi(engine::ansi::foreground(0x00FF00));

    printf(ansi("Hello World!\n").c_str());

    //std::string filePath = "engine_java_demo.jar;./engine_java_core.jar";
    //std::string className = " demo.App";
//
    //engine::process::runJar(filePath, className, std::filesystem::current_path().string());

    std::cin.get();

    return 0;
}