#include "TeaPot.hpp"

#include <glm/glm.hpp>

// enablel swizzle operators
#define GLM_SWIZZLE
#include <glm/gtx/vec_swizzle.hpp>

int EntryPoint()
{
    TeaPot::Editor editor;

    editor.Activate();

    editor.Join();

    editor.Deactivate();

    glm::vec3 vec3(1.0f, 2.0f, 3.0f);

    return 0;
}