set(PROJECT_NAME ENGINE_CORE)
set(PROJECT_VERSION 1.0.0)

# project directories

set(PROJECT_DIR ${CMAKE_SOURCE_DIR})

set(CORE_DIR     ${PROJECT_DIR}/core)
set(EDITOR_DIR   ${PROJECT_DIR}/editor)
set(DEMO_DIR     ${PROJECT_DIR}/demo)
set(WRAPPER_DIR  ${PROJECT_DIR}/wrapper)
set(JAVA_API_DIR ${PROJECT_DIR}/scripting/java/api/)
set(JAVA_CORE_DIR ${PROJECT_DIR}/scripting/java/core/src/main/java/)
set(DEMO_JAVA_DIR ${PROJECT_DIR}/demo/java/src/main/java)

#set(SOURCE_DIR       ${PROJECT_DIR}/src         )          
#set(RESOURCE_DIR     ${PROJECT_DIR}/resources   )
set(OUTPUT_DIR       ${PROJECT_DIR}/bin         )
set(DEPENDENCIES_DIR ${PROJECT_DIR}/dependencies)

# project files

file(GLOB_RECURSE CORE_FILES
    ${CORE_DIR}/*.cpp
    ${CORE_DIR}/*.h
)

file(GLOB_RECURSE EDITOR_FILES
    ${EDITOR_DIR}/*.cpp
    ${EDITOR_DIR}/*.h
)

file(GLOB_RECURSE DEMO_FILES
    ${DEMO_DIR}/*.java
)

file(GLOB_RECURSE WRAPPER_FILES
    ${WRAPPER_DIR}/*.cpp
    ${WRAPPER_DIR}/*.h
)

file(GLOB_RECURSE JAVA_API_FILES
    ${JAVA_API_DIR}/*.cpp
    ${JAVA_API_DIR}/*.h
)

file(GLOB_RECURSE JAVA_CORE_FILES
    ${JAVA_CORE_DIR}/*.java
)

file(GLOB_RECURSE DEMO_JAVA_FILES
    ${DEMO_JAVA_DIR}/*.java
)

set(FILES
    ${CORE_FILES}
    ${EDITOR_FILES}
    ${DEMO_FILES}
    ${WRAPPER_FILES}
    ${JAVA_API_FILES}
)

#file(GLOB_RECURSE FILES
#    ${SOURCE_DIR}/*.cpp
#    ${SOURCE_DIR}/*.h
#)

#set(FILES_WITH_UNCOMPLETE_PATH
#    testing/app
#    java/JNITest
#)
#
#set(FILES "")
#
#foreach(File ${FILES_WITH_UNCOMPLETE_PATH})
#    list(APPEND FILES ${SOURCE_DIR}/${File}.cpp)
#    list(APPEND FILES ${SOURCE_DIR}/${File}.h)
#endforeach()

# dependencies

set(GLFW_DIR ${DEPENDENCIES_DIR}/glfw)
set(IMGUI_DIR ${DEPENDENCIES_DIR}/imgui)
set(GLEW_DIR ${DEPENDENCIES_DIR}/glew)

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

    #${CORE_DIR}
    #${EDITOR_DIR}
    #${DEMO_DIR}
    #${WRAPPER_DIR}
    #${JAVA_API_DIR}
    
    #${SOURCE_DIR}
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