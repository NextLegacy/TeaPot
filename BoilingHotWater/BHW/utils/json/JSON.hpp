#pragma once

#include <string>
#include <map>
#include <vector>

#include "BHW/utils/json/JSONObject.hpp"

namespace BHW
{
    std::string SerializeJSON  (JSONObject  jsonObject);
    JSONObject  DeserializeJSON(std::string jsonString);

    namespace
    {
        bool Parse(std::string& json, JSONObject& result);

        void SkipToSignificant (std::string& json, uint32_t& index);

        bool ParseValue (std::string& json, uint32_t& index, JSONObject& result);
        bool ParseString(std::string& json, uint32_t& index, JSONObject& result);
        bool ParseNumber(std::string& json, uint32_t& index, JSONObject& result);
        bool ParseBool  (std::string& json, uint32_t& index, JSONObject& result);
        bool ParseArray (std::string& json, uint32_t& index, JSONObject& result);
        bool ParseObject(std::string& json, uint32_t& index, JSONObject& result);
    }
}