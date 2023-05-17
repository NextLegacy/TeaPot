set(GLFW_BUILD_DOCS OFF CACHE BOOL "" FORCE)
set(GLFW_BUILD_TESTS OFF CACHE BOOL "" FORCE)
set(GLFW_BUILD_EXAMPLES OFF CACHE BOOL "" FORCE)

add_subdirectory(${GLFW_DIR})

set(GLFW_INCLUDE_DIR ${GLFW_DIR}/include)

# if needed in future add OPEN_GL libraries here like this and add OpenGL::GL to target_link_libraries()
# find_package(OpenGL REQUIRED)
# set(OPENGL_LIBRARIES OpenGL::GL)

set(GLFW_LIBRARIES glfw)