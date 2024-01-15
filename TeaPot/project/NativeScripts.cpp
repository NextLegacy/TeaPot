#include "TeaPot/project/NativeScripts.hpp"

#include <BHW/utils/reflection/TypeInfo.hpp>
#include <BHW/utils/io/FileHandler.hpp>

#include "TeaPot/project/Project.hpp"

namespace TP
{
    NativeScript::NativeScript(const std::vector<BHW::TypeInfo>& types, const std::string& sourceLocation)
        : Types(types), SourceLocation(sourceLocation)
    {

    }

    NativeScripts::NativeScripts(const Project& project)
        : m_project(project), m_nativeScriptCoreDLL()
    {

    }

    void NativeScripts::Update()
    {
        return;
        m_nativeScriptCoreDLL.SetPath(BHW::CombinePaths(m_project.GetProjectMetaData().Path, m_project.GetProjectMetaData().Directories.DistributionDirectory, "NativeScripts.dll"));
        m_nativeScriptCoreDLL.Unload();
    
        m_nativeScripts.clear();

        m_nativeScriptCoreDLL.Load();

        if (!m_nativeScriptCoreDLL.IsLoaded())
        {
            BHW::Console::WriteLine("Failed to load native script core DLL!");
            return;
        }

        typedef std::vector<BHW::TypeInfo>&(*GetTypes)();
        typedef uint32_t(*GetTypesCount)();

        GetTypes getComponents = m_nativeScriptCoreDLL.GetFunction<GetTypes>("GetComponents");
        GetTypes getSystems    = m_nativeScriptCoreDLL.GetFunction<GetTypes>("GetSystems");

        GetTypesCount getComponentsCount = m_nativeScriptCoreDLL.GetFunction<GetTypesCount>("GetCount");

        if (!getComponents)
        {
            BHW::Console::WriteLine("Failed to get GetComponents function from native script core DLL!");
            return;
        }

        std::vector<BHW::TypeInfo>& types = getComponents();

        BHW::Console::WriteLine("Types: " + std::to_string(types.size()));

        std::string componentsString = "";
        for (const BHW::TypeInfo& type : types)
        {
            std::string componentString = std::string(type.Type.Name);

            componentString = componentString.substr(componentString.find(" ") + 1);

            componentsString += componentString + ",\n";
        }

        
    }
}