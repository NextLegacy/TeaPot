#include "TeaPot/TeaPotEntryPoint.hpp"

#include "TeaPot/application/TeaPot.hpp"
#include <BHW/utils/reflection/Reflection.hpp>

struct SuperTestStruct
{

};

BHW_REFLECT(SuperTestStruct)

int BHW::EntryPoint(std::vector<std::string> args)
{
    BHW::Console::Initialize();
    BHW::Console::WriteLine("TeaPot Engine - Version 0.0.1");

    BHW::Console::WriteLine(std::string(BHW::TypeName<SuperTestStruct>()).c_str());

    BHW::Console::WriteLine(super_path());

    TP::TeaPot teaPot;

    teaPot.Run();

    return 0;
}