#pragma once

#include "BHW/utils/json/JSON.hpp"

namespace BHW
{
    class Serializable
    {
    public:
        Serializable() = default;

        std::string Serialize  (                      );
        void        Deserialize(std::string jsonString);
    
    protected:
        void Register(std::string key, JSONObject jsonObject);

        virtual void InitializeSerializable() = 0;

    private:
        JSONObject m_jsonObject;
    };
}