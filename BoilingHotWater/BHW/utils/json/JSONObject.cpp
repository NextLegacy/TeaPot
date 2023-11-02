#include "BHW/utils/json/JSONObject.hpp"

namespace BHW
{
    JSONObject::JSONObject() : m_type(JSONType::Null), m_value(nullptr) { }

    JSONObject::JSONObject(JSONType type, void* value) : m_type(type), m_value(value) { }

    bool JSONObject::Is(JSONType type) { return m_type == type; }

    std::string JSONObject::ToString()
    {
        switch (m_type)
        {
            case JSONType::String: return ToString<std::string                         >();
            case JSONType::Int64 : return ToString<int64_t                             >();
            case JSONType::Int32 : return ToString<int32_t                             >();
            case JSONType::Int16 : return ToString<int16_t                             >();
            case JSONType::Int8  : return ToString<int8_t                              >();
            case JSONType::UInt64: return ToString<uint64_t                            >();
            case JSONType::UInt32: return ToString<uint32_t                            >();
            case JSONType::UInt16: return ToString<uint16_t                            >();
            case JSONType::UInt8 : return ToString<uint8_t                             >();
            case JSONType::Float : return ToString<float                               >();
            case JSONType::Double: return ToString<double                              >();
            case JSONType::Bool  : return ToString<bool                                >();
            case JSONType::Array : return ToString<std::vector<JSONObject             >>();
            case JSONType::Object: return ToString<std::map   <std::string, JSONObject>>();

            default: BHW_ASSERT(false, "JSONObject::ToString", "Unknown type"); return "Unknown type";
        }
    }

    template <> std::string JSONObject::ToString<std::string>() { return "\"" + Get<std::string>()  + "\""       ; }
    template <> std::string JSONObject::ToString<int64_t    >() { return std::to_string(Get<int64_t >()) + "i64" ; }
    template <> std::string JSONObject::ToString<int32_t    >() { return std::to_string(Get<int32_t >()) + "i32" ; }
    template <> std::string JSONObject::ToString<int16_t    >() { return std::to_string(Get<int16_t >()) + "i16" ; }
    template <> std::string JSONObject::ToString<int8_t     >() { return std::to_string(Get<int8_t  >()) + "i8"  ; }
    template <> std::string JSONObject::ToString<uint64_t   >() { return std::to_string(Get<uint64_t>()) + "ui64"; }
    template <> std::string JSONObject::ToString<uint32_t   >() { return std::to_string(Get<uint32_t>()) + "ui32"; }
    template <> std::string JSONObject::ToString<uint16_t   >() { return std::to_string(Get<uint16_t>()) + "ui16"; }
    template <> std::string JSONObject::ToString<uint8_t    >() { return std::to_string(Get<uint8_t >()) + "ui8" ; }
    template <> std::string JSONObject::ToString<float      >() { return std::to_string(Get<float   >()) + "f"   ; }
    template <> std::string JSONObject::ToString<double     >() { return std::to_string(Get<double  >()) + "d"   ; }
    template <> std::string JSONObject::ToString<bool       >() { return Get<bool>() ? "true" : "false"          ; }

    template <>
    std::string JSONObject::ToString<std::vector<JSONObject>>()
    {
        std::string result = "[\n";

        for (JSONObject& value : Get<std::vector<JSONObject>>())
        {
            std::string valueString = value.ToString();

            for (uint32_t i = 0; i < valueString.size(); i++)
            {
                if (valueString[i] == '\n')
                    valueString.insert(i + 1, "    ");
            }

            result += "    " + valueString + ",\n";
        }

        return result + "]";
    }

    template <>
    std::string JSONObject::ToString<std::map<std::string, JSONObject>>()
    {
        std::string result = "{\n";

        for (auto& [key, value] : Get<std::map<std::string, JSONObject>>())
        {
            std::string valueString = value.ToString();

            for (uint32_t i = 0; i < valueString.size(); i++)
            {
                if (valueString[i] == '\n')
                    valueString.insert(i + 1, "    ");
            }

            result += "    \"" + key + "\": " + valueString + ",\n";
        }

        return result + "}";
    }
}