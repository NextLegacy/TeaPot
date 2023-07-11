#pragma once

#include <string>
#include <map>

#define NAMEOF(x) #x
#define SERIALIZE(x) { NAMEOF(x), &x }

namespace DigitalTea 
{
    class Serializable
    {
    protected: 
        template<typename T>
        void Serialize(std::string name, T& value);

        virtual std::map<std::string, void*> Serialization() = 0;
    
    public:
        static std::string Serialize(Serializable& object);
        static void Deserialize(std::string path, Serializable* object);
    };
}