#include "BHW/utils/io/FileHandler.hpp"

#include <fstream>
#include <filesystem>
#include "BHW/utils/Debug.hpp"

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

    void CreateFolder(const std::string& path)
    {
        std::filesystem::create_directories(path);
    }

    void CreateFile(const std::string& path)
    {
        std::ofstream file(path);
    }

    std::string SelectFolder(const std::string& title, const std::string& defaultPath, HWND parent)
    {
#ifdef _WIN32

        std::wstring folderPath;
        IFileOpenDialog* pfd;

        HRESULT hr = CoInitializeEx(NULL, COINIT_APARTMENTTHREADED | COINIT_DISABLE_OLE1DDE);
        if (SUCCEEDED(hr))
        {
            hr = CoCreateInstance(CLSID_FileOpenDialog, NULL, CLSCTX_ALL, IID_IFileOpenDialog, reinterpret_cast<void**>(&pfd));
            if (SUCCEEDED(hr))
            {
                DWORD dwOptions;
                if (SUCCEEDED(pfd->GetOptions(&dwOptions)))
                {
                    pfd->SetOptions(dwOptions | FOS_PICKFOLDERS);
                }

                hr = pfd->Show(parent);
                if (SUCCEEDED(hr))
                {
                    IShellItem* psi;
                    hr = pfd->GetResult(&psi);
                    if (SUCCEEDED(hr))
                    {
                        PWSTR pszPath;
                        hr = psi->GetDisplayName(SIGDN_FILESYSPATH, &pszPath);
                        if (SUCCEEDED(hr))
                        {
                            folderPath = pszPath;
                            CoTaskMemFree(pszPath);
                        }
                        psi->Release();
                    }
                }
                pfd->Release();
            }
            CoUninitialize();
        }

        return std::string(folderPath.begin(), folderPath.end());
#else
        return "";
#endif
    }
}