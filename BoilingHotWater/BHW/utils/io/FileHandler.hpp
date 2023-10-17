#pragma once

#include <string>

namespace BHW
{
    std::string ReadFile(const std::string& path);

    void WriteFile(const std::string& path, const std::string& content);

    bool FileExists(const std::string& path);
}