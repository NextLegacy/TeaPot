#include <BHW/DLLEntryPoint.hpp>
#include <BHW/utils/Debug.hpp>

int test(std::string arg)
{{
    return 0;
}}

int BHW::ProcessAttach() {{ return test("ProcessAttach"); }}
int BHW::ProcessDetach() {{ return test("ProcessDetach"); }}
int BHW::ThreadAttach () {{ return test("ThreadAttach" );  }}
int BHW::ThreadDetach () {{ return test("ThreadDetach" );  }}