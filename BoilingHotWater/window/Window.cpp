#include "window/Window.hpp"

namespace BHW
{
    Window::Window() : 
        m_input(API_CreateKeyMap()), m_size(0, 0), 
        m_title("BoilingHotWater Application"), m_keyMap(API_CreateKeyMap())
    {
    }

    Window::~Window()
    {
    }

    int  Window::InitializeWindow() { return API_InitializeWindow(); }
    void Window::StartUpdate     () { return API_StartUpdate     (); }
    void Window::EndUpdate       () { return API_EndUpdate       (); }
    void Window::StartFrame      () { return API_StartFrame      (); }
    void Window::EndFrame        () { return API_EndFrame        (); }
    void Window::TerminateWindow () { return API_TerminateWindow (); }

    void Window::SetSize (glm::ivec2         size ) { return API_SetSize (size ); }
    void Window::SetTitle(const std::string& title) { return API_SetTitle(title); }
}