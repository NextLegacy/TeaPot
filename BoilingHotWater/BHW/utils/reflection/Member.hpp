#pragma once

#include <string_view>
#include <tuple>

#include "BHW/utils/reflection/TypeInfo.hpp"

namespace BHW
{
    template<typename TClass, typename TType>
    class Member
    {
    public:
        constexpr Member(std::string_view name, TType TClass::* memberPtr) 
            : Name     (name                 ), 
              MemberPtr(memberPtr            ), 
              Type     (GetTypeInfo<TType >()),
              Class    (GetTypeInfo<TClass>()) { }
    public:
        const std::string_view Name     ;
        const TType TClass::*  MemberPtr;
        const TypeInfo&        Type     ;
        const TypeInfo&        Class    ;
    };
    
    namespace __reflection
    {
        template<typename TClass, typename ...TMemberTypes>
        constexpr std::tuple<Member<TClass, TMemberTypes>...> MakeMembers(Member<TClass, TMemberTypes>... members)
        {
            return std::make_tuple(members...);
        }

        template<typename TClass, typename TType>
        constexpr Member<TClass, TType> MakeMember(std::string_view name, TType TClass::* memberPtr)
        {
            return Member<TClass, TType>(name, memberPtr);
        }
    }

    template <typename TClass>
    constexpr auto GetMembers();
}