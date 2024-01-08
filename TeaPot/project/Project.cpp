#include "TeaPot/project/Project.hpp"

#include <BHW/utils/Debug.hpp>
#include <BHW/utils/io/FileHandler.hpp>
#include <BHW/utils/json/JSON.hpp>

namespace TP
{
    Project::Project(const std::string& path)
    {
        Load(path);
    }

    void Project::Load(std::string path) 
    { 
        if (path.empty()) return;

        for (auto& c : path)
        {
            if (c == '\\') c = '/';
        }

        BHW::Console::WriteLine("Loading project from: " + BHW::CombinePaths(path, "Project.teapot"));

        if (!BHW::FileExists(BHW::CombinePaths(path, "Project.teapot")))
        {
            BHW::WriteFile(BHW::CombinePaths(path, "Project.teapot"), BHW::Serialize(m_metaData));
        }

        m_metaData = BHW::Deserialize<ProjectMetaData>(BHW::ReadFile(BHW::CombinePaths(path, "Project.teapot")));
        m_metaData.Path = path;

        GenerateProjectFiles();
    }

    void Project::Save() 
    {
        GenerateProjectFiles();
    }

    void Project::BuildNativeScripts() 
    {
        CMakeBuild("NativeScripts");
    }

    void Project::BuildFinalExecutable() 
    {
        GenerateProjectFiles    ();
        GenerateFinalSourceFiles();

        CMakeBuild("Final");
    }

    void Project::CMakeBuild(std::string target)
    {
        BHW::Console::WriteLine("Building project: " + target);

        BHW::Console::WriteLine(BHW::Format(
            R"("{} --build {} --config Release --target {} -j 30 --)",
            m_metaData.CMakeCommand,
            BHW::CombinePaths(m_metaData.Path, "/.teapot/.teabrewer"),
            target
        ));

        std::system
        ((
            BHW::Format(
                R"("{} --build {} --config Release --target {} -j 30 --)",
                m_metaData.CMakeCommand,
                BHW::CombinePaths(m_metaData.Path, ".teapot/.teabrewer"),
                target
            ) +
            " > " + BHW::CombinePaths(m_metaData.Path, m_metaData.Directories.LogDirectory + "/BuildLog.txt") +
            " 2> " + BHW::CombinePaths(m_metaData.Path, m_metaData.Directories.LogDirectory + "/BuildErrorLog.txt")
        ).c_str());
    }

    template<typename ...TArgs>
    void Project::GenerateBuildFile(std::string fileName, TArgs&&... args)
    {
        BHW::WriteFile(BHW::CombinePaths(m_metaData.Path,".teapot/.teabrewer/" + fileName),
            BHW::Format(BHW::ReadFile(BHW::CombinePaths(BHW::CombinePaths(BHW::GetExecutablePath(), "ProjectTemplateFiles"), fileName)),
                std::forward<TArgs>(args)...
            )
        );
    }

    void Project::GenerateProjectFiles()
    {
        BHW::Console::WriteLine(m_metaData.Path);

        BHW::WriteFile(m_metaData.Path, BHW::Serialize(m_metaData));

        auto relativePath = [m_metaData = m_metaData](const std::string& path) -> std::string
        {
            return BHW::CombinePaths(m_metaData.Path, path);
        };

        BHW::CreateFolder(relativePath(m_metaData.Directories.DistributionDirectory));
        BHW::CreateFolder(relativePath(m_metaData.Directories.SourceDirectory      ));
        BHW::CreateFolder(relativePath(m_metaData.Directories.ResourceDirectory    ));
        BHW::CreateFolder(relativePath(m_metaData.Directories.LogDirectory         ));
        BHW::CreateFolder(relativePath(".teapot"                                   ));
        BHW::CreateFolder(relativePath(".teapot/.teabrewer"                        ));

        GenerateBuildFile("CMakeLists.txt", 
            m_metaData.Directories.DistributionDirectory,
            BHW::GetExecutablePath(),
            m_metaData.Directories.SourceDirectory,
            m_metaData.Directories.ResourceDirectory,
            m_metaData.Name
        );

        GenerateBuildFile("EntryPoint_NativeScriptCore.cpp");
    }

    void Project::GenerateFinalSourceFiles()
    {
        GenerateBuildFile("EntryPoint_Final.cpp");
        GenerateBuildFile("Tea.cpp");
        GenerateBuildFile("Tea.hpp");
    }
}