#pragma once

#include "window/input/Input.hpp"

#include <iostream>

#include <map>

namespace BHW
{
    class Window
    {
    public:
        Window();
        virtual ~Window();

        int  InitializeWindow();

        void StartUpdate();
        void EndUpdate  ();

        void StartFrame();
        void EndFrame  ();

        void TerminateWindow();

        inline int          Width () { return m_size.x; }
        inline int          Height() { return m_size.y; }
        inline glm::ivec2&  Size  () { return m_size  ; }
        inline std::string& Title () { return m_title ; }

        virtual void SetSize (glm::ivec2         size );
        virtual void SetTitle(const std::string& title);

    protected:
        inline KeyCode MapKey(int key) const { return m_keyMap.at(key); }
        inline Input&  Input () { return m_input ; }

    protected:
        virtual int  API_InitializeWindow() = 0;

        virtual void API_StartUpdate() = 0;
        virtual void API_EndUpdate  () = 0;

        virtual void API_StartFrame() = 0;
        virtual void API_EndFrame  () = 0;
        
        virtual void API_TerminateWindow () = 0;

        virtual void API_SetSize (glm::ivec2         size ) = 0;
        virtual void API_SetTitle(const std::string& title) = 0;

        virtual std::map<int, KeyCode> API_CreateKeyMap() = 0;

    private:
        BHW::Input  m_input;

        glm::ivec2  m_size;
        std::string m_title;

        std::map<int, KeyCode> m_keyMap;
    };
}