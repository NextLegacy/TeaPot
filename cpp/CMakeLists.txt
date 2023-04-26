cmake_minimum_required(VERSION 3.0.0)
project(Engine VERSION 0.1.0)

find_package(Java REQUIRED)
find_package(JNI REQUIRED)

include(UseJava)

set(CMAKE_JAVA_COMPILE_FLAGS "-source" "1.6" "-target" "1.6")

include_directories(
    ${JNI_INCLUDE_DIRS} 
    ${_classDir} 
    ${_stubDir}

    libs/
    libs/include/
    libs/include/GLFW/
    libs/lib-vc2022/
    src/
    src/java/
    src/java/testing/
    src/opengl/
)

set(GLFW_BUILD_DOCS OFF CACHE BOOL "" FORCE)
set(GLFW_BUILD_TESTS OFF CACHE BOOL "" FORCE)
set(GLFW_BUILD_EXAMPLES OFF CACHE BOOL "" FORCE)

add_library(Engine MODULE 
    src/java/testing/JNITest.cpp
    src/java/testing/JNITest.h

    src/opengl/OpenGL.cpp
    src/opengl/OpenGL.h
)

set_target_properties(Engine PROPERTIES SUFFIX ".dll")
target_link_libraries(Engine PUBLIC
    ${JNI_LIBRARIES}

    libs/include/GLFW/glfw3.h
    libs/include/GLFW/glfw3native.h
    libs/lib-vc2022/glfw3.lib
    libs/lib-vc2022/glfw3dll.lib
    libs/lib-vc2022/glfw3dll.dll.lib
    libs/lib-vc2022/glfw3d_mt.lib
)