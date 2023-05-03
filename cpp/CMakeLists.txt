cmake_minimum_required(VERSION 3.26.3)
set(CMAKE_CXX_STANDARD 20)

project(Engine)

include(cmake/project.cmake)

#add_executable(Engine WIN32 ${FILES})
#target_link_options(Engine PUBLIC -mwindows)
add_library(Engine SHARED ${FILES})

target_include_directories(Engine PUBLIC ${INCLUDE_DIRS})

target_link_libraries(Engine PUBLIC ${LIBRARIES})

message(${CMAKE_BINARY_DIR})

set_target_properties(Engine PROPERTIES 
    SUFFIX ".dll"
    #ARCHIVE_OUTPUT_DIRECTORY "../../lib"
    #LIBRARY_OUTPUT_DIRECTORY "../../lib"
    RUNTIME_OUTPUT_DIRECTORY "../../bin"
)