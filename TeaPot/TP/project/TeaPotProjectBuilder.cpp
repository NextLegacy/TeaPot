#include "TP/project/TeaPotProjectBuilder.hpp"

#include <BHW/utils/json/JSON.hpp>
#include <BHW/utils/io/FileHandler.hpp>

namespace TP
{
    std::string ScriptProjectCmakeFile(const std::string& libsPath, TeaPotProject& project)
    {
        std::string sourceFiles;

        for (auto& file : project.m_sourceFiles)
        {
            sourceFiles += "    " + file + "\n";
        }

        std::string executableFileLocation;

        std::string content = 
        "project(Tea)"                                                           "\n"
                                                                                 "\n"
        "set(CMAKE_CXX_STANDARD 23)"                                             "\n"
        "set(CMAKE_CXX_STANDARD_REQUIRED ON)"                                    "\n"
        "set(CMAKE_CXX_EXTENSIONS OFF)"                                          "\n"
                                                                                 "\n"
        "add_library(Tea SHARED"                                                 "\n"
            + sourceFiles +                                                      "\n"
        ")"                                                                      "\n"
                                                                                 "\n"
        "target_include_directories(Tea PUBLIC"                                  "\n"
        "    ${CMAKE_CURRENT_SOURCE_DIR}/../../Sources"                          "\n"
        ")"                                                                      "\n"
                                                                                 "\n"
        "target_link_libraries(Tea PUBLIC "                                      "\n"
        "    " + libsPath + "/BoilingHotWater.lib" +                             "\n"
        "    " + libsPath + "/TeaCup.lib" +                                      "\n"
        "    " + libsPath + "/Tea.lib" +                                         "\n"
        ")"                                                                      "\n"
                                                                                 "\n"
        "set_target_properties(Tea PROPERTIES "                                  "\n"
        "    ARCHIVE_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/dist/"         "\n"
        "    LIBRARY_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/dist/"         "\n"
        "    RUNTIME_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/dist/"         "\n"
        "    ARCHIVE_OUTPUT_DIRECTORY_DEBUG ${CMAKE_CURRENT_SOURCE_DIR}/dist/"   "\n"
        "    LIBRARY_OUTPUT_DIRECTORY_DEBUG ${CMAKE_CURRENT_SOURCE_DIR}/dist/"   "\n"
        "    RUNTIME_OUTPUT_DIRECTORY_DEBUG ${CMAKE_CURRENT_SOURCE_DIR}/dist/"   "\n"
        "    ARCHIVE_OUTPUT_DIRECTORY_RELEASE ${CMAKE_CURRENT_SOURCE_DIR}/dist/" "\n"
        "    LIBRARY_OUTPUT_DIRECTORY_RELEASE ${CMAKE_CURRENT_SOURCE_DIR}/dist/" "\n"
        "    RUNTIME_OUTPUT_DIRECTORY_RELEASE ${CMAKE_CURRENT_SOURCE_DIR}/dist/" "\n"
        ")";

        return content;
    }

    TeaPotProject LoadProject(const std::string& path)
    {
        TeaPotProject project = BHW::Deserialize<TeaPotProject>(BHW::ReadFile(path + "/project.teapot"));

        std::vector<std::string> sourceFiles = BHW::GetFilesInFolder(path + "/Source", ".cpp");
        std::vector<std::string> headerFiles = BHW::GetFilesInFolder(path + "/Source", ".hpp");

        project.m_sourceFiles.insert(project.m_sourceFiles.end(), sourceFiles.begin(), sourceFiles.end());
        project.m_sourceFiles.insert(project.m_sourceFiles.end(), headerFiles.begin(), headerFiles.end());

        return project;
    }

    TeaPotProject BuildProject(const std::string& libsPath, const std::string& path)
    {

        TeaPotProject project;

        if (BHW::FileExists(path + "/project.teapot")) project = LoadProject(path);

        BHW::WriteFile(path + "/project.teapot", BHW::ToString(project));

        BHW::CreateFolder(path + "/Source");
        BHW::CreateFolder(path + "/Resources");

        BHW::CreateFolder(path + "/Dist");

        BHW::CreateFolder(path + "/Generated");
        BHW::CreateFolder(path + "/Generated/ScriptProject");
        BHW::CreateFolder(path + "/Generated/ScriptProject/src");
        BHW::CreateFolder(path + "/Generated/ScriptProject/rsc");
        BHW::CreateFolder(path + "/Generated/ScriptProject/build");
        BHW::CreateFolder(path + "/Generated/ScriptProject/dist");
        BHW::CreateFolder(path + "/Generated/FinalProject");
        BHW::CreateFolder(path + "/Generated/FinalProject/src");
        BHW::CreateFolder(path + "/Generated/FinalProject/rsc");
        BHW::CreateFolder(path + "/Generated/FinalProject/build");

        BHW::CreateFile(path + "/Generated/ScriptProject/CMakeLists.txt");
        BHW::CreateFile(path + "/Generated/FinalProject/CMakeLists.txt");

        BHW::WriteFile(path + "/Generated/ScriptProject/CMakeLists.txt", ScriptProjectCmakeFile(libsPath, project));

        return project;
    }
}