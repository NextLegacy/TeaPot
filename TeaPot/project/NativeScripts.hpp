#pragma once

#include <vector>

#include <BHW/utils/reflection/TypeInfo.hpp>
#include <BHW/utils/dlls/DLL.hpp>

namespace TP
{
    class Project;

    class NativeScript
    {
    public:
        NativeScript(const std::vector<BHW::TypeInfo>& types, const std::string& sourceLocation);

    public:
        const std::string& GetSourceLocation() const { return SourceLocation; }
        const std::vector<BHW::TypeInfo>& GetTypes() const { return Types; }

        inline void AddType(const BHW::TypeInfo& type) { Types.push_back(type); }

    private:
        const std::string SourceLocation;
        std::vector<BHW::TypeInfo> Types;
    };

    class NativeScripts
    {
    public:
        NativeScripts(const Project& project);

        void Update();

    public:
        const std::unordered_map<std::string, NativeScript>& GetNativeScripts() const { return m_nativeScripts; }

    private:
        const Project& m_project;
    
        std::unordered_map<std::string, NativeScript> m_nativeScripts;
    
        BHW::DLL m_nativeScriptCoreDLL;
    };
}