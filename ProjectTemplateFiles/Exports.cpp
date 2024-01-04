#include "Exports.hpp"

#include <BHW/utils/reflection/Reflection.hpp>

std::vector<std::string> BHW_DLL_EXPORT GetSystems()
{
    BHW::TypeInfo type = BHW::GetTypeInfo<Tea::EventSubscriber>();

    std::unordered_map<uint64_t, const BHW::TypeInfo&> types = type.DerivedClasses;

    std::vector<std::string> result;

    for (const auto& [_, type] : types)
    {
        result.push_back(std::string(type.Name));
    }

    return result;
}

std::vector<std::string> BHW_DLL_EXPORT GetComponents()
{
    BHW::TypeInfo type = BHW::GetTypeInfo<Tea::EventStorage>();

    std::unordered_map<uint64_t, const BHW::TypeInfo&> types = type.DerivedClasses;

    std::vector<std::string> result;

    for (const auto& [_, type] : types)
    {
        result.push_back(std::string(type.Name));
    }

    return result;
}