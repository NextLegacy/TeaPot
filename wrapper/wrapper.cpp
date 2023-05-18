#include "wrapper.h"

#include "core.h"

int APIENTRY WinMain(HINSTANCE hInstance,
    HINSTANCE hPrevInstance,
    LPSTR lpCmdLine, int nCmdShow)
{
    AllocConsole();
    freopen("conin$","r",stdin);
    freopen("conout$","w",stdout);
    freopen("conout$","w",stderr);
    printf("Hello World!\n");

    printf("Hello World!\n");
    std::cin.get();

    std::string filePath = "engine_java_core.jar";

    engine::process::runJar(filePath);

    return 0;
}