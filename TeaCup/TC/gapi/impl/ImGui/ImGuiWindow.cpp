#include "TC/gapi/impl/ImGui/ImGuiWindow.hpp"

#include <BHW/utils/console/Console.hpp>
#include <BHW/utils/random/Random.hpp>

namespace TC
{
    static BHW::Random random = BHW::Random(5);

    ImGuiWindow::ImGuiWindow(const std::string& name) : m_name(name), m_id(random.UInt64()), m_isVisible(true)
    {
    }

    ImGuiWindow::~ImGuiWindow()
    {
    }   

    void ImGuiWindow::Render()
    {
        if (m_isVisible) 
        {
            RenderImpl();
        }
    }
}