#include "process.h"

namespace engine
{
    namespace process
    {
        const std::string javaHome = getenv("JAVA_HOME");

        const std::string java = javaHome + "/bin/java.exe";  
        
        void runJar(std::string file, std::string className)
        {
            std::string completeCommand = "\"" + java +  "\"" + " -cp " + file + className;
            
            system(completeCommand.c_str());
        }
    }
}