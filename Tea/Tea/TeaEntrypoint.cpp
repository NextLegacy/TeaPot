#include "Tea/TeaEntryPoint.hpp"

int BHW::EntryPoint(std::vector<std::string> args)
{
    BHW::Console::Initialize();

    Tea::Tea app;
    app.Run();

    return 0; 
}