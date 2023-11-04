#pragma once

#include "BHW/utils/reflection/Type.hpp"
#include "BHW/utils/reflection/TypeInfo.hpp"

#define BHW_REFLECTION_CONCAT(a, b) a##b
#define BHW_REFLECTION_TYPE_INTERNAL(class_name, var_name, ...) \
    namespace BHW \
    { \
        namespace __reflection \
        { \
            inline static const TypeInfo& BHW_REFLECTION_CONCAT(__, var_name) = MakeTypeInfo<class_name, __VA_ARGS__>(); \
        } \
        template <> \
        inline constexpr const TypeInfo& GetTypeInfo<Hash<class_name>()>() \
        { \
            return __reflection::BHW_REFLECTION_CONCAT(__, var_name); \
        } \
        template <> \
        inline constexpr bool IsRegistered<class_name>() \
        { \
            return true; \
        } \
    }

#define BHW_REFLECTION_TYPE(class_name, ...) BHW_REFLECTION_TYPE_INTERNAL(class_name, __COUNTER__, __VA_ARGS__)

#define BHW_REFLECTION_MEMBERS(class_name, ...) \
    namespace BHW \
    { \
        namespace __reflection \
        { \
            inline static const auto __##class_name##_members = MakeMembers<class_name>(__VA_ARGS__); \
        } \
        template <> \
        inline constexpr auto GetMembers<class_name>() \
        { \
            return __reflection::__##class_name##_members; \
        } \
    }

#define BHW_REFLECTION_MEMBER(member_name, member_ptr) MakeMember(member_name, member_ptr)