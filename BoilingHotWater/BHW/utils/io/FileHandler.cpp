#include "BHW/utils/io/FileHandler.hpp"

#include <fstream>

namespace BHW
{
    std::string ReadFile(const std::string& path)
    {
        std::ifstream file(path);

        std::string result;

        std::string line;

        while (std::getline(file, line))
        {
            result += line + "\n";
        }

        return result;
    }

    void WriteFile(const std::string& path, const std::string& content)
    {
        std::ofstream file(path);

        file << content;
    }

    bool FileExists(const std::string& path)
    {
        std::ifstream file(path);

        return file.good();
    }
}