#include "TP/application/gui/view/Inspector.hpp"

#include "TP/application/TeaPot.hpp"

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


            /*
            if (data.m_data == nullptr)
            {
                ImGui::End();
                return;
            }

            for (const auto& [name, member] : data.m_type.
            {
                ImGui::Text(member.Name.c_str());
            }
            */

            ImGui::End();
        }

        template<>
        void InspectorRenderer::RenderField<std::string>(const std::string& name, std::string& value)
        {
            //ImGui::InputText("##string", value.c_str(), sizeof(value));
        }
    
        //template<>
        //void InspectorRenderer::RenderField<void*>(std::string& name, void*& object)
        //{
        //    ImGui::Text(name.c_str());
        //}
    }
}