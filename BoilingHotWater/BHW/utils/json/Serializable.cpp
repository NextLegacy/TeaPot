#include "BHW/utils/json/Serializable.hpp"

namespace BHW
{
    std::string Serializable::Serialize() 
    { 
        return SerializeJSON(m_jsonObject); 
    }

    void Serializable::Deserialize(std::string jsonString) 
    { 
        JSONObject deserializedData    = DeserializeJSON(jsonString); 
        JSONMap    deserializedDataMap = deserializedData.Get<JSONMap>();

        for (auto& [key, value] : deserializedDataMap)
        {
            JSONObject jsonObject = deserializedDataMap[key];

            jsonObject.Get<void*>() = value.Get<void*>();
        }
    }

    void Serializable::Register(std::string key, JSONObject jsonObject)
    {
        m_jsonObject.Get<JSONMap>()[key] = jsonObject;
    }
}