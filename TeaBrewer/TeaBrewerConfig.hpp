#pragma once

#include <BHW/utils/reflection/Reflection.hpp>

#include <string>
#include <vector>

namespace TB
{
    struct TeaBrewerConfig
    {
        std::string Name                  = "UntitledProject"                       ;
        std::string Author                = "Unknown Author"                        ;
        std::string Description           = "No Description"                        ;
        std::string Version               = "0.0.0"                                 ;
        std::string License               = "No License"                            ;

        std::string ProjectDirectory      = ""                                      ;

        bool IsConsoleApplication         = false                                   ;

        std::string GAPI                  = "TC::GLFW_OpenGL_DearImGui"             ;
        std::string GAPI_Include          = "TC/gapi/impl/GLFW_OpenGL_DearImGui.hpp";

        std::string Image                                                           ;
        std::string DistributionDirectory = "Dist"                                  ;
        std::string SourceDirectory       = "Sources"                               ;
        std::string ResourceDirectory     = "Resources"                             ;
        std::string LogDirectory          = "Logs"                                  ;

        std::vector<std::string> Dependencies;
        
        std::vector<std::string> SourceFiles;
        std::vector<std::string> HeaderFiles;
        std::vector<std::string> Files      ;

        constexpr bool IsInvalid() { return ProjectDirectory == ""; }

        std::string GetProjectPath     () const;
        std::string GetConfigPath      () const;
        std::string GetLogPath         () const;
        std::string GetImagePath       () const;
        std::string GetDistPath        () const;
        std::string GetSourcePath      () const;
        std::string GetResourcePath    () const;
        std::string GetTeaPath         () const;
        std::string GetIntermediatePath() const;
        std::string GetFinalPath       () const;
        std::string GetBuildPath       () const;
    };

    TeaBrewerConfig LoadConfig        (const std::string& path);
    TeaBrewerConfig CreateConfig      (const std::string& path);

    TeaBrewerConfig LoadOrCreateConfig(const std::string& path);
}

BHW_REFLECT(TB::TeaBrewerConfig)

BHW_REFLECT_MEMBERS(TB::TeaBrewerConfig,
    BHW_REFLECT_MEMBER("Name"                 , &TB::TeaBrewerConfig::Name                 ),
    BHW_REFLECT_MEMBER("Author"               , &TB::TeaBrewerConfig::Author               ),
    BHW_REFLECT_MEMBER("Description"          , &TB::TeaBrewerConfig::Description          ),
    BHW_REFLECT_MEMBER("Version"              , &TB::TeaBrewerConfig::Version              ),
    BHW_REFLECT_MEMBER("License"              , &TB::TeaBrewerConfig::License              ),
  //BHW_REFLECT_MEMBER("ProjectDirectory"     , &TB::TeaBrewerConfig::ProjectDirectory     ),
    BHW_REFLECT_MEMBER("IsConsoleApplication" , &TB::TeaBrewerConfig::IsConsoleApplication ),
    BHW_REFLECT_MEMBER("GAPI"                 , &TB::TeaBrewerConfig::GAPI                 ),
    BHW_REFLECT_MEMBER("Image"                , &TB::TeaBrewerConfig::Image                ),
    BHW_REFLECT_MEMBER("DistributionDirectory", &TB::TeaBrewerConfig::DistributionDirectory),
    BHW_REFLECT_MEMBER("SourceDirectory"      , &TB::TeaBrewerConfig::SourceDirectory      ),
    BHW_REFLECT_MEMBER("ResourceDirectory"    , &TB::TeaBrewerConfig::ResourceDirectory    ),
    BHW_REFLECT_MEMBER("Dependencies"         , &TB::TeaBrewerConfig::Dependencies         )
)