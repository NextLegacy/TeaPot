#include "TP/application/gui/view/Inspector.hpp"

#include "TP/application/TeaPot.hpp"

namespace TP
{
    namespace View
    {
        Inspector::Inspector(std::string name, BHW::Serializable* serializable, bool open) 
            : m_name(name), m_serializable(serializable), m_open(open) { }

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

            if (data.m_serializable == nullptr)
            {
                ImGui::End();
                return;
            }

            for (auto& [key, value] : data.m_serializable->GetJSONObject().Get<BHW::JSONMap>())
            {
                switch (value.GetType())
                {
                    case BHW::JSONType::String: RenderField<std::string>(key, value.Get<std::string>()); break;
                    //case JSONType::Int64 : break;
                    //case JSONType::Int32 : break;
                    //case JSONType::Int16 : break;
                    //case JSONType::Int8  : break;
                    //case JSONType::UInt64: break;
                    //case JSONType::UInt32: break;
                    //case JSONType::UInt16: break;
                    //case JSONType::UInt8 : break;
                    //case JSONType::Float : break;
                    //case JSONType::Double: break;
                    //case JSONType::Bool  : break;
                    //case JSONType::Array : break;
                    //case JSONType::Object: break;

                    //default: RenderField<void*>(key, value.Get<void*>()); break;
                }
            }

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