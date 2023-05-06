// Win Main
#include <Windows.h>
#include <iostream>

int main()
{
    std::cout << "Hello World!" << std::endl;
}

int WINAPI wWinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, PWSTR pCmdLine, int nCmdShow)
{
    std::cout << "Hello World!" << std::endl;
}