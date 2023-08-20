#include "BHW/utils/serialization/JSON.hpp"

namespace BHW
{   
    JSONObject::JSONObject() : m_type(JSONType::Null), m_value(nullptr) { }
    JSONObject::JSONObject(JSONType type, void* value) : m_type(type), m_value(value) { }

    bool JSONObject::Is(JSONType type) { return m_type == type; }

    template <typename T>
    T* JSONObject::Get() 
    {
        if (m_value == nullptr) 
        {
            ASSERT(false, "JSON value is null");
            return nullptr;
        }

        return static_cast<T*>(m_value); 
    }

    std::string JSONObject::ToString()
    {
        switch (m_type)
        {
            case JSONType::String: return ToString<std::string>();
            case JSONType::Int64 : return ToString<int64_t    >();
            case JSONType::Int32 : return ToString<int32_t    >();
            case JSONType::Int16 : return ToString<int16_t    >();
            case JSONType::Int8  : return ToString<int8_t     >();
            case JSONType::UInt64: return ToString<uint64_t   >();
            case JSONType::UInt32: return ToString<uint32_t   >();
            case JSONType::UInt16: return ToString<uint16_t   >();
            case JSONType::UInt8 : return ToString<uint8_t    >();
            case JSONType::Float : return ToString<float      >();
            case JSONType::Double: return ToString<double     >();
            case JSONType::Bool  : return ToString<bool       >();

            case JSONType::Array : return ToString<std::vector<JSONObject>>          ();
            case JSONType::Object: return ToString<std::map<std::string, JSONObject>>();

            default: return "418";    
        }
    }

    template <> std::string JSONObject::ToString<std::string>() { return "\"" + *Get<std::string>() + "\""; }
    template <> std::string JSONObject::ToString<int64_t    >() { return std::to_string(*Get<int64_t >()) + "i64" ; }
    template <> std::string JSONObject::ToString<int32_t    >() { return std::to_string(*Get<int32_t >()) + "i32" ; }
    template <> std::string JSONObject::ToString<int16_t    >() { return std::to_string(*Get<int16_t >()) + "i16" ; }
    template <> std::string JSONObject::ToString<int8_t     >() { return std::to_string(*Get<int8_t  >()) + "i8"  ; }
    template <> std::string JSONObject::ToString<uint64_t   >() { return std::to_string(*Get<uint64_t>()) + "ui64"; }
    template <> std::string JSONObject::ToString<uint32_t   >() { return std::to_string(*Get<uint32_t>()) + "ui32"; }
    template <> std::string JSONObject::ToString<uint16_t   >() { return std::to_string(*Get<uint16_t>()) + "ui16"; }
    template <> std::string JSONObject::ToString<uint8_t    >() { return std::to_string(*Get<uint8_t >()) + "ui8" ; }
    template <> std::string JSONObject::ToString<float      >() { return std::to_string(*Get<float   >()) + "f"   ; }
    template <> std::string JSONObject::ToString<double     >() { return std::to_string(*Get<double  >()) + "d"   ; }
    template <> std::string JSONObject::ToString<bool       >() { return *Get<bool>() ? "true" : "false"; }

    template <>
    std::string JSONObject::ToString<std::vector<JSONObject>>()
    {
        std::string result = "[\n";

        for (JSONObject& value : *Get<std::vector<JSONObject>>())
        {
            result += "    " + value.ToString() + ",\n";
        }

        return result + "]";
    }

    template <>
    std::string JSONObject::ToString<std::map<std::string, JSONObject>>()
    {
        std::string result = "{\n";

        for (auto& [key, value] : *Get<std::map<std::string, JSONObject>>())
        {
            result += "    \"" + key + "\": " + value.ToString() + ",\n";
        }

        return result + "}";
    }

    std::string Serialize(JSONObject json)
    {
        ASSERT(json.Is(JSONType::Object), "JSON is not an object");

        return json.ToString();
    }
    
    JSONObject Deserialize(std::string json)
    {
        JSONObject* result = new JSONObject(JSONType::Object, new std::map<std::string, JSONObject>());

        ASSERT(Parse(json, *result), "JSON parsing failed");

        return *result;
    }

    namespace
    {
        bool Parse(std::string& json, JSONObject& result)
        {
            ASSERT(result.Is(JSONType::Object), "JSON is not an object");

            std::map<std::string, JSONObject>* map = result.Get<std::map<std::string, JSONObject>>();

            uint32_t index = 0;

            ASSERT
            (
                ParserSkip(json, index) && 

                index < json.length() &&
                json[index++] == '{'  && 

                ParserSkip(json, index), 
                
                "Must start with {"
            );

            for (; index < json.length(); index++)
            {
                JSONObject key;
                JSONObject value;

                ASSERT(ParserSkip(json, index), "C");

                ASSERT(json[index] == '\"', "ABC");

                ASSERT(ParseString(json, index, key), "D");

                ASSERT(ParserSkip(json, index), "E");
                
                ASSERT(json[index++] == ':', "F");

                ASSERT(ParserSkip(json, index), "G");

                ASSERT(ParseString(json, index, value) ||
                       ParseNumber(json, index, value) ||
                       ParseBool  (json, index, value) ||
                       ParseArray (json, index, value) ||
                       ParseObject(json, index, value), "H");

                map->insert({ *key.Get<std::string>(), value });

                ASSERT(ParserSkip(json, index), "I");

                if (json[index] == ',') index++;

                ASSERT(ParserSkip(json, index), "I");

                if (json[index--] == '}') break;
            }


            return true;
        }

        inline bool ParserSkip(std::string& data, uint32_t& i)
        {
            if (data[i] == ' ' || data[i] == '\n')  return ParserSkip(data, ++i);

            if (data[i] == '\\') { i++; return true; }

            if (data[i] != '/') return true;

            if (!(++i < data.length())) return false;

            if (data[i] == '/')
                for (; i < data.length() && data[i] != '\n'; i++);

            else if (data[i] == '*')
                for (; i++ < data.length() && data[i - 1] != '/' && data[i]; i++); 

            return true;
        }

        inline bool ParseNumber(std::string& data, uint32_t& i, JSONObject& result)
        {
            if (!(data[i] == '0' ||
                  data[i] == '1' ||
                  data[i] == '2' ||
                  data[i] == '3' ||
                  data[i] == '4' ||
                  data[i] == '5' ||
                  data[i] == '6' ||
                  data[i] == '7' ||
                  data[i] == '8' ||
                  data[i] == '9')) return false;

            int base = 10;
            bool isFloatingPointNumber = false;
            bool isExponential = false;

            if (data[i] == '0')
                if (data[i + 1] == 'b') base = 2;
                if (data[i + 1] == 'o') base = 8;
                if (data[i + 1] == 'x') base = 16;

            if (base != 10) i += 2;

            int pos = i ;

            if (data[i] == '+') i++;
            if (data[i] == '-') i++;

            for (; i < data.length(); i++)
            {
                if (data[i] == '.')
                {
                    if (!isFloatingPointNumber) isFloatingPointNumber = true;
                    else Console::WriteLine("Error: Floating point number can't have more than one dot");
                }

                if (base == 10 && data[i] == 'e')
                {
                    if (!isExponential) isExponential = true;
                    else Console::WriteLine("Error: Exponential number can't have more than one e");
                }

                if (data[i] == ',' ||
                    data[i] == 'i' && data[i + 1] == '6' && data[i + 2] == '4') { result.m_type = JSONType::Int64; result.m_value = new int64_t(std::stoll(data.substr(pos, i - pos), nullptr, base)); i += 3; break; }
                if (data[i] == 'i' && data[i + 1] == '3' && data[i + 2] == '2') { result.m_type = JSONType::Int32; result.m_value = new int32_t(std::stoi (data.substr(pos, i - pos), nullptr, base)); i += 3; break; }
                if (data[i] == 'i' && data[i + 1] == '1' && data[i + 2] == '6') { result.m_type = JSONType::Int16; result.m_value = new int16_t(std::stoi (data.substr(pos, i - pos), nullptr, base)); i += 3; break; }
                if (data[i] == 'i' && data[i + 1] == '8')                       { result.m_type = JSONType::Int8 ; result.m_value = new int8_t (std::stoi (data.substr(pos, i - pos), nullptr, base)); i += 2; break; }

                if (data[i] == 'u' && data[i + 1] == 'i' && data[i + 2] == '6' && data[i + 3] == '4') { result.m_type = JSONType::UInt64; result.m_value = new uint64_t(std::stoull(data.substr(pos, i - pos), nullptr, base)); i += 4; break; }
                if (data[i] == 'u' && data[i + 1] == 'i' && data[i + 2] == '3' && data[i + 3] == '2') { result.m_type = JSONType::UInt32; result.m_value = new uint32_t(std::stoul (data.substr(pos, i - pos), nullptr, base)); i += 4; break; }
                if (data[i] == 'u' && data[i + 1] == 'i' && data[i + 2] == '1' && data[i + 3] == '6') { result.m_type = JSONType::UInt16; result.m_value = new uint16_t(std::stoul (data.substr(pos, i - pos), nullptr, base)); i += 4; break; }
                if (data[i] == 'u' && data[i + 1] == 'i' && data[i + 2] == '8')                       { result.m_type = JSONType::UInt8 ; result.m_value = new uint8_t (std::stoul (data.substr(pos, i - pos), nullptr, base)); i += 3; break; }

                if (data[i] == 'f') { result.m_type = JSONType::Float ; result.m_value = new float (std::stof(data.substr(pos, i - pos), nullptr)); i++; break; }
                if (data[i] == 'd') { result.m_type = JSONType::Double; result.m_value = new double(std::stod(data.substr(pos, i - pos), nullptr)); i++; break; }
            }

            return true;
        }

        inline bool ParseBool(std::string& data, uint32_t& i, JSONObject& result)
        {
            if (data[i] == 't' && data[i + 1] == 'r' &&data[i + 2] == 'u' && data[i + 3] == 'e') 
            {
                result.m_type  = JSONType::Bool;
                result.m_value = new bool(true);

                i += 4;

                return true;
            }
            
            if (data[i] == 'f' && data[i + 1] == 'a' &&data[i + 2] == 'l' && data[i + 3] == 's' && data[i + 4] == 'e') 
            {
                result.m_type  = JSONType::Bool;
                result.m_value = new bool(false);

                i += 5;

                return true;
            }

            return false;
        }

        inline bool ParseString(std::string& data, uint32_t& i, JSONObject& result)
        {
            if (data[i] != '\"') return false;

            i++;

            int pos = i;
            
            for (; i < data.length() && data[i] != '\"' && data[i - 1] != '\\'; i++);

            result.m_type  = JSONType::String;
            result.m_value = new std::string(data.substr(pos, i - pos));

            ASSERT(data[i++] == '\"', "X");

            return true;
        }

        inline bool ParseArray(std::string& data, uint32_t& i, JSONObject& result)
        {
            if (data[i] != '[') return false;
            
            i++;

            uint32_t index = 0;

            result.m_type  = JSONType::Array;
            result.m_value = new std::vector<JSONObject>();

            std::vector<JSONObject>* array = result.Get<std::vector<JSONObject>>();

            if (ParserSkip(data, i)) { /* error */ }

            JSONObject inArrayResult;

            while (ParseString (data, i, inArrayResult) || ParseNumber(data, i, inArrayResult) || 
                   ParseBool   (data, i, inArrayResult) || ParseArray (data, i, inArrayResult) || 
                   ParseObject (data, i, inArrayResult))
            {
                array->push_back(inArrayResult);

                inArrayResult = JSONObject();

                if (ParserSkip(data, i)) { /* error */ }

                if (data[i] == ']') { break; }
                if (data[i] == ',') { i++; }

                if (ParserSkip(data, i)) { /* error */ }
            }

            i++;

            return true;
        }

        inline bool ParseObject(std::string& data, uint32_t& i, JSONObject& result)
        {
            if (data[i] != '{') return false;

            int count = 1;

            int pos = i;
            
            for (; i < data.length(); i++)
            {
                if (data[i] == '{') count++;
                else if (data[i] == '}') count--;

                if (count == 0) break;
            }

            if (count != 0) return false;
            
            result = Deserialize(data.substr(pos, i - pos));

            return true;
        }
    }
}