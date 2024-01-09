#include <BHW/EntryPoint.hpp>

#include "Tea.hpp"

int BHW::EntryPoint(std::vector<std::string> args)
{
    Tea::Application app;
    app.Run();

    return 0;
}