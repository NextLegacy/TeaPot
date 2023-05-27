#include "wrapper.h"

#include "core.h"

int APIENTRY WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine, int nCmdShow)
{
    AllocConsole();
    freopen("conin$","r",stdin);
    freopen("conout$","w",stdout);
    freopen("conout$","w",stderr);

    std::cin.get();

    std::string filePath = "engine_java_demo.jar;./engine_java_core.jar";
    std::string className = " demo.App";

    engine::process::runJar(filePath, className, std::filesystem::current_path().string());

    std::cin.get();

    return 0;
}