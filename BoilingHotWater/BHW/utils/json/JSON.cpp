#include "BHW/utils/json/JSON.hpp"

#include "BHW/utils/Debug.hpp"
#include "BHW/utils/console/Console.hpp"

namespace BHW
{   
    std::string SerializeJSON(JSONObject json)
    {
        BHW_ASSERT(json.Is(JSONType::Object), "JSON | Serialize", "JSON is not an object");

        return json.ToString();
    }
    
    JSONObject DeserializeJSON(std::string json)
    {
        JSONObject result(JSONType::Object, new std::map<std::string, JSONObject>());

        BHW_ASSERT(Parse(json, result), "JSON | Deserialize", "Failed to parse JSON");

        return result;
    }

    namespace
    {
        bool Parse(std::string& json, JSONObject& result)
        {
            BHW_ASSERT(result.Is(JSONType::Object), "BHW::Parse()", "JSON is not an object");

            std::map<std::string, JSONObject>& map = result.Get<std::map<std::string, JSONObject>>();

            uint32_t index = 0;

            SkipToSignificant(json, index);

            BHW_ASSERT(json[index++] == '{', "BHW::Parse()", "Must start with {{");

            SkipToSignificant(json, index);

            for (; index < json.length();)
            {
                JSONObject key;
                JSONObject value;

                SkipToSignificant(json, index);
                
                BHW_ASSERT(json[index] == '\"'           , "BHW::Parse()", "Key must start with \""        );

                BHW_ASSERT(ParseString(json, index, key) , "BHW::Parse()", "Failed to parse key"           );

                SkipToSignificant(json, ++index);

                BHW_ASSERT(json[index++] == ':'          , "BHW::Parse()", "Expected key value seperator :");

                SkipToSignificant(json, index);

                BHW_ASSERT(ParseValue(json, index, value), "BHW::Parse()", "Failed to parse value"         );
    
                SkipToSignificant(json, ++index);

                map.insert({ key.Get<std::string>(), value });

                // TODO: just tested some stuff, works for now
                //       refactor this part later

                if (json[index] == '}') break;
                if (json[index] == ',') index++;
                if (json[index] == '}') break;

                SkipToSignificant(json, index);
                if (json[index] == '}') break;
            }

            return true;
        }

        inline void SkipToSignificant(std::string& data, uint32_t& i)
        {
            BHW_ASSERT(i < data.length(), "BHW::ParserSkip()", "Index out of range");

            while (data[i] == '\t' || data[i] == '\r' || data[i] == '\n' || data[i] == ' ') ++i;

            //if (data[i] == ' ' || data[i] == '\n') { ParserSkip(data, ++i); return; }
            //if (data[i] == '\\'                  ) { ++i; return; }

            if (data[i] != '/') { return; }

            if      (data[i] == '/') for (; i   < data.length() && data[i    ] != '\n'                 ; i++);
            else if (data[i] == '*') for (; i++ < data.length() && data[i - 1] != '*' && data[i] != '/'; i++);

            SkipToSignificant(data, i);
        }

        inline bool ParseValue(std::string& data, uint32_t& i, JSONObject& result)
        {
            return ParseString(data, i, result) || ParseNumber(data, i, result) || 
                   ParseBool  (data, i, result) || ParseArray (data, i, result) || 
                   ParseObject(data, i, result);
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
                    BHW_ASSERT(!isFloatingPointNumber, "BHW::ParseNumber()", "Number can't have more than one .");

                    if (!isFloatingPointNumber) isFloatingPointNumber = true;
                }

                if (base == 10 && data[i] == 'e')
                {
                    BHW_ASSERT(!isExponential, "BHW::ParseNumber()", "Exponential number can't have more than one e");

                    if (!isExponential) isExponential = true;
                }

                if (!(data[i] == '0' ||
                      data[i] == '1' ||
                      data[i] == '2' ||
                      data[i] == '3' ||
                      data[i] == '4' ||
                      data[i] == '5' ||
                      data[i] == '6' ||
                      data[i] == '7' ||
                      data[i] == '8' ||
                      data[i] == '9')) { result.m_type = JSONType::Int64; result.m_value = new int64_t(std::stoll(data.substr(pos, i - pos), nullptr, base)); break; }

                if (data[i] == 'i' && data[i + 1] == '6' && data[i + 2] == '4') { result.m_type = JSONType::Int64; result.m_value = new int64_t(std::stoll(data.substr(pos, i - pos), nullptr, base)); i += 3; break; }
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

            BHW_ASSERT(data[i] == '\"', "BHW::ParseString()", "String must end with \"");

            return true;
        }

        inline bool ParseArray(std::string& data, uint32_t& i, JSONObject& result)
        {
            if (data[i] != '[') return false;
            
            i++;

            uint32_t index = 0;

            result.m_type  = JSONType::Array;
            result.m_value = new std::vector<JSONObject>();

            std::vector<JSONObject>& array = result.Get<std::vector<JSONObject>>();

            SkipToSignificant(data, i);

            JSONObject inArrayResult;

            while (ParseString (data, i, inArrayResult) || ParseNumber(data, i, inArrayResult) || 
                   ParseBool   (data, i, inArrayResult) || ParseArray (data, i, inArrayResult) || 
                   ParseObject (data, i, inArrayResult))
            {
                array.push_back(inArrayResult);

                inArrayResult = JSONObject();

                SkipToSignificant(data, i);

                if (data[i] == ']') { break; }
                if (data[i] == ',') { i++  ; }

                SkipToSignificant(data, i);
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
                if      (data[i] == '{') count++;
                else if (data[i] == '}') count--;

                if (count == 0) break;
            }

            if (count != 0) return false;
            
            result = DeserializeJSON(data.substr(pos, i - pos));

            return true;
        }
    }
}