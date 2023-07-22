#include "TeaPot.hpp"

int EntryPoint()
{
    TeaPot::Editor editor;

    editor.Activate();

    editor.Join();

    editor.Deactivate();

    return 0;
}