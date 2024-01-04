cmake_minimum_required(VERSION 3.26.3)

project(Tea)

set(TEA_PROJECT_NAME {})
set(OUTPUT_DIR ${{CMAKE_CURRENT_SOURCE_DIR}}/../{})
set(TEA_TYPE {})
set(SOURCES 
{})
set(DEPENDENCIES 
{})

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

set(TEA_BIN_DIR {})
set(TEA_INCLUDE_DIR ${{Engine_BIN_DIR}}/include)
set(TEA_LIB_DIR ${{Engine_BIN_DIR}}/lib)

find_package(BoilingHotWater REQUIRED CONFIG PATHS ${{TEA_BIN_DIR}}/lib/cmake/BoilingHotWater)

include_directories(${{TEA_INCLUDE_DIR}})
link_directories(${{TEA_LIB_DIR}})

add_subdirectory(Final)
add_subdirectory(Intermediate)