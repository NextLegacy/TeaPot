#pragma once

#include <string>
#include <map>
#include <vector>

#include "BHW/utils/json/JSONType.hpp"
#include "BHW/utils/Debug.hpp"

namespace BHW
{
    class JSONObject;

    typedef std::map<std::string, JSONObject> JSONMap  ;
    typedef std::vector<JSONObject>           JSONArray;

    class JSONObject
    {
    public:
        JSONObject();
        JSONObject(JSONType type, void* value);

        bool Is(JSONType type);

        template <typename T>
        T& Get() 
        {
            BHW_ASSERT(m_value != nullptr, "JSONObject::Get", "Value is nullptr");

            return *(T*) m_value; 
        }

        const JSONType& GetType () const { return m_type ; }
        const void*     GetValue() const { return m_value; }

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

        template <> std::string ToString<JSONArray>();
        template <> std::string ToString<JSONMap>();

    public:
        JSONType m_type ;
        void*    m_value;
    };
}