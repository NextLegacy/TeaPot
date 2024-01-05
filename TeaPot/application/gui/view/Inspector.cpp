#include "TeaPot/application/gui/view/Inspector.hpp"

#include "TeaPot/application/TeaPot.hpp"

#include "TeaPot/application/gui/Inspector.hpp"

namespace TP
{
    namespace View
    {
        Inspector::Inspector(std::string name, bool open) 
            : m_name(name), m_open(open) { }

        void InspectorRenderer::Render(TeaPot& teaPot)
        {
            teaPot.ForEachComponent<Inspector>([&](BHW::EntityUUID uuid, Inspector& data)
            {
                RenderInspector(teaPot, data);
            });
        }

        void InspectorRenderer::RenderInspector(TeaPot& teaPot, Inspector& data)
        {
            if (!data.m_open) return;

            ImGui::Begin(data.m_name.c_str(), &data.m_open);

            ImGuiInputField("Test", data.m_test);

            ImGui::End();
        }
    }
}