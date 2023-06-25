#pragma once

// Standard library
#include <filesystem>
#include <iostream>
#include <string>
#include <vector>
#include <map>
#include <functional>

#ifdef _WIN32

#include <Windows.h>

#endif

// dependencies
#include <GL/glew.h>
#include <GL/GL.h>
#include <GLFW/glfw3.h>
#include <glm/glm.hpp>

#include <imgui.h>
#include <imgui_impl_glfw.h>
#include <imgui_impl_opengl3.h>

// Engine

#include "utils/Activateable.hpp"
#include "utils/Time.hpp"
#include "utils/Random.hpp"
#include "utils/serialization/Serializable.hpp"
//#include "utils/console/console.hpp"
//#include "utils/console/ansi.hpp"
#include "etc/Component.hpp"
#include "etc/Entity.hpp" 

#include "graphics/Window.hpp"

#include "scene/GameEngine.hpp"


namespace Engine
{
    std::string HELLO_WORLD();
}