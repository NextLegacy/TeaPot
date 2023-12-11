#include "TP/TeaPotEntryPoint.hpp"

#include "TP/application/TeaPot.hpp"

int BHW::EntryPoint(std::vector<std::string> args)
{
    BHW::Console::WriteLine("TeaPot Engine - Version 0.0.1");

    TP::TeaPot teaPot;

    teaPot.Run();

    return 0;
}