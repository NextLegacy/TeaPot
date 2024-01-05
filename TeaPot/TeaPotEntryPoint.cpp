#include "TeaPot/TeaPotEntryPoint.hpp"

#include "TeaPot/application/TeaPot.hpp"

int BHW::EntryPoint(std::vector<std::string> args)
{
    BHW::Console::WriteLine("TeaPot Engine - Version 0.0.1");

    TP::TeaPot teaPot;

    teaPot.Run();

    return 0;
}