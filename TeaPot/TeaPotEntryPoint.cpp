#include "TeaPot/TeaPotEntryPoint.hpp"

#include "TeaPot/application/TeaPot.hpp"
#include <BHW/utils/reflection/Reflection.hpp>
#include <BHW/utils/json/JSON.hpp>

int BHW::EntryPoint(std::vector<std::string> args)
{
    BHW::Console::Initialize();
    BHW::Console::WriteLine("TeaPot Engine - Version 0.0.1");

    BHW::Console::WriteLine(super_path());

    TP::TeaPot teaPot;

    teaPot.Run();

    return 0;
}

uint64_t fib(uint64_t n)
{
    constexpr double phi       = 1.61803398874989484820458683436563811772030917980576286213544862270526046281890;
    constexpr double sqrt5     = 2.23606797749978969640917366873127623544061835961152572427089724541052092568571;
    constexpr double sqrt5_inv = 0.44721359549995793928183473374625524708812367192230514805652174508216445;
    return static_cast<uint64_t>(sqrt5_inv * (std::pow(phi, n) - std::pow(1 - phi, n)));
}