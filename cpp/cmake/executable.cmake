add_executable(EngineExecutable WIN32 ${FILES})
target_link_options(EngineExecutable PUBLIC -mwindows)

target_include_directories(EngineExecutable PUBLIC ${INCLUDE_DIRS})
target_compile_definitions(EngineExecutable PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})

target_link_libraries(EngineExecutable PUBLIC ${LIBRARIES})

set_target_properties(EngineExecutable PROPERTIES 
    #ARCHIVE_OUTPUT_DIRECTORY "../../lib"
    #LIBRARY_OUTPUT_DIRECTORY "../../lib"
    RUNTIME_OUTPUT_DIRECTORY "../../bin"
)