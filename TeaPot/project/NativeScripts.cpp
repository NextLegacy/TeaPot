#include "TeaPot/project/NativeScripts.hpp"

#include <BHW/utils/reflection/TypeInfo.hpp>

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
        m_nativeScriptCoreDLL.Unload();
    
        m_nativeScripts.clear();

        m_nativeScriptCoreDLL.Load();

        if (!m_nativeScriptCoreDLL.IsLoaded())
        {
            BHW::Console::WriteLine("Failed to load native script core DLL!");
            return;
        }

        typedef void(*GetNativeScriptsFunc)(BHW::TypeInfo**, int*);

        GetNativeScriptsFunc getNativeScriptsFunc = m_nativeScriptCoreDLL.GetFunction<GetNativeScriptsFunc>("GetNativeScripts");

        if (!getNativeScriptsFunc)
        {
            BHW::Console::WriteLine("Failed to get GetNativeScripts function from native script core DLL!");
            return;
        }

        BHW::TypeInfo* types = nullptr;
        int count;

        getNativeScriptsFunc(&types, &count);

        for (int i = 0; i < count; i++)
        {
            if (m_nativeScripts.find(std::string(types[i].SourceLocation)) != m_nativeScripts.end())
            {
                m_nativeScripts.insert(std::make_pair(std::string(types[i].SourceLocation), NativeScript({ types[i] }, std::string(types[i].SourceLocation))));
                continue;           
            }

            m_nativeScripts.at(std::string(types[i].SourceLocation)).AddType(types[i]);
        }
    }
}