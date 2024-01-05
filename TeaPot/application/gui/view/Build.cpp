#include "TeaPot/application/gui/view/Build.hpp"

#include "TeaPot/application/TeaPot.hpp"

namespace TP
{
    namespace View
    {
        Build::Build(std::string name, bool open) : m_name(name), m_open(open) { }

        void BuildRenderer::Render(TeaPot& teaPot)
        {
            teaPot.ForEachComponent<Build>([&](BHW::EntityUUID uuid, Build& data)
            {
                RenderBuild(teaPot, data);
            });
        }

        void BuildRenderer::RenderBuild(TeaPot& teaPot, Build& data)
        {
            if (!data.m_open) return;

            ImGui::Begin(data.m_name.c_str(), &data.m_open);

            ImGui::Text("Build");
            
            ImGui::End();
        }
    }
}