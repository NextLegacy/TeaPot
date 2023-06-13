set(DEPENDENCIES_DIR ${CMAKE_CURRENT_SOURCE_DIR}/dependencies)

set(GLFW_DIR  ${DEPENDENCIES_DIR}/glfw )
set(GLM_DIR   ${DEPENDENCIES_DIR}/glm  )
set(GLEW_DIR  ${DEPENDENCIES_DIR}/glew-2.2.0)
set(IMGUI_DIR ${DEPENDENCIES_DIR}/imgui)

set(DEPENDENCIES_OUTPUT_DIR ${CMAKE_CURRENT_BINARY_DIR}/dependencies)

include(cmake/dependencies/opengl.cmake)
include(cmake/dependencies/glew.cmake)
include(cmake/dependencies/glfw.cmake)
include(cmake/dependencies/glm.cmake)

include(cmake/dependencies/imgui.cmake)

include(cmake/dependencies/vulkan.cmake)

include(cmake/dependencies/java.cmake)