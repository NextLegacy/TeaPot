#pragma once

#include <string>

#include "BHW/utils/console/Console.hpp"

#define BHW_ASSERT(condition, from, message) BHW::ASSERT(condition, "[" + from + "] " + message)

namespace BHW
{
    void ASSERT(bool condition, std::string message = "Assertion failed");
}