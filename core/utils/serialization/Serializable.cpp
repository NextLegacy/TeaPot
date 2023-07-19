#include "serializable.hpp"

namespace DigitalTea
{
    std::string Serializable::Serialize(Serializable& object)
    {
        std::string result = "{";

        // for (auto& [key, value] : object.m_references)
        // {
        //     result += "\"" + key + "\": ";

        //     //serializable* serializable = dynamic_cast<serializable>(a);
        // }

        return "}";
    }
}
