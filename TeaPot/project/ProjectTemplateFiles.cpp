#include "TeaPot/project/ProjectTemplateFiles.hpp"


#include "TeaPot/project/files/CMakeLists.txt.hpp"
#include "TeaPot/project/files/EntryPoint_Final.cpp.hpp"
#include "TeaPot/project/files/EntryPoint_NativeScriptCore.cpp.hpp"
#include "TeaPot/project/files/EventSubscriber.hpp.hpp"
#include "TeaPot/project/files/Tea.cpp.hpp"
#include "TeaPot/project/files/Tea.hpp.hpp"

namespace TP
{
    namespace ProjectTemplateFiles
    {
        std::string CMakeLists               = reinterpret_cast<const char*>(CMakeLists_txt);
        std::string EntryPoint_Final         = reinterpret_cast<const char*>(EntryPoint_Final_cpp);
        std::string EntryPoint_NativeScripts = reinterpret_cast<const char*>(EntryPoint_NativeScriptCore_cpp);
        std::string EventSubscriber          = reinterpret_cast<const char*>(EventSubscriber_hpp);
        std::string TeaSource                = reinterpret_cast<const char*>(Tea_cpp);
        std::string TeaHeader                = reinterpret_cast<const char*>(Tea_hpp);
    }
}