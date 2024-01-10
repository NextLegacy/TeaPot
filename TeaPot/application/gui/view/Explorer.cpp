#include "TeaPot/application/gui/view/Explorer.hpp"

#include <filesystem>

#include "TeaPot/application/TeaPot.hpp"

#include <BHW/utils/io/FileHandler.hpp>

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
            std::string path = data.m_path;

            if (path.empty()) path = teaPot.m_project.GetProjectMetaData().Path;

            for (auto& c : path)
            {
                if (c == '\\') c = '/';
            }

            if (path.back() != '/') path += '/';

            if (!std::filesystem::exists(path)) return;

            for (auto& p : std::filesystem::directory_iterator(path))
            {
                if (p.is_directory())
                {
                    if (ImGui::TreeNode(p.path().filename().string().c_str()))
                    {
                        RenderFiles(teaPot, data);

                        ImGui::TreePop();
                    }
                }
                else
                {
                    ImGui::Text(p.path().filename().string().c_str());
                }
            }

            ImGui::Separator();

            
        }
    }
}