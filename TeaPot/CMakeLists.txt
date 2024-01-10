set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

function(embed_resource resource_file_name source_file_name variable_name)

    file(READ ${resource_file_name} hex_content HEX)

    # remove all newlines
    string(REPEAT "[0-9a-f]" 32 column_pattern)
    string(REGEX REPLACE "(${column_pattern})" "\\1\n" content "${hex_content}")
    
    string(REGEX REPLACE "([0-9a-f][0-9a-f])" "0x\\1, " content "${content}")
    
    string(REGEX REPLACE ", $" "" content "${content}")
    
    string(REGEX REPLACE "0x0d, 0x0a" "0x0a" content "${content}")
    set(array_definition "static const unsigned char ${variable_name}[] =\n{\n${content}\n};")
    
    set(source "// Auto generated file.\n${array_definition}\n")
    
    file(WRITE "${source_file_name}" "${source}")

endfunction()

embed_resource(${CMAKE_CURRENT_SOURCE_DIR}/../ProjectTemplateFiles/CMakeLists.txt ${CMAKE_CURRENT_SOURCE_DIR}/project/files/CMakeLists.txt.hpp CMakeLists_txt)
embed_resource(${CMAKE_CURRENT_SOURCE_DIR}/../ProjectTemplateFiles/EntryPoint_Final.cpp ${CMAKE_CURRENT_SOURCE_DIR}/project/files/EntryPoint_Final.cpp.hpp EntryPoint_Final_cpp)
embed_resource(${CMAKE_CURRENT_SOURCE_DIR}/../ProjectTemplateFiles/EntryPoint_NativeScriptCore.cpp ${CMAKE_CURRENT_SOURCE_DIR}/project/files/EntryPoint_NativeScriptCore.cpp.hpp EntryPoint_NativeScriptCore_cpp)
embed_resource(${CMAKE_CURRENT_SOURCE_DIR}/../ProjectTemplateFiles/EventSubscriber.hpp ${CMAKE_CURRENT_SOURCE_DIR}/project/files/EventSubscriber.hpp.hpp EventSubscriber_hpp)
embed_resource(${CMAKE_CURRENT_SOURCE_DIR}/../ProjectTemplateFiles/Tea.cpp ${CMAKE_CURRENT_SOURCE_DIR}/project/files/Tea.cpp.hpp Tea_cpp)
embed_resource(${CMAKE_CURRENT_SOURCE_DIR}/../ProjectTemplateFiles/Tea.hpp ${CMAKE_CURRENT_SOURCE_DIR}/project/files/Tea.hpp.hpp Tea_hpp)

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