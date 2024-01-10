#include "TeaPot/project/Project.hpp"

#include <BHW/utils/Debug.hpp>
#include <BHW/utils/io/FileHandler.hpp>
#include <BHW/utils/json/JSON.hpp>

#include "TeaPot/project/ProjectTemplateFiles.hpp"


#include <BHW/utils/reflection/Reflection.hpp>
#include <array>

namespace TP
{
    Project::Project(const std::string& path) : m_nativeScripts(NativeScripts(*this))
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

        m_nativeScripts.Update();
    }

    void Project::BuildFinalExecutable() 
    {
        GenerateProjectFiles    ();
        GenerateFinalSourceFiles();

        CMakeBuild("Final");
    }

    void Project::CMakeBuild(std::string target)
    {
        std::system
        ((
            BHW::Format(
                R"("cd {}/build && {} ..)",
                BHW::CombinePaths(m_metaData.Path, ".teapot/.teabrewer"),
                m_metaData.CMakeCommand
            ) +
            " > " + BHW::CombinePaths(m_metaData.Path, m_metaData.Directories.LogDirectory + "/ConfigureLog" + target + ".txt") +
            " 2> " + BHW::CombinePaths(m_metaData.Path, m_metaData.Directories.LogDirectory + "/ConfigureErrorLog" + target + ".txt")
        ).c_str());

        std::system
        ((
            BHW::Format(
                R"("cd {}/build && {} --build . --config Release --target {} -j 30 --)",
                BHW::CombinePaths(m_metaData.Path, ".teapot/.teabrewer"),
                m_metaData.CMakeCommand,
                target
            ) +
            " > " + BHW::CombinePaths(m_metaData.Path, m_metaData.Directories.LogDirectory + "/BuildLog" + target + ".txt") +
            " 2> " + BHW::CombinePaths(m_metaData.Path, m_metaData.Directories.LogDirectory + "/BuildErrorLog" + target + ".txt")
        ).c_str());
    }

    template<typename ...TArgs>
    void Project::GenerateBuildFile(std::string fileName, std::string fileContent, TArgs&&... args)
    {
        BHW::WriteFile(BHW::CombinePaths(m_metaData.Path,".teapot/.teabrewer/" + fileName),
            BHW::Format(fileContent, std::forward<TArgs>(args)... )
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
        BHW::CreateFolder(relativePath(".teapot/.teabrewer/build"                  ));

        GenerateBuildFile("CMakeLists.txt", TP::ProjectTemplateFiles::CMakeLists,
            m_metaData.Directories.DistributionDirectory,
            BHW::GetExecutablePath(),
            m_metaData.Directories.SourceDirectory,
            m_metaData.Directories.ResourceDirectory,
            m_metaData.Name
        );

        GenerateBuildFile("EntryPoint_NativeScriptCore.cpp", TP::ProjectTemplateFiles::EntryPoint_NativeScripts);
    }

    void Project::GenerateFinalSourceFiles()
    {
        std::string nativeScripts       ;
        std::string nativeScriptIncludes;

        for (auto& nativeScript : m_nativeScripts.GetNativeScripts())
        {
            if (nativeScript.TypeInfo.InheritedClasses.at(BHW::TypeOf<TP::EventSubscriber>)

            nativeScripts += BHW::Format("    
            nativeScriptIncludes += BHW::Format("#include \"{}\"\n", nativeScript.SourceLocation);
        }
    }
}