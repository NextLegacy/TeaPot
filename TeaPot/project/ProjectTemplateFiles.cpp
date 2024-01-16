#include "TeaPot/project/ProjectTemplateFiles.hpp"

#include "TeaPot/project/files/CMakeLists.txt.hpp"
#include "TeaPot/project/files/EntryPoint_Final.cpp.hpp"
#include "TeaPot/project/files/NativeScripts.cpp.hpp"
#include "TeaPot/project/files/Tea.cpp.hpp"
#include "TeaPot/project/files/Tea.hpp.hpp"
#include "TeaPot/project/files/Resources.hpp.hpp"

namespace TP
{
    namespace ProjectTemplateFiles
    {
        std::string CMakeLists               = reinterpret_cast<const char*>(CMakeLists_txt);
        std::string EntryPoint_Final         = reinterpret_cast<const char*>(EntryPoint_Final_cpp);
        std::string NativeScripts            = reinterpret_cast<const char*>(NativeScripts_cpp);
        std::string TeaSource                = reinterpret_cast<const char*>(Tea_cpp);
        std::string TeaHeader                = reinterpret_cast<const char*>(Tea_hpp);
        std::string ResourcesHeader          = reinterpret_cast<const char*>(Resources_hpp);
    }
}