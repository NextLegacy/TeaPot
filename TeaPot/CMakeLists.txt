set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

include(Resources.cmake)

file(GLOB_RECURSE TeaPotSources *.cpp)
file(GLOB_RECURSE TeaPotHeaders *.hpp)

add_executable(TeaPot WIN32 ${TeaPotSources} ${TeaPotHeaders})

set_target_properties(TeaPot PROPERTIES
    RUNTIME_OUTPUT_DIRECTORY_DEBUG ${CMAKE_CURRENT_SOURCE_DIR}/../bin
    RUNTIME_OUTPUT_DIRECTORY_RELEASE ${CMAKE_CURRENT_SOURCE_DIR}/../bin
    RUNTIME_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/../bin
)

target_include_directories(TeaPot PUBLIC 
    $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/..>
    $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/../../ProjectTemplateFiles>
    $<INSTALL_INTERFACE:include>
)

target_link_libraries(TeaPot PUBLIC 
    BoilingHotWater::BoilingHotWater
    TeaCup::TeaCup
    Tea
)

#add_custom_command(
#        TARGET TeaPot POST_BUILD
#        COMMAND ${CMAKE_COMMAND} -E copy_directory
#        ${CMAKE_CURRENT_SOURCE_DIR}/../ProjectTemplateFiles ${CMAKE_CURRENT_SOURCE_DIR}/../bin/ProjectTemplateFiles
#        COMMENT "Copying ProjectTemplateFiles to ${OUTPUT_DIR}/ProjectTemplateFiles"
#)