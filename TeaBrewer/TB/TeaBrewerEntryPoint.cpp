#include "TB/TeaBrewerEntryPoint.hpp"

#include <BHW/utils/io/FileHandler.hpp>

#include "TB/TeaBrewer.hpp"

enum class TeaBrewerAction
{
    HELP,
    UPDATE,
    BUILD,
    CREATE,
    BUILD_OR_CREATE
};

struct TeaBrewerArguments
{
    TeaBrewerAction Action          ;
    std::string     ProjectDirectory;
};

TeaBrewerArguments ProcessArguments(std::vector<std::string> args)
{
    args.erase(args.begin());

    return
    {
        args.size() == 0        ? TeaBrewerAction::BUILD_OR_CREATE : 
        args.size() >  2        ? TeaBrewerAction::HELP            :
        args[0]     == "help"   ? TeaBrewerAction::HELP            :
        args[0]     == "update" ? TeaBrewerAction::UPDATE          :
        args[0]     == "build"  ? TeaBrewerAction::BUILD           :
        args[0]     == "create" ? TeaBrewerAction::CREATE          : TeaBrewerAction::HELP,

        args.size() != 2 ? BHW::GetCurrentPath() :
        args.size() == 2 ? args[1]               : ""
    };
}

int BHW::EntryPoint(std::vector<std::string> args)
{
    TeaBrewerArguments arguments = ProcessArguments(args);

    if (arguments.Action == TeaBrewerAction::HELP)
    {
        BHW::Console::WriteLine("Usage: teabrewer [optional: help | update | (default if present) build | (default) create] [optional: project directory (default is current directory)]");

        return 0;
    }

    TB::TeaBrewer teaBrewer(arguments.ProjectDirectory);

    if (arguments.Action == TeaBrewerAction::CREATE) return 0;
    if (arguments.Action == TeaBrewerAction::UPDATE) teaBrewer.Update();
    if (arguments.Action == TeaBrewerAction::BUILD ) teaBrewer.Build (TB::BuildTarget::ALL_BUILD);

    return 0;
}