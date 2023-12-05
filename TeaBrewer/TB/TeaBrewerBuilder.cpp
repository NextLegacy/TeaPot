#include "TB/TeaBrewerBuilder.hpp"

#include <BHW/utils/dlls/DLL.hpp>
#include <BHW/utils/io/FileHandler.hpp>

#include "TB/TeaBrewerUpdater.hpp"

namespace TB
{
    void CMakeBuild(const TeaBrewerConfig& config, BuildTarget target)
    {
        std::string targetStr = target == BuildTarget::ALL_BUILD ?  "ALL_BUILD" : config.Name + (target == BuildTarget::FINAL ? "" : "Core");

        std::system
        ((
            BHW::Format(
                R"("C:\Program Files\CMake\bin\cmake.EXE" --build {} --config Release --target {} -j 30 --)",
                config.GetBuildPath(),
                targetStr
            ) +
            " > " + config.GetLogPath() + "/CMakeBuild" + targetStr + ".log"
        ).c_str());
    }

    std::string ApplicationHeaderFile(const TeaBrewerConfig& config)
    {
        std::string systemsString   ;
        std::string componentsString;

        BHW::DLL dll(BHW::CombinePaths(config.GetDistPath(), config.Name + ".dll").c_str());

        typedef std::vector<std::string>(*_function)();
        auto getSystems    = dll.GetFunction<_function>("GetSystems"   );
        auto getComponents = dll.GetFunction<_function>("GetComponents");

        for (const auto& system    : getSystems   ()) systemsString    += "            " + system    + ",\n";
        for (const auto& component : getComponents()) componentsString += "        "     + component + ",\n";

        return BHW::Format
        (
            BHW::ReadFile(BHW::GetExecutablePath() + "/TeaBrewer/Application.hpp"), 
            config.GAPI_Include,
            config.GAPI        ,
            systemsString      ,
            componentsString
        );
    }

    void Build(const TeaBrewerConfig& config, BuildTarget target)
    {
        if (target == BuildTarget::ALL_BUILD)
        {
            Build(config, BuildTarget::INTERMEDIATE);
            
            BHW::WriteFile(BHW::CombinePaths(config.GetFinalPath(), "Application.hpp"), ApplicationHeaderFile(config));

            Build(config, BuildTarget::FINAL);

            return;
        }

        Update    (config        );
        CMakeBuild(config, target);
    }
}