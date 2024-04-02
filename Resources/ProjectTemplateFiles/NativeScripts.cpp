#include <BHW/DLLEntryPoint.hpp>
#include <BHW/utils/Debug.hpp>

#include "Headers.hpp"

int test(std::string arg)
{{
    return 0;
}}

int BHW::ProcessAttach() {{ return test("ProcessAttach"); }}
int BHW::ProcessDetach() {{ return test("ProcessDetach"); }}
int BHW::ThreadAttach () {{ return test("ThreadAttach" ); }}
int BHW::ThreadDetach () {{ return test("ThreadDetach" ); }}

#include <Tea/EventSubscriber.hpp>
#include <BHW/utils/reflection/Reflection.hpp>

extern "C" __declspec(dllexport) void GetComponents(const BHW::TypeInfo** types, uint32_t* count)
{{
    const BHW::TypeInfo& typeInfo = BHW::GetTypeInfo<Tea::Component>();

    *count = typeInfo.DerivedClasses.size();

    for (auto&[hash, typeInfo] : typeInfo.DerivedClasses) *types++ = &typeInfo;
}}

extern "C" __declspec(dllexport) void GetSystems(const BHW::TypeInfo** types, uint32_t* count)
{{
    const BHW::TypeInfo& typeInfo = BHW::GetTypeInfo<Tea::System>();

    *count = typeInfo.DerivedClasses.size();

    for (auto&[hash, typeInfo] : typeInfo.DerivedClasses) *types++ = &typeInfo;
}}
