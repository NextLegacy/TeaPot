#include "TeaPot/application/gui/view/SceneView.hpp"

#include "TeaPot/application/TeaPot.hpp"

#include "TeaPot/application/gui/Inspector.hpp"

namespace TP
{
    namespace View
    {
        SceneView::SceneView(std::string name, bool open) : m_name(name), m_open(open) { }

        void SceneViewRenderer::Render(TeaPot& teaPot)
        {
            teaPot.ForEachComponent<SceneView>([&](BHW::EntityUUID uuid, SceneView& data)
            {
                RenderSceneView(teaPot, data);
            });
        }

        void SceneViewRenderer::RenderSceneView(TeaPot& teaPot, SceneView& data)
        {
            if (!data.m_open) return;

            ImGui::Begin(data.m_name.c_str(), &data.m_open);

            ImGui::Text("Scene View");

            ProjectMetaData project = teaPot.m_project.GetProjectMetaData();

            ImGuiInputField("Test", project);
            
            ImGui::End();
        }
    }
}