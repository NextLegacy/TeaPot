#include "TP/application/gui/view/SceneView.hpp"

#include "TP/application/TeaPot.hpp"

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
            
            ImGui::End();
        }
    }
}