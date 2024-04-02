#include "TeaPot/project/Project.hpp"

#include <BHW/utils/Debug.hpp>
#include <BHW/utils/io/FileHandler.hpp>
#include <BHW/utils/json/JSON.hpp>

#include "TeaPot/Resources.hpp"

#include <BHW/utils/reflection/Reflection.hpp>
#include <array>

#include <BHW/utils/dlls/DLL.hpp>

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

        BHW::Console::WriteLine("Loading project from: " + BHW::CombinePaths(path, "Project.teapot"));

        BHW::Console::WriteLine(BHW::ReadFile(BHW::CombinePaths(path, "Project.teapot")));

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

        //m_nativeScripts.Update();
    }

    void Project::BuildFinalExecutable() 
    {
        GenerateProjectFiles    ();
        GenerateFinalSourceFiles();

        CMakeBuild(GetProjectMetaData().Name);

        BHW::Console::WriteLine("Build complete!");
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

        GenerateBuildFile("CMakeLists.txt", Resources.at("CMakeLists.txt"),
            m_metaData.Directories.DistributionDirectory,
            BHW::GetExecutablePath(),
            m_metaData.Directories.SourceDirectory,
            m_metaData.Directories.ResourceDirectory,
            m_metaData.Name
        );

        GenerateBuildFile("NativeScripts.cpp", Resources.at("NativeScripts.cpp"));

        GenerateBuildFile("Tea.hpp", Resources.at("Tea.hpp"), GetProjectMetaData().GAPI.Include, GetProjectMetaData().GAPI.Name, "", "");
        GenerateBuildFile("Tea.cpp", Resources.at("Tea.cpp"));
        GenerateBuildFile("EntryPoint.cpp", Resources.at("EntryPoint.cpp"));

        GenerateBuildFile("Resources.hpp", Resources.at("Resources.hpp"));
    }

    void Project::GenerateFinalSourceFiles()
    {
        BHW::DLL dll(BHW::CombinePaths(GetProjectMetaData().Path, GetProjectMetaData().Directories.DistributionDirectory, "NativeScripts.dll"));
       
        dll.Load();

        if (!dll.IsLoaded())
        {
            BHW::Console::WriteLine("Failed to load native script core DLL!");
            return;
        }

        typedef void(*GetTypes)(BHW::TypeInfo**, uint32_t*);

        GetTypes GetComponents = dll.GetFunction<GetTypes>("GetComponents");
        GetTypes GetSystems    = dll.GetFunction<GetTypes>("GetSystems");

        if (!GetComponents || !GetSystems)
        {
            BHW::Console::WriteLine("Failed to get GetComponents or GetSystems function from native script core DLL!");
            return;
        }

        BHW::TypeInfo* components     = nullptr;
        uint32_t       componentCount = 0      ;

        BHW::TypeInfo* systems     = nullptr;
        uint32_t       systemCount = 0      ;

        GetComponents(&components, &componentCount);
        GetSystems   (&systems   , &systemCount   );

        std::string componentsString = "";
        for (uint32_t i = 0; i < componentCount; i++)
        {
            std::string componentString = std::string(components[i].Type.Name);

            componentString = componentString.substr(componentString.find(" ") + 1);

            componentsString += "        " + componentString + ",\n";
        }

        std::string systemsString = "";
        for (uint32_t i = 0; i < systemCount; i++)
        {
            std::string systemString = std::string(systems[i].Type.Name);

            systemString = systemString.substr(systemString.find(" ") + 1);

            systemsString += "        " + systemString + ",\n";
        }

    	// remove last comma
        componentsString = componentsString.substr(0, componentsString.size() - 2);
        systemsString    = systemsString   .substr(0, systemsString   .size() - 2);

        GenerateBuildFile("Tea.hpp", Resources.at("Tea.hpp"), GetProjectMetaData().GAPI.Include, GetProjectMetaData().GAPI.Name, systemsString, componentsString);
        GenerateBuildFile("Tea.cpp", Resources.at("Tea.cpp"));
        GenerateBuildFile("EntryPoint_final.cpp", Resources.at("EntryPoint.cpp"));

        dll.Unload();
    }
}