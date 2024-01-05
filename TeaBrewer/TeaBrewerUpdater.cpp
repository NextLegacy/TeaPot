#include "TeaBrewer/TeaBrewerUpdater.hpp"

#include <BHW/utils/io/FileHandler.hpp>
#include <BHW/utils/String.hpp>

#include <algorithm>

namespace TB
{
    std::string IntermediateCMakeListsFile()
    {
        return BHW::ReadFile(BHW::GetExecutablePath() + "/ProjectTemplateFiles/Intermediate.cmake");
    }

    std::string FinalCMakeListsFile(const TeaBrewerConfig& config)
    {
        return BHW::ReadFile(BHW::GetExecutablePath() + "/ProjectTemplateFiles/Final.cmake");
    }

    std::string ProjectCMakeListsFile(const TeaBrewerConfig& config)
    {
        std::string sourcesString     ;
        std::string dependenciesString;

        for (const std::string& source     : config.Files       ) sourcesString      += "    " + source     + "\n";
        for (const std::string& dependency : config.Dependencies) dependenciesString += "    " + dependency + "\n";

        std::string executablePath = BHW::GetExecutablePath();
        std::replace(executablePath.begin(), executablePath.end(), '\\', '/');

        return BHW::Format
        (
            BHW::ReadFile(BHW::GetExecutablePath() + "/ProjectTemplateFiles/Project.cmake"),
            config.Name                 ,
            config.DistributionDirectory,
            config.IsConsoleApplication ? "" : "WIN32",
            sourcesString               ,
            dependenciesString          ,
            executablePath  
        );
    }

    std::string EntryPointSourceFile(const TeaBrewerConfig& config)
    {
        return BHW::ReadFile(BHW::GetExecutablePath() + "/ProjectTemplateFiles/EntryPoint.cpp");
    }

    std::string ExportHeaderFile(const TeaBrewerConfig& config)
    {
        return BHW::ReadFile(BHW::GetExecutablePath() + "/ProjectTemplateFiles/Exports.hpp");
    }

    std::string ExportSourceFile(const TeaBrewerConfig& config)
    {
        return BHW::ReadFile(BHW::GetExecutablePath() + "/ProjectTemplateFiles/Exports.cpp");
    }

    std::string DLLEntryPointSourceFile(const TeaBrewerConfig& config)
    {
        return BHW::ReadFile(BHW::GetExecutablePath() + "/ProjectTemplateFiles/DLLEntryPoint.cpp");
    }

    void Update(const TeaBrewerConfig& config)
    {
        BHW::CreateFolder(config.GetTeaPath     ());
        BHW::CreateFolder(config.GetDistPath    ());
        BHW::CreateFolder(config.GetSourcePath  ());
        BHW::CreateFolder(config.GetResourcePath());
        BHW::CreateFolder(config.GetLogPath     ());

        BHW::CreateFolder(BHW::CombinePaths(config.GetTeaPath(), "Intermediate"));
        BHW::CreateFolder(BHW::CombinePaths(config.GetTeaPath(), "Final"       ));

        BHW::WriteFile(BHW::CombinePaths(config.GetIntermediatePath(), "CMakeLists.txt"   ), IntermediateCMakeListsFile(      ));
        BHW::WriteFile(BHW::CombinePaths(config.GetIntermediatePath(), "Exports.hpp"      ), ExportHeaderFile          (config));
        BHW::WriteFile(BHW::CombinePaths(config.GetIntermediatePath(), "Exports.cpp"      ), ExportSourceFile          (config));
        BHW::WriteFile(BHW::CombinePaths(config.GetIntermediatePath(), "DLLEntryPoint.cpp"), DLLEntryPointSourceFile   (config));

        BHW::WriteFile(BHW::CombinePaths(config.GetFinalPath       (), "CMakeLists.txt"   ), FinalCMakeListsFile       (config));
        BHW::WriteFile(BHW::CombinePaths(config.GetFinalPath       (), "Application.hpp"  ), ""                                );
        BHW::WriteFile(BHW::CombinePaths(config.GetFinalPath       (), "EntryPoint.cpp"   ), EntryPointSourceFile      (config));

        BHW::WriteFile(BHW::CombinePaths(config.GetTeaPath         (), "CMakeLists.txt"   ), ProjectCMakeListsFile     (config));

        std::system((
            BHW::Format(R"(cmake --no-warn-unused-cli -DCMAKE_EXPORT_COMPILE_COMMANDS:BOOL=TRUE -S{} -B{} -G "Visual Studio 17 2022" -T host=x64 -A x64)",
                config.GetTeaPath  (),
                config.GetBuildPath()
            ) +
            " > " + config.GetLogPath() + "/CMakeConfigure.log"
        ).c_str());
    }
}