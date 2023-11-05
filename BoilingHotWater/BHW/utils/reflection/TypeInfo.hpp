#pragma once

#include <unordered_map>

#include "BHW/utils/reflection/Type.hpp"

namespace BHW
{
    class TypeInfo;

    namespace __reflection
    {
        template <typename TClass, typename ...TInheritedClasses>
        constexpr const TypeInfo& MakeTypeInfo();
    }

    class TypeInfo
    {
    public:
        const std::string_view Name;
        const uint64_t         Hash;

        const std::unordered_map<uint64_t, const TypeInfo&> InheritedClasses;
              std::unordered_map<uint64_t, const TypeInfo&> DerivedClasses  ;

    private:
        TypeInfo(
            std::string_view                                    name            , 
            uint64_t                                            hash            , 
            const std::unordered_map<uint64_t, const TypeInfo&> inheritedClasses
        ) : Name(name), Hash(hash), InheritedClasses(inheritedClasses), DerivedClasses() 
        {
        }

        template <typename TClass, typename ...TInheritedClasses>
        friend constexpr const TypeInfo& __reflection::MakeTypeInfo<TClass, TInheritedClasses...>();
    };

    namespace __reflection
    {
        template <typename TClass, typename ...TInheritedClasses>
        constexpr const TypeInfo& MakeTypeInfo()
        {
            const TypeInfo& typeInfo = *(new TypeInfo
            (
                TypeName<TClass>(),
                Hash    <TClass>(), 
                { { Hash<TInheritedClasses>(), GetTypeInfo<Hash<TInheritedClasses>()>() }... }
            ));

            ((const_cast<TypeInfo*>(&GetTypeInfo<Hash<TInheritedClasses>()>())->DerivedClasses.insert({ Hash<TClass>(), typeInfo })), ...);

            return typeInfo;
        }
    }
    
    template <size_t THash>
    inline constexpr const TypeInfo& GetTypeInfo();

    template <typename TClass> 
    inline constexpr bool IsRegistered()
    {
        return false;
    }

    template <typename TClass>
    inline constexpr const TypeInfo& GetTypeInfo()
    {
        static_assert(IsRegistered<TClass>(), "Type is not registered");

        return GetTypeInfo<Hash<TClass>()>();
    }
}