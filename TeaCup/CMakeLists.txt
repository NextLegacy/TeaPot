project(TeaCup)

add_library(TeaCup
    ${CMAKE_CURRENT_SOURCE_DIR}/TC/TeaCup.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TC/TeaCupRuntime.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TC/TeaCupRuntime.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TC/TeaCupBaseEventSystem.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TC/gapi/GAPI.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TC/gapi/GAPI.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TC/gapi/impl/OpenGl.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TC/gapi/impl/OpenGl.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TC/gapi/impl/GLFW.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TC/gapi/impl/GLFW.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TC/gapi/impl/GLFW_OpenGL.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TC/gapi/impl/GLFW_OpenGL.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TC/gapi/impl/DearImGui.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TC/gapi/impl/DearImGui.cpp
    
    ${CMAKE_CURRENT_SOURCE_DIR}/TC/gapi/impl/GLFW_OpenGL_DearImGui.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TC/gapi/impl/GLFW_OpenGL_DearImGui.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TC/input/Input.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TC/input/Input.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TC/input/InputAdapter.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TC/input/InputAdapter.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TC/input/Keyboard.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TC/input/Keyboard.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TC/input/Mouse.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TC/input/Mouse.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TC/input/Key.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TC/input/Key.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TC/input/MouseButton.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TC/input/MouseButton.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TC/input/MouseWheel.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TC/input/MouseWheel.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TC/input/KeyCode.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TC/input/KeyCode.cpp
)

target_include_directories(TeaCup PUBLIC 
    ${CMAKE_CURRENT_SOURCE_DIR}
)

target_link_libraries(TeaCup PUBLIC 
    OpenGL::GL
    glfw
    glm::glm
    glew
    imgui
    BoilingHotWater
)

set_target_properties(TeaCup PROPERTIES 
    #SUFFIX ".dll"
    ARCHIVE_OUTPUT_DIRECTORY ${OUTPUT_DIR}
    LIBRARY_OUTPUT_DIRECTORY ${OUTPUT_DIR}
    RUNTIME_OUTPUT_DIRECTORY ${OUTPUT_DIR}
    ARCHIVE_OUTPUT_DIRECTORY_DEBUG ${OUTPUT_DIR}
    LIBRARY_OUTPUT_DIRECTORY_DEBUG ${OUTPUT_DIR}
    RUNTIME_OUTPUT_DIRECTORY_DEBUG ${OUTPUT_DIR}
    ARCHIVE_OUTPUT_DIRECTORY_RELEASE ${OUTPUT_DIR}
    LIBRARY_OUTPUT_DIRECTORY_RELEASE ${OUTPUT_DIR}
    RUNTIME_OUTPUT_DIRECTORY_RELEASE ${OUTPUT_DIR}
)