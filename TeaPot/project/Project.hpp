#pragma once

#include "TeaPot/project/ProjectMetaData.hpp"

// --------------------------------------------------------------------------------------------
// Project Directory Structure (generated with cmd: tree /F /A)
// |   Project.teapot
// |
// +---.teapot
// |   |   Settings.teapot
// |   |
// |   +---.teabrewer
// |   |       CMakeLists.txt
// |   |       EntryPoint_Final.cpp
// |   |       EntryPoint_NativeScriptCore.cpp
// |   |       NativeScriptCore.dll
// |   |       Tea.cpp
// |   |       Tea.hpp
// |   |
// |   +---Distribution
// |   |   |   UntitledProject.exe
// |   |   |
// |   |   \---Resources
// |   \---Logs
// +---Resources
// \---Sources
//         NativeScript.cpp
//         NativeScript.hpp
// Source
// --------------------------------------------------------------------------------------------

namespace TP
{
    class Project
    {
    public:
        Project(const std::string& path = "");

    public:
        void Load(std::string path = "");
        void Save();

        void BuildNativeScripts();

        void BuildFinalExecutable();

        const ProjectMetaData& GetProjectMetaData() const { return m_metaData; }

    private:
        template<typename ...TArgs>
        void GenerateBuildFile(std::string fileName, TArgs&&... args);

        void GenerateProjectFiles();

        void GenerateFinalSourceFiles();

        void CMakeBuild(std::string target);

    private:
        ProjectMetaData m_metaData;
    };
}