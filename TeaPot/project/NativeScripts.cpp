#include "TeaPot/project/NativeScripts.hpp"

namespace TP
{
    NativeScript::NativeScript(const std::vector<BHW::TypeInfo>& types, const std::string& sourceLocation)
        : Types(types), SourceLocation(sourceLocation)
    {

    }

    NativeScripts::NativeScripts(const Project& project)
        : m_project(project)
    {

    }

    void NativeScripts::Update()
    {
        if (m_nativeScriptCoreDLL)
        {
            m_nativeScriptCoreDLL.Unload();
        }
    }
}