#pragma once

#include <string>

#include <BHW/utils/reflection/Reflection.hpp>

namespace TP
{
    struct TeaPotProject
    {
        uint64_t m_version = 0;
        std::string m_name = "Untitled Project";

        std::vector<std::string> m_sourceFiles  ;
        std::vector<std::string> m_resourceFiles;
    };
}

BHW_REFLECT(TP::TeaPotProject)
BHW_REFLECT_MEMBERS(TP::TeaPotProject,
    BHW_REFLECT_MEMBER("version", &TP::TeaPotProject::m_version),
    BHW_REFLECT_MEMBER("name", &TP::TeaPotProject::m_name)
)
