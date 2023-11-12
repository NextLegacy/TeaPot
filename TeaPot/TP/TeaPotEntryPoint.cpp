#include "TP/TeaPotEntryPoint.hpp"

#include "TP/application/TeaPot.hpp"

#include <BHW/utils/reflection/Reflection.hpp>
#include <BHW/utils/io/FileHandler.hpp>

struct SuperStruct { };

BHW_REFLECTION_TYPE(SuperStruct)

struct HelloWorld : SuperStruct
{
    int a;
    float b;
    double c;
};

BHW_REFLECTION_TYPE(HelloWorld, SuperStruct)

BHW_REFLECTION_MEMBERS(HelloWorld, 
    BHW_REFLECTION_MEMBER("a", &HelloWorld::a),
    BHW_REFLECTION_MEMBER("b", &HelloWorld::b),
    BHW_REFLECTION_MEMBER("c", &HelloWorld::c)
)

template<typename TClass, typename TType>
constexpr std::string TypeName(const BHW::Member<TClass, TType>& member)
{
    return std::string(BHW::TypeName<TType>());
}

int BHW::EntryPoint(std::vector<std::string> args)
{
    BHW::Console::Initialize();

    HelloWorld helloWorld = HelloWorld();
 
    std::apply( [](auto&&... args) 
        {
            (BHW::Console::WriteLine(TypeName(args)), ...);
        }, 
        BHW::GetMembers<HelloWorld>()
    );

    const BHW::TypeInfo& typeInfo = BHW::GetTypeInfo<SuperStruct>();

    for (const auto& [hash, typeInfo] : typeInfo.DerivedClasses)
    {
        BHW::Console::WriteLine(std::string(typeInfo.Name));
    }

    BHW::CreateFolder("./test");

    TP::TeaPot app;
    app.Run();

    return 0; 
}
