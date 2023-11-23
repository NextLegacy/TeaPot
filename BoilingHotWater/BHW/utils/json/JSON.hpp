#pragma once

#include <string>
#include <map>
#include <vector>

#include "BHW/utils/reflection/Reflection.hpp"
#include "BHW/utils/Debug.hpp"

namespace BHW
{
    typedef std::vector<Value> JSONArray;
    typedef std::map<std::string, Value> JSONObject;

    template <typename T>
    struct JSONKeyValue
    {
        std::string key;
        T value;

        JSONKeyValue(std::string key, T value) : key(key), value(value) { }
    };

    std::string ToString(Value& value);

    template <typename T>
    inline std::string ToString(const T& object)
    {
        return "\"UNKNOW\"";
    }

    template <> std::string ToString<std::string>(const std::string& value);
    template <> std::string ToString<int64_t    >(const int64_t&     value);
    template <> std::string ToString<int32_t    >(const int32_t&     value);
    template <> std::string ToString<int16_t    >(const int16_t&     value);
    template <> std::string ToString<int8_t     >(const int8_t&      value);
    template <> std::string ToString<uint64_t   >(const uint64_t&    value);
    template <> std::string ToString<uint32_t   >(const uint32_t&    value);
    template <> std::string ToString<uint16_t   >(const uint16_t&    value);
    template <> std::string ToString<uint8_t    >(const uint8_t&     value);
    template <> std::string ToString<float      >(const float&       value);
    template <> std::string ToString<double     >(const double&      value);
    template <> std::string ToString<bool       >(const bool&        value);

    /*
    template <typename T>
    inline std::string ToString(const Value& value)
    {
        if (value.Is<T>())
            return ToString(value.Get<T>());
        else
            return "null";
    }*/

    template <typename T> 
    inline std::string ToString(std::vector<T>& vector)
    {
        std::string result = "[\n";

        for (T& value : vector)
        {
            std::string valueString = ToString(value);

            for (uint32_t i = 0; i < valueString.size(); i++)
            {
                if (valueString[i] == '\n')
                    valueString.insert(i + 1, "    ");
            }

            result += "    " + valueString + ",\n";
        }

        return result + "]";
    }


    template <typename T> 
    inline std::string ToString(std::map<std::string, T>& map)
    {
        std::string result = "{\n";

        for (auto& [key, value] : map)
        {
            std::string valueString = ToString(value);

            for (uint32_t i = 0; i < valueString.size(); i++)
            {
                if (valueString[i] == '\n')
                    valueString.insert(i + 1, "    ");
            }

            result += "    \"" + key + "\": " + valueString + ",\n";
        }

        return result + "}";
    }

    namespace
    {
        bool Parse(const std::string& json, JSONObject& result);

        void SkipToSignificant (const std::string& json, uint32_t& index);

        bool ParseValue (const std::string& json, uint32_t& index, Value& result);
        bool ParseString(const std::string& json, uint32_t& index, Value& result);
        bool ParseNumber(const std::string& json, uint32_t& index, Value& result);
        bool ParseBool  (const std::string& json, uint32_t& index, Value& result);
        bool ParseArray (const std::string& json, uint32_t& index, Value& result);
        bool ParseObject(const std::string& json, uint32_t& index, Value& result);
    }

    template <typename ...T>
    inline std::string Serialize(JSONKeyValue<T>... keyValue)
    {
        std::string result = "{\n";

        std::apply([&](auto&&... keyValue)
        {
            ((result += "    \"" + keyValue.key + "\": " + ToString(keyValue.value) + ",\n"), ...);
        }, std::tuple(keyValue...));        

        return result + "}";
    }

    JSONObject DeserializeToMap(const std::string& string);

    template <typename T>
    inline std::string Serialize(T& object)
    {
        BHW_ASSERT(BHW::IsRegistered<T>(), "Serialize()", "Type is not registered!");

        std::string result = "{\n";

        std::apply([&](auto&&... members)
        {
            ((result += "    \"" + std::string(members.Name.begin(), members.Name.end()) + "\": " + ToString(members(object)) + ",\n"), ...);
        }, GetMembers<T>());

        return result + "}";

        return result;
    }

    template <typename T>
    inline T Deserialize(std::string string, T* result = nullptr)
    {
        BHW_ASSERT(BHW::IsRegistered<T>(), "Deserialize()", "Type is not registered!");

        JSONObject object = DeserializeToMap(string);

        if (result == nullptr) result = new T();

        std::apply([&](auto&&... members)
        {
            ((members(*result) = object[std::string(members.Name.begin(), members.Name.end())].Get<typename std::remove_reference<decltype(members(*result))>::type>()), ...);
        }, GetMembers<T>());

        return *result;
    }

}