#pragma once

#include <string>

#ifdef _WIN32
#include <windows.h>
#include <shobjidl.h>
#include <shobjidl_core.h>
#include <comdef.h>
#endif

namespace BHW
{
    std::string ReadFile(const std::string& path);

    void WriteFile(const std::string& path, const std::string& content);

    bool FileExists(const std::string& path);

    void CreateFolder(const std::string& path);
    void CreateFile(const std::string& path);

    std::string SelectFolder(const std::string& title, const std::string& defaultPath, HWND parent);
}