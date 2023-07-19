#include "Editor.hpp"

int EntryPoint()
{
    DigitalTea::InitConsole();

    std::cout << "Hello World!" << std::endl;
    
    DigitalTea::Engine* engine = new DigitalTea::Engine();

    engine->Initialize();

    std::cout << "Hello World!";

    engine->Run();

    engine->Terminate();

    DigitalTea::ShutdownConsole();

    return 0;
}