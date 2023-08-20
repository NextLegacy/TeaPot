#pragma once

#include <string>
#include <map>
#include <vector>

#include "BHW/utils/Assert.hpp"

namespace BHW
{
    enum class JSONType
    {
        String,
        Int64,
        Int32,
        Int16,
        Int8,
        UInt64,
        UInt32,
        UInt16,
        UInt8,
        Float,
        Double,
        Bool,
        Array,
        Object,
        Null
    };

    class JSONObject
    {
    public:
        JSONObject();
        JSONObject(JSONType type, void* value);

        bool Is(JSONType type);

        template <typename T>
        T* Get();

        std::string ToString();

    private:
        template <typename T>
        std::string ToString();

        template <> std::string ToString<std::string>();
        template <> std::string ToString<int64_t    >();
        template <> std::string ToString<int32_t    >();
        template <> std::string ToString<int16_t    >();
        template <> std::string ToString<int8_t     >();
        template <> std::string ToString<uint64_t   >();
        template <> std::string ToString<uint32_t   >();
        template <> std::string ToString<uint16_t   >();
        template <> std::string ToString<uint8_t    >();
        template <> std::string ToString<float      >();
        template <> std::string ToString<double     >();
        template <> std::string ToString<bool       >();

        template <> std::string ToString<std::vector<JSONObject>>();
        template <> std::string ToString<std::map<std::string, JSONObject>>();

    public:
        JSONType m_type;
        void* m_value;
    };

    std::string Serialize(JSONObject json);

    JSONObject Deserialize(std::string);

    // functions that are invisble for the global scope
    namespace
    {
        bool Parse(std::string& json, JSONObject& result);

        bool ParserSkip (std::string& json, uint32_t& index);

        bool ParseString(std::string& json, uint32_t& index, JSONObject& result);
        bool ParseNumber(std::string& json, uint32_t& index, JSONObject& result);
        bool ParseBool  (std::string& json, uint32_t& index, JSONObject& result);
        bool ParseArray (std::string& json, uint32_t& index, JSONObject& result);
        bool ParseObject(std::string& json, uint32_t& index, JSONObject& result);
    }
}