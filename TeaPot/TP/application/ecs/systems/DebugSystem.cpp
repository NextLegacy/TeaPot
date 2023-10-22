#include "TP/application/ecs/systems/DebugSystem.hpp"

#include "TP/application/TeaPot.hpp"

#include "TP/project/TeaPotProject.hpp"

namespace TP
{
    void DebugSystem::Start(TeaPot& teaPot)
    {
        TeaPotProject project = LoadOrCreateProject("C:/Users/mertc/Desktop/Test/TestProject");

        BHW::Console::WriteLine("Project name: " + project.m_projectName);
        BHW::Console::WriteLine("Project number: " + std::to_string(project.m_someNumber));
    }

    void DebugSystem::Update(TeaPot& teaPot)
    {
        //BHW::Console::WriteLine("FPS: " + std::to_string(teaPot.GetFPS()));
    }

    void DebugSystem::Render(TeaPot& teaPot)
    {
        //ImGui::ShowDemoWindow();
    }   
}