cmake_minimum_required(VERSION 3.28.1)

project(DigitalTea)

include(cmake/project.cmake)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

set(CMAKE_INSTALL_PREFIX ${OUTPUT_DIR})

add_subdirectory(BoilingHotWater)
add_subdirectory(TeaCup         )
add_subdirectory(Tea            )
add_subdirectory(TeaBrewer      )
add_subdirectory(TeaPot         )