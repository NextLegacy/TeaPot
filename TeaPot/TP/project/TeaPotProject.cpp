#include "TP/project/TeaPotProject.hpp"

#include <BHW/utils/io/FileHandler.hpp>
#include <BHW/utils/json/JSON.hpp>

#include <BHW/utils/console/Console.hpp>

namespace TP
{
    TeaPotProject::TeaPotProject()
    {
    }

    TeaPotProject::~TeaPotProject()
    {
    }

    void TeaPotProject::Save()
    {

    }

    void TeaPotProject::Reload()
    {

    }

    TeaPotProject CreateProject(const std::string& projectLocation)
    {
        TeaPotProject project;

        BHW::JSONObject projectJSON(BHW::JSONType::Object, 
            new std::map<std::string, BHW::JSONObject>
            {
                { "name", BHW::JSONObject(BHW::JSONType::String, (void*) new std::string("TestProject")) },
                { "someNumber", BHW::JSONObject(BHW::JSONType::UInt64, (void*) new uint64_t(42)) }
            }
        );

        std::string json = BHW::SerializeJSON(projectJSON);

        BHW::WriteFile(projectLocation + "/project.tp", json);

        return LoadProject(projectLocation);
    }

    TeaPotProject LoadProject(const std::string& projectLocation)
    {
        TeaPotProject project;
        
        std::string json = BHW::ReadFile(projectLocation + "/project.tp");

        BHW::JSONObject projectJSON = BHW::DeserializeJSON(json);
        auto projectJSONMap = projectJSON.Get<BHW::JSONMap>();

        for (auto& [key, value] : projectJSONMap)
        {
            BHW::Console::WriteLine(key + ": " + value.ToString());
        }

        project.m_projectName     = projectJSONMap["name"      ].Get<std::string>();
        project.m_someNumber      = projectJSONMap["someNumber"].Get<uint64_t>();
        project.m_projectLocation = projectLocation;

        return project;
    }

    TeaPotProject LoadOrCreateProject(const std::string& projectLocation)
    {
        return BHW::FileExists(projectLocation + "/project.tp") ? LoadProject(projectLocation) : CreateProject(projectLocation);
    }
}