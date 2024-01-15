#include "TeaPot/application/gui/view/Explorer.hpp"

#include <filesystem>

#include "TeaPot/application/TeaPot.hpp"

#include <BHW/utils/io/FileHandler.hpp>

namespace TP
{
    namespace View
    {
        Explorer::Explorer(std::string name, std::string path, bool open) : m_name(name), m_open(open)
        {
            m_baseDirectory    = path;
            m_currentDirectory = path;
        }

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

            if (teaPot.m_project.IsLoaded())
            {
                data.m_baseDirectory    = teaPot.m_project.GetProjectMetaData().Path;
                data.m_currentDirectory = teaPot.m_project.GetProjectMetaData().Path;

                RenderPath(data);
            }
            else
            {
                if (ImGui::Button("Load or Create Project"))
                {
                    teaPot.m_project.Load(BHW::SelectFolder("Select Project Folder Path", "", teaPot.GetWindowHandle())); 
                }
            }

            ImGui::End();
        }

        void ExplorerRenderer::RenderPath(Explorer& data)
        {
            // https://github.com/TheCherno/Hazel/blob/master/Hazelnut/src/Panels/ContentBrowserPanel.cpp
            // Currently testing a bit around
            // TODO: refactor refactor refactor

            if (data.m_currentDirectory != std::filesystem::path(data.m_baseDirectory))
            {
                if (ImGui::Button("<-"))
                {
                    data.m_currentDirectory = data.m_currentDirectory.parent_path();
                }
            }

            static float padding = 16.0f;
            static float thumbnailSize = 128.0f;
            float cellSize = thumbnailSize + padding;

            float panelWidth = ImGui::GetContentRegionAvail().x;
            int columnCount = (int)(panelWidth / cellSize);
            if (columnCount < 1)
                columnCount = 1;

            ImGui::Columns(columnCount, 0, false);

            for (auto& directoryEntry : std::filesystem::directory_iterator(data.m_currentDirectory))
            {
                const auto& path = directoryEntry.path();
                std::string filenameString = path.filename().string();
                
                ImGui::PushID(filenameString.c_str());
                //Ref<Texture2D> icon = directoryEntry.is_directory() ? m_DirectoryIcon : m_FileIcon;
                ImGui::PushStyleColor(ImGuiCol_Button, ImVec4(0, 0, 0, 0));
                ImGui::ImageButton((ImTextureID)0, { thumbnailSize, thumbnailSize }, { 0, 1 }, { 1, 0 });

                if (ImGui::BeginDragDropSource())
                {
                    std::filesystem::path relativePath(path);
                    const wchar_t* itemPath = relativePath.c_str();
                    ImGui::SetDragDropPayload("CONTENT_BROWSER_ITEM", itemPath, (wcslen(itemPath) + 1) * sizeof(wchar_t));
                    ImGui::EndDragDropSource();
                }

                ImGui::PopStyleColor();
                if (ImGui::IsItemHovered() && ImGui::IsMouseDoubleClicked(ImGuiMouseButton_Left))
                {
                    if (directoryEntry.is_directory())
                        data.m_currentDirectory /= path.filename();

                }
                ImGui::TextWrapped(filenameString.c_str());

                ImGui::NextColumn();

                ImGui::PopID();
            }

            ImGui::Columns(1);

            ImGui::SliderFloat("Thumbnail Size", &thumbnailSize, 16, 512);
            ImGui::SliderFloat("Padding", &padding, 0, 32);
        }
    }
}