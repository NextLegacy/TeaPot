#include "BHW/utils/Assert.hpp"

namespace BHW
{
    void ASSERT(bool condition, std::string message)
    {
        //#ifdef DEBUG

        if (condition) return;

        Console::WriteLine(message);

        //#endif // DEBUG
    }
}