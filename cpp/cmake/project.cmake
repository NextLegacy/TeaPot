set(PROJECT_NAME ENGINE_CORE)

# project directories

set(PROJECT_DIR ${CMAKE_SOURCE_DIR})

set(SOURCE_DIR       ${PROJECT_DIR}/src         )          
set(RESOURCE_DIR     ${PROJECT_DIR}/resources   )
set(OUTPUT_DIR       ${PROJECT_DIR}/bin         )
set(DEPENDENCIES_DIR ${PROJECT_DIR}/dependencies)

# project files

set(FILES_WITH_UNCOMPLETE_PATH
    testing/app
    java/JNITest
)

set(FILES "")

foreach(File ${FILES_WITH_UNCOMPLETE_PATH})
    list(APPEND FILES ${SOURCE_DIR}/${File}.cpp)
    list(APPEND FILES ${SOURCE_DIR}/${File}.h)
endforeach()

# dependencies

set(GLFW_DIR ${DEPENDENCIES_DIR}/glfw-3.3.8)
set(IMGUI_DIR ${DEPENDENCIES_DIR}/imgui-1.89.5)
set(GLEW_DIR ${DEPENDENCIES_DIR}/glew-2.1.0)

set(DEPENDENCIES
    ${GLFW_DIR}
    ${IMGUI_DIR}
    ${GLEW_DIR}
)

# cmake files

set(CMAKE_FILES
    glfw.cmake
    #vulkan.cmake
    imgui.cmake
    java.cmake
    opengl.cmake
)

foreach(CMAKE_FILE ${CMAKE_FILES})
    include(cmake/${CMAKE_FILE})
endforeach()

# collecting

list(APPEND FILES ${JAVA_SOURCES})
list(APPEND FILES ${IMGUI_FILES})

set(INCLUDE_DIRS
    ${GLFW_INCLUDE_DIR}
    #${VULKAN_INCLUDE_DIR}
    ${OPENGL_INCLUDE_DIRS}
    ${GLEW_INCLUDE_DIRS}
    ${IMGUI_INCLUDE_DIRS}
    ${JAVA_INCLUDE_DIRS}

    ${SOURCE_DIR}
)

set(LIBRARIES
    ${GLFW_LIBRARIES}
    #Vulkan::Vulkan 
    #${VULKAN_LIBRARIES}
    ${OPENGL_LIBRARIES}
    ${GLEW_LIBRARIES}
    ${IMGUI_LIBRARIES}
    ${JAVA_LIBRARIES}
)