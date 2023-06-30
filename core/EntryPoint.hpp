#pragma once

#include "engine"

int engine_main();

#ifdef _WIN32

int APIENTRY WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine, int nCmdShow)
{
    return engine_main();
}

#else

int main(const int argc, const char** argv)
{
    return engine_main();
}

#endif