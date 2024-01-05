#pragma once

#include <BHW/utils/reflection/Reflection.hpp>
#include <TC/gapi/impl/DearImGui.hpp>

namespace TP
{
    template<typename T>
    inline void ImGuiInputField(std::string name, T& object)
    {
        ImGui::Text(name.c_str());

        if constexpr (BHW::IsRegistered<T>())
        {
            std::apply([&](auto&&... members) { ((ImGuiInputField(std::string(members.Name.begin(), members.Name.end()), members(object))), ...); }, BHW::GetMembersOfType<T>());
        }
        else
        {
            ImGui::Text("Type is not serializable");
        }
    }

    template<> inline void ImGuiInputField<int        >(std::string name, int&         object) { ImGui::InputInt   (name.c_str(), &object); }
    template<> inline void ImGuiInputField<float      >(std::string name, float&       object) { ImGui::InputFloat (name.c_str(), &object); }
    template<> inline void ImGuiInputField<double     >(std::string name, double&      object) { ImGui::InputDouble(name.c_str(), &object); }
    template<> inline void ImGuiInputField<bool       >(std::string name, bool&        object) { ImGui::Checkbox   (name.c_str(), &object); }
    template<> inline void ImGuiInputField<std::string>(std::string name, std::string& object) { char buffer[256]; strcpy_s(buffer, object.c_str()); ImGui::InputText(name.c_str(), buffer, 256); object = buffer; }
}