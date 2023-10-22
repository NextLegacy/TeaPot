#include "TP/TeaPotEntryPoint.hpp"

int BHW::EntryPoint(std::vector<std::string> args)
{
    BHW::Console::Initialize();

    TP::TeaPot app = TP::TeaPot();
    app.Run();

    return 0; 
}