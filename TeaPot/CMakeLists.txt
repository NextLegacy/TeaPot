file(GLOB_RECURSE TeaPotSources *.cpp)
file(GLOB_RECURSE TeaPotHeaders *.hpp)

add_executable(TeaPot WIN32 ${TeaPotSources} ${TeaPotHeaders})

#dont create debug and release folders
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/../bin)
set_target_properties(TeaPot PROPERTIES
    RUNTIME_OUTPUT_DIRECTORY_DEBUG ${CMAKE_CURRENT_SOURCE_DIR}/../bin
)

target_include_directories(TeaPot PUBLIC 
    $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/..>
    $<INSTALL_INTERFACE:include>
)

target_link_libraries(TeaPot PUBLIC 
    BoilingHotWater::BoilingHotWater
    TeaCup::TeaCup
    Tea
)

add_custom_command(
        TARGET TeaPot POST_BUILD
        COMMAND ${CMAKE_COMMAND} -E copy_directory
        ${CMAKE_CURRENT_SOURCE_DIR}/../ProjectTemplateFiles ${CMAKE_CURRENT_SOURCE_DIR}/../bin/ProjectTemplateFiles
        COMMENT "Copying ProjectTemplateFiles to ${OUTPUT_DIR}/ProjectTemplateFiles"
)