#pragma once

#include <string>

#include <BHW/utils/reflection/Reflection.hpp>

namespace TP
{
    struct TeaPotProjectConfig
    {
        std::string m_name;
    };

    struct TeaPotProject
    {
        TeaPotProjectConfig m_config;

        std::string m_path;

        std::vector<std::string> m_scripts  ;
        std::vector<std::string> m_resources;
    };
}

/*
BHW_REFLECT(TP::TeaPotProjectConfig)

BHW_REFLECT_MEMBERS(TP::TeaPotProjectConfig,
    BHW_REFLECT_MEMBER("name", &TP::TeaPotProjectConfig::m_name)
)

*/