#include "TP/TeaPotEntryPoint.hpp"

#include <BHW/utils/console/Console.hpp>

#include "TP/application/TeaPot.hpp"

int BHW::EntryPoint(std::vector<std::string> args)
{
    BHW::Console::Initialize();

    TP::TeaPot teaPot;

    teaPot.Run();

    return 0;
}