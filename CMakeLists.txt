cmake_minimum_required(VERSION 3.26.3)

project(DigitalTea)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

include(cmake/project.cmake)     # set up project variables

add_subdirectory(BoilingHotWater)
add_subdirectory(TeaCup)
add_subdirectory(TeaPot)