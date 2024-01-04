cmake_minimum_required(VERSION 3.28.1)

project(DigitalTea)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

set(CMAKE_INSTALL_PREFIX bin)

include(${CMAKE_CURRENT_SOURCE_DIR}/cmake/dependencies.cmake)

add_subdirectory(Tea            )
add_subdirectory(TeaBrewer      )
add_subdirectory(TeaPot         )

add_custom_command(
        TARGET ${PROJECT_NAME} POST_BUILD
        COMMAND ${CMAKE_COMMAND} -E copy_directory
        ${CMAKE_CURRENT_SOURCE_DIR}/ProjectTemplateFiles bin/ProjectTemplateFiles
        COMMENT "Copying ProjectTemplateFiles to ${OUTPUT_DIR}/ProjectTemplateFiles"
)