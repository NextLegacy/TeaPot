#pragma once

#include <string>

namespace TP
{
    class TeaPotProject
    {
    public:
        TeaPotProject();
        ~TeaPotProject();

        std::string m_projectLocation;
        std::string m_projectName;
        uint64_t    m_someNumber;
    };

    TeaPotProject CreateProject      (const std::string& projectLocation);
    TeaPotProject LoadProject        (const std::string& projectLocation);
    TeaPotProject LoadOrCreateProject(const std::string& projectLocation);
}