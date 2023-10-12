#pragma once

#include <TC/gapi/impl/ImGui/ImGuiDockspace.hpp>
#include <BHW/utils/console/Console.hpp>

#include <imgui_internal.h>

namespace TP
{
    class TeaPot;

    class Dockspace : public TC::ImGuiDockspace
    {
    public:
        Dockspace(TeaPot& teaPot);

    protected:
        virtual void DockSpaceImpl() override;

    private:
        TeaPot& m_teaPot;
    };
}