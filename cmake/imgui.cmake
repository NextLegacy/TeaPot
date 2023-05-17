file(GLOB IMGUI_SOURCES ${IMGUI_DIR}/*.cpp)
file(GLOB IMGUI_HEADERS ${IMGUI_DIR}/*.h)

set(IMGUI_BACKENDS_DIR ${IMGUI_DIR}/backends)

set(IMGUI_BACKEND_SOURCES
    ${IMGUI_BACKENDS_DIR}/imgui_impl_glfw.cpp
    ${IMGUI_BACKENDS_DIR}/imgui_impl_opengl3.cpp
    #${IMGUI_BACKENDS_DIR}/imgui_impl_vulkan.cpp
)
set(IMGUI_BACKEND_HEADERS
    ${IMGUI_BACKENDS_DIR}/imgui_impl_glfw.h
    ${IMGUI_BACKENDS_DIR}/imgui_impl_opengl3.h
    #${IMGUI_BACKENDS_DIR}/imgui_impl_vulkan.h
)

set(IMGUI_FILES
    ${IMGUI_SOURCES}
    ${IMGUI_HEADERS}
    
    ${IMGUI_BACKEND_SOURCES}
    ${IMGUI_BACKEND_HEADERS}
)

set(IMGUI_INCLUDE_DIRS
    ${IMGUI_DIR}
    ${IMGUI_BACKENDS_DIR}
    #${Vulkan_INCLUDE_DIR}
   # ${GLFW_INCLUDE_DIR}
    #${SDL2_INCLUDE_DIRS}
)

set(IMGUI_LIBRARIES
    #${Vulkan_LIBRARIES}
    ##${OPENGL_LIBRARIES}
    ##${GLEW_LIBRARIES}
    ##${GLFW_LIBRARIES}
    #${SDL2_LIBRARIES}
)

#set_target_properties(imgui PROPERTIES LINKER_LANGUAGE CXX)
#set_target_properties(imgui PROPERTIES FOLDER dependencies)

#set(IMGUI_LIBRARIES imgui)