#include "TP/application/gui/view/Explorer.hpp"

#include <filesystem>

#include "TP/application/TeaPot.hpp"

namespace TP
{
    namespace View
    {
        Explorer::Explorer(std::string name, std::string path, bool open) : m_name(name), m_open(open), m_path(path) { }

        void ExplorerRenderer::Render(TeaPot& teaPot)
        {
            teaPot.ForEachComponent<Explorer>([&](BHW::EntityUUID uuid, Explorer& data)
            {
                RenderExplorer(teaPot, data);
            });
        }

        void ExplorerRenderer::RenderExplorer(TeaPot& teaPot, Explorer& data)
        {
            if (!data.m_open) return;

            ImGui::Begin(data.m_name.c_str(), &data.m_open);

            RenderFiles(teaPot, data);

            ImGui::End();
        }

        void ExplorerRenderer::RenderFiles(TeaPot& teaPot, Explorer& data)
        {
            if(!(std::filesystem::exists(data.m_path) && std::filesystem::is_directory(data.m_path))) return;

            if (m_files.find(data.m_path) == m_files.end())
            {
                std::vector<File> files;

                for (auto& p : std::filesystem::directory_iterator(data.m_path))
                {
                    files.push_back({ p.path().filename().string() });
                }

                m_files[data.m_path] = files;
            }

            std::vector<File> files = m_files[data.m_path];


            for (auto& file : files)
            {
                ImGui::Text(file.m_fileName.c_str());
            }
        }
    }
}