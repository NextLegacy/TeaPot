#include <BHW/DLLEntryPoint.hpp>
#include <BHW/utils/Debug.hpp>

int test(std::string arg)
{
    static bool first = true;

    if (first)
    {
        first = false;
        BHW::Console::Initialize();
        BHW::Console::WriteLine("Hello from " + arg + "!");
    }
}

int BHW::ProcessAttach() { return test("ProcessAttach"); }
int BHW::ProcessDetach() { return test("ProcessDetach"); }
int BHW::ThreadAttach () { return test("ThreadAttach" );  }
int BHW::ThreadDetach () { return test("ThreadDetach" );  }