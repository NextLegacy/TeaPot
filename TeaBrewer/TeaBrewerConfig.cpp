#include "TeaBrewer/TeaBrewerConfig.hpp"

#include <BHW/utils/io/FileHandler.hpp>
#include <BHW/utils/json/JSON.hpp>
#include <algorithm>

namespace TB
{
    std::string TeaBrewerConfig::GetProjectPath     () const { return ProjectDirectory; }
    std::string TeaBrewerConfig::GetConfigPath      () const { return BHW::CombinePaths(ProjectDirectory, Name + ".teabrewer"); }
    std::string TeaBrewerConfig::GetLogPath         () const { return BHW::CombinePaths(ProjectDirectory, LogDirectory); }
    std::string TeaBrewerConfig::GetImagePath       () const { return BHW::CombinePaths(ProjectDirectory, Image); }
    std::string TeaBrewerConfig::GetDistPath        () const { return BHW::CombinePaths(ProjectDirectory, DistributionDirectory); }
    std::string TeaBrewerConfig::GetSourcePath      () const { return BHW::CombinePaths(ProjectDirectory, SourceDirectory); }
    std::string TeaBrewerConfig::GetResourcePath    () const { return BHW::CombinePaths(ProjectDirectory, ResourceDirectory); }
    std::string TeaBrewerConfig::GetTeaPath         () const { return BHW::CombinePaths(ProjectDirectory, ".tea"); }
    std::string TeaBrewerConfig::GetIntermediatePath() const { return BHW::CombinePaths(GetTeaPath(), "Intermediate"); }
    std::string TeaBrewerConfig::GetFinalPath       () const { return BHW::CombinePaths(GetTeaPath(), "Final"       ); }
    std::string TeaBrewerConfig::GetBuildPath       () const { return BHW::CombinePaths(GetTeaPath(), "Build"       ); }

    std::string GetProjectPath(const std::string& path)
    {
        if (!BHW::IsAbsolutePath(path)) return GetProjectPath(BHW::CombinePaths(BHW::GetCurrentPath(), path));
        if ( BHW::IsFile        (path)) return BHW::GetFileDirectory(path);
        if (!BHW::PathExists    (path)) BHW::CreateFolder(path);

        return path;
    }

    std::string GetConfigPath(const std::string& path)
    {
        if (BHW::IsFile(path)) return path;

        std::string projectPath = GetProjectPath(path);

        if (projectPath == "") return "";

        std::vector<std::string> files = BHW::GetFilesInFolder(projectPath, ".teabrewer");

        if (files.size() == 0) return "";

        return files[0];
    }

    TeaBrewerConfig LoadConfig(const std::string& path)
    {
        std::string projectPath   = GetProjectPath(path      );
        std::string configPath    = GetConfigPath (path      );
        std::string configContent = BHW::ReadFile (configPath);

        TeaBrewerConfig config = BHW::Deserialize<TeaBrewerConfig>(configContent);

        config.ProjectDirectory = projectPath;

        std::vector<std::string> sources = BHW::GetFilesInFolder(config.ProjectDirectory + "\\" + config.SourceDirectory, ".cpp");
        std::vector<std::string> headers = BHW::GetFilesInFolder(config.ProjectDirectory + "\\" + config.SourceDirectory, ".hpp");

        std::vector<std::string> files;
        files.insert(files.end(), sources.begin(), sources.end());
        files.insert(files.end(), headers.begin(), headers.end());

        for (std::string& file : files) std::replace(file.begin(), file.end(), '\\', '/');

        config.SourceFiles = sources;
        config.HeaderFiles = headers;
        config.Files = files;

        

        return config;
    }

    TeaBrewerConfig CreateConfig(const std::string& path)
    {
        TeaBrewerConfig config;

        config.ProjectDirectory = GetProjectPath(path);

        BHW::WriteFile(BHW::CombinePaths(config.ProjectDirectory, ".teabrewer"), BHW::Serialize(config));

        return config;
    }

    TeaBrewerConfig LoadOrCreateConfig(const std::string& path)
    {
        if (GetProjectPath(path) == "") return TeaBrewerConfig();
        if (GetConfigPath (path) == "") return CreateConfig(path);

        return LoadConfig(path);
    }
}