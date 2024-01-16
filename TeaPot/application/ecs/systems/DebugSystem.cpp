#include "TeaPot/application/ecs/systems/DebugSystem.hpp"

#include <BHW/utils/json/JSON.hpp>
#include <BHW/utils/console/Console.hpp>

#include "TeaPot/application/TeaPot.hpp"

#include "BHW/utils/reflection/Reflection.hpp"

struct JustSomeTupleTest
{
    std::tuple<std::string, int, float> tuple = std::make_tuple("Hello", 42, 3.14f);
};

typedef std::tuple<std::string, int, float> SomeTuple;
BHW_REFLECT(SomeTuple)

BHW_REFLECT(JustSomeTupleTest)
BHW_REFLECT_MEMBERS(JustSomeTupleTest,
    BHW_REFLECT_MEMBER("tuple", &JustSomeTupleTest::tuple)
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
}

namespace TP
{
    void DebugSystem::Start(TeaPot& teaPot)
    {
        JustSomeTupleTest test;

        BHW::Console::WriteLine(BHW::Serialize(test));
    }

    void DebugSystem::Update(TeaPot& teaPot)
    {
    }

    void DebugSystem::Render(TeaPot& teaPot)
    {
    }   
}