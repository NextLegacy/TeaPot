#pragma once

#include <glm/glm.hpp>

#include "TC/input/Input.hpp"

namespace TC
{
    class GAPI
    {
    public:
        GAPI();

    public:
        virtual int  InitializeWindow() = 0;

        virtual void InitializeInput () = 0;

        virtual void InitializeFrameThread() = 0;

        virtual void ProcessEvents() = 0;

        virtual void RenderFrame  () = 0;

        virtual void TerminateWindow() = 0;

        virtual bool IsWindowOpen() = 0;

    public:
        virtual void SetTitle   (const std::string& title   ) = 0;
        virtual void SetSize    (const glm::ivec2&  size    ) = 0;
        //virtual void SetPosition(const glm::ivec2&  position) = 0;

    public:
        inline Input&     GetInput() { return m_input; }
        inline glm::ivec2 GetSize () { return m_size ; }

    protected:
        virtual void Frame() = 0;

    protected:
        glm::ivec2 m_size ;
        Input      m_input;
    };
}