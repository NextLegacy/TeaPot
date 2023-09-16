#pragma once

#include <glm/glm.hpp>

#include <BHW/utils/console/Console.hpp>
#include <BHW/utils/threads/Thread.hpp>

#include "TC/input/Input.hpp"

namespace TC
{
    class GAPI
    {
    public:
        GAPI();

        virtual int  InitializeWindow  () = 0;
        virtual void InitializeInput   () = 0;
        
        virtual void ProcessEvents() = 0;
        virtual void RenderFrame  () = 0;

        virtual void TerminateWindow() = 0;

        virtual bool IsWindowOpen() = 0;

        virtual void SetTitle(const std::string& title) = 0;
        virtual void SetSize (const glm::ivec2&  size)  = 0;
    
        inline TC::Input* GetInput() { return m_input; }
        inline glm::ivec2 GetSize () { return m_size ; }

    protected:
        virtual void Frame() = 0;

    protected:
        glm::ivec2 m_size;

    private:
        TC::Input* m_input;
    };
}