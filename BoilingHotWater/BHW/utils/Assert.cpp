#include "BHW/utils/Assert.hpp"

#include "BHW/utils/console/Console.hpp"

namespace BHW
{
    template<typename... TArgs>
    void Assert(bool condition, std::string format, TArgs... messages)
    {
        //#ifdef DEBUG

        if (condition) return;

        std::string message = std::format(format, messages...);

        Console::WriteLine(message);

        //#endif // DEBUG
    }
}