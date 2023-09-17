#pragma once 

#include "TC/gapi/impl/OpenGL.hpp"
#include "TC/gapi/impl/GLFW.hpp"

namespace TC
{
    class GLFW_OpenGL : public GLFW, public OpenGL
    {
    protected:
        virtual void SetWindowHints() override;
    };
}