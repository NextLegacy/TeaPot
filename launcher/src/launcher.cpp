#include "launcher.hpp"

int EntryPoint()
{
    DigitalTea::Engine* engine = new DigitalTea::Engine();

    engine->Initialize();

    engine->Run();

    engine->Terminate();

    return 0;
}