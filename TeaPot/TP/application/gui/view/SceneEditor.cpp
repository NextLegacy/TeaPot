#include "TP/application/gui/view/SceneEditor.hpp"

#include "TP/application/TeaPot.hpp"

namespace TP
{
    namespace View
    {
        SceneEditor::SceneEditor(std::string name, bool open) : m_name(name), m_open(open) { }

        void SceneEditorRenderer::Render(TeaPot& teaPot)
        {
            teaPot.ForEachComponent<SceneEditor>([&](BHW::EntityUUID uuid, SceneEditor& data)
            {
                RenderSceneEditor(teaPot, data);
            });
        }

        void SceneEditorRenderer::RenderSceneEditor(TeaPot& teaPot, SceneEditor& data)
        {
            if (!data.m_open) return;

            ImGui::Begin(data.m_name.c_str(), &data.m_open);

            ImGui::Text("Scene Editor");
            
            ImGui::End();
        }
    }
}