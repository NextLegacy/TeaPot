#pragma once

#include <Tea/Tea.hpp>

#include <BHW/utils/reflection/Reflection.hpp>

#include <{}> // Graphics API include
#include "Headers.hpp"

namespace Tea
{{
    class Application : public Tea
    <        
{}, // Graphics API
        System, 
        ECSSystem
        <
            System,

            // ECS Systems
{}
        >,
        Application,

        // ECS Components
{}
    >
    {{
    }};
}}

BHW_REFLECT(Tea::Application::TECS)
BHW_REFLECT_MEMBERS(Tea::Application::TECS
    BHW_REFLECT_MEMBER(Tea::Application::TECS, &Tea::Application::TECS::m_components           )
    BHW_REFLECT_MEMBER(Tea::Application::TECS, &Tea::Application::TECS::m_entityComponents     )
    BHW_REFLECT_MEMBER(Tea::Application::TECS, &Tea::Application::TECS::m_componentEntityOwners)
)

namespace BHW
{{
    template <>
    std::string ToString<decltype(Tea::Application::TECS::m_components)>(decltype(Tea::Application::TECS::m_components)& object, uint64_t indent)
    {{
        std::string result = "[\n";

        std::string indentString = "";

        for (uint64_t i = 0; i < indent; i++) indentString += "    ";

        std::apply([&](auto&&... args) {{ ((result += "    " + indentString + ToString(args, indent + 1) + ",\n"), ...); }}, object);

        return result + indentString + "]";
    }}
}}