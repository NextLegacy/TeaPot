#pragma once

namespace BHW
{
    class GraphicsAPI
    {
    public:
        virtual int  InitializeWindow() = 0;

        virtual void StartUpdate() = 0;
        virtual void EndUpdate  () = 0;

        virtual void StartFrame   () = 0;
        virtual void EndFrame     () = 0;

        virtual void TerminateWindow() = 0;
    
    private:
        GraphicsAPI() = default;

        friend class Window;
    };
}