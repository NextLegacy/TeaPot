#pragma once

#include <BHW/utils/reflection/Reflection.hpp>

#include <string>

namespace TP
{
    struct ProjectGAPI
    {
        std::string Name                  = "TC::GLFW_OpenGL_DearImGui"             ;
        std::string Include               = "TC/gapi/impl/GLFW_OpenGL_DearImGui.hpp";
    };

    struct ProjectDirectories
    {
        std::string DistributionDirectory = ".teapot/Distribution"                  ; 
        std::string SourceDirectory       = "Source"                                ;
        std::string ResourceDirectory     = "Resource"                              ;
        std::string LogDirectory          = ".teapot/Log"                           ;
    };

    struct ProjectMetaData
    {
        std::string        Path           = ""                                      ;
        std::string        Name           = "UntitledProject"                       ;
    
        std::string        Author         = "Unknown Author"                        ;
        std::string        Description    = "Hello World!"                          ;
        std::string        Version        = "1.0.0"                                 ;
        std::string        License        = "MIT"                                   ;

        std::string        Icon           = ""                                      ;

        ProjectGAPI        GAPI           = {}                                      ;
        ProjectDirectories Directories    = {}                                      ;

        std::string        CMakeCommand   = "cmake"                                 ;
    };
}

BHW_REFLECT(TP::ProjectMetaData)
BHW_REFLECT_MEMBERS(TP::ProjectMetaData,
    BHW_REFLECT_MEMBER("Name"        , &TP::ProjectMetaData::Name        ),
    BHW_REFLECT_MEMBER("Author"      , &TP::ProjectMetaData::Author      ),
    BHW_REFLECT_MEMBER("Description" , &TP::ProjectMetaData::Description ),
    BHW_REFLECT_MEMBER("Version"     , &TP::ProjectMetaData::Version     ),
    BHW_REFLECT_MEMBER("License"     , &TP::ProjectMetaData::License     ),
    BHW_REFLECT_MEMBER("Icon"        , &TP::ProjectMetaData::Icon        ),
    BHW_REFLECT_MEMBER("GAPI"        , &TP::ProjectMetaData::GAPI        ),
    BHW_REFLECT_MEMBER("Directories" , &TP::ProjectMetaData::Directories ),
    BHW_REFLECT_MEMBER("CMakeCommand", &TP::ProjectMetaData::CMakeCommand)
)

BHW_REFLECT(TP::ProjectGAPI);
BHW_REFLECT_MEMBERS(TP::ProjectGAPI,
    BHW_REFLECT_MEMBER("Name"       , &TP::ProjectGAPI::Name           ),
    BHW_REFLECT_MEMBER("Include"    , &TP::ProjectGAPI::Include        )
)

BHW_REFLECT(TP::ProjectDirectories);
BHW_REFLECT_MEMBERS(TP::ProjectDirectories,
    BHW_REFLECT_MEMBER("DistributionDirectory", &TP::ProjectDirectories::DistributionDirectory),
    BHW_REFLECT_MEMBER("SourceDirectory"      , &TP::ProjectDirectories::SourceDirectory      ),
    BHW_REFLECT_MEMBER("ResourceDirectory"    , &TP::ProjectDirectories::ResourceDirectory    ),
    BHW_REFLECT_MEMBER("LogDirectory"         , &TP::ProjectDirectories::LogDirectory         )
)