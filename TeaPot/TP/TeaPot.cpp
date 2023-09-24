#include "TP/TeaPot.hpp"

struct ApplicationSystem : public TP::TeaPotBaseEventSystem
{
    using TP::TeaPotBaseEventSystem::TeaPotBaseEventSystem;

    inline void Start() override
    {
        BHW::Console::WriteLine("Start!");
    }

    inline void Update() override
    {
        BHW::Console::WriteLine("Update!");
    }

    inline void FixedUpdate() override
    {
        BHW::Console::WriteLine("FixedUpdate!");
    }

    inline void Render() override
    {
        BHW::Console::WriteLine("Render!");
    }

    inline void End() override
    {
        BHW::Console::WriteLine("End!");
    }
};

int BHW::EntryPoint(std::vector<std::string> args)
{
    TP::Editor editor;

    BHW::Console::InitConsole();

    editor.SubscribeEventSystem<ApplicationSystem>(&editor);

    editor.Run();

    return 0; 
}