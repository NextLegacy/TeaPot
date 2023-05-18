cmake_minimum_required(VERSION 3.26.3)
set(CMAKE_CXX_STANDARD 20)

project(Engine)

include(cmake/project.cmake)

#include(cmake/library.cmake)
#include(cmake/executable.cmake)

add_subdirectory(core)
add_subdirectory(editor)
add_subdirectory(wrapper)
add_subdirectory(scripting/java)