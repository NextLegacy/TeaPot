#pragma once

#include <BoilingHotWater>

namespace BHW
{
    class ImGuiWindow
    {
    public:
        void Render();

    protected:
        virtual void OnRender() = 0;
    private:
        void Begin();
        void End();
    };
}