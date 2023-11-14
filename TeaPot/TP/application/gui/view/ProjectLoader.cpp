#include "TP/application/gui/view/ProjectLoader.hpp"

#include <BHW/utils/io/FileHandler.hpp>

#include "TP/application/TeaPot.hpp"

namespace TP
{
    namespace View
    {
        ProjectLoader::ProjectLoader(std::string name, bool open) : m_name(name), m_open(open) { }

        void ProjectLoaderRenderer::Render(TeaPot& teaPot)
        {
            teaPot.ForEachComponent<ProjectLoader>([&](BHW::EntityUUID uuid, ProjectLoader& data)
            {
                RenderProjectLoader(teaPot, data);
            });
        }

        void ProjectLoaderRenderer::RenderProjectLoader(TeaPot& teaPot, ProjectLoader& data)
        {
            if (!data.m_open) return;

            // Windows Select folder screen

            ImGui::Begin(data.m_name.c_str(), &data.m_open);

            ImGui::Text("Load a project");

            ImGui::InputText("Path", (char*)data.m_path.c_str(), data.m_path.size());

            ImGui::SameLine();

            if (ImGui::Button("Select folder"))
            {
                data.m_path = BHW::SelectFolder("Select a folder", "", teaPot.GetWindowHandle());
            }

            ImGui::End();

            //ImGui::Begin(data.m_name.c_str(), &data.m_open);
//
            //ImGui::Text("Load a project");
            //
            //ImGui::End();
        }
    }
}