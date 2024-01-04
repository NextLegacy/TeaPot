#include <BHW/EntryPoint>

#include "Application.hpp"

int BHW::EntryPoint(std::vector<std::string> args)
{
    Tea::Application app;

    app.Run();

    return 0;
}