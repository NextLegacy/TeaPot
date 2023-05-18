#include "process.h"

#include <stdlib.h>
#include <string>

namespace engine
{
    namespace process
    {
        const std::string javaHome = getenv("JAVA_HOME");

        const std::string java = javaHome + "\\bin\\java.exe";  
        
        void runJar(std::string file)
        {
            return;
            std::string completeCommand = java + " -jar " + file;

            system(completeCommand.c_str());
        }
    }
}