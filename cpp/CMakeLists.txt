cmake_minimum_required(VERSION 3.26.3)

project(Engine)

include(cmake/project.cmake)

add_executable(Engine WIN32 ${FILES})
#target_link_options(Engine PUBLIC -mwindows)
#add_library(Engine SHARED ${FILES})
#set_target_properties(Engine PROPERTIES SUFFIX ".dll")

target_include_directories(Engine PUBLIC ${INCLUDE_DIRS})

target_link_libraries(Engine PUBLIC ${LIBRARIES})