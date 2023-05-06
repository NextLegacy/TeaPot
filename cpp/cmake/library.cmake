add_library(EngineLibrary ${FILES})

target_include_directories(EngineLibrary PUBLIC ${INCLUDE_DIRS})
target_compile_definitions(EngineLibrary PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})

target_link_libraries(EngineLibrary PUBLIC ${LIBRARIES})

set_target_properties(EngineLibrary PROPERTIES 
    SUFFIX ".dll"
    #ARCHIVE_OUTPUT_DIRECTORY "../../lib"
    #LIBRARY_OUTPUT_DIRECTORY "../../lib"
    RUNTIME_OUTPUT_DIRECTORY "../../bin"
)