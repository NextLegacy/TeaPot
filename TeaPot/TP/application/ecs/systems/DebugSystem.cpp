#include "TP/application/ecs/systems/DebugSystem.hpp"

#include <BHW/utils/json/JSON.hpp>
#include <BHW/utils/console/Console.hpp>

#include "TP/application/TeaPot.hpp"

namespace TP
{
    void DebugSystem::Start(TeaPot& teaPot)
    {
        //SomeTest test{ 123, "Hello World", { "Hello", "World" }, { { "Hello", "World" }, { "Hello", "World" } } };

        //BHW::Deserialize<SomeTest>(BHW::Serialize<SomeTest>(test), &test);

        //BHW::Console::WriteLine(BHW::Serialize<SomeTest>(test));
    }

    void DebugSystem::Update(TeaPot& teaPot)
    {
    }

    void DebugSystem::Render(TeaPot& teaPot)
    {
    }   
}