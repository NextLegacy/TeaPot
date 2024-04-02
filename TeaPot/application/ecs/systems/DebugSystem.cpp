#include "TeaPot/application/ecs/systems/DebugSystem.hpp"

#include <BHW/utils/json/JSON.hpp>
#include <BHW/utils/console/Console.hpp>

#include "TeaPot/application/TeaPot.hpp"

#include "BHW/utils/reflection/Reflection.hpp"

struct JustSomeTupleTest
{
    std::tuple<std::string, int, float> tuple = std::make_tuple("Hello", 42, 3.14f);

    std::map<std::string, std::string> test = { {"Hello", "World"}, {"Foo", "Bar"} };
};

typedef std::tuple<std::string, int, float> SomeTuple;
BHW_REFLECT(SomeTuple)
typedef std::map<std::string, std::string> SomeMap;
BHW_REFLECT(SomeMap)

BHW_REFLECT(JustSomeTupleTest)
BHW_REFLECT_MEMBERS(JustSomeTupleTest,
    BHW_REFLECT_MEMBER("tuple", &JustSomeTupleTest::tuple),
    BHW_REFLECT_MEMBER("test", &JustSomeTupleTest::test)
)

namespace BHW
{
    template <>
    std::string ToString<std::tuple<std::string, int, float>>(std::tuple<std::string, int, float>& object, uint64_t indent)
    {
        std::string result = "[\n";

        std::string indentString = "";

        for (uint64_t i = 0; i < indent; i++) indentString += "    ";

        std::apply([&](auto&&... args) { ((result += "    " + indentString + ToString(args, indent + 1) + ",\n"), ...); }, object);

        return result + indentString + "]";
    }

    template <>
    std::string ToString<std::unordered_map<std::string, std::string>>(std::unordered_map<std::string, std::string>& object, uint64_t indent)
    {
        std::map<std::string, std::string> map(object.begin(), object.end());

        return ToString(map, indent);
    }

    template <>
    inline std::tuple<std::string, int, float> Deserialize(std::string string, std::tuple<std::string, int, float>* result)
    {
        Value value;
        uint32_t index = 0;
        
        ParseArray(string, index, value); 

        if (result != nullptr) result = new std::tuple<std::string, int, float>();

        std::apply([&](auto&&... args) { ((args = value.Get<std::remove_reference_t<decltype(args)>>()), ...); }, *result);

        return *result;
    }
}

namespace TP
{
    void DebugSystem::Start(TeaPot& teaPot)
    {
       //JustSomeTupleTest test;

       //test.test["Peter"] = "Pan";

       //std::string text = BHW::Serialize(test);

       //BHW::Console::WriteLine(text);

       //auto test2 = BHW::Deserialize<JustSomeTupleTest>(text);

       //BHW::Console::WriteLine(BHW::ToString(test2));
    }

    void DebugSystem::Update(TeaPot& teaPot)
    {
    }

    void DebugSystem::Render(TeaPot& teaPot)
    {
    }   
}