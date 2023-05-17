add_executable(Engine WIN32 ${FILES})
target_link_options(Engine PUBLIC -mwindows)

target_include_directories(Engine PUBLIC ${INCLUDE_DIRS})
target_link_libraries(Engine PUBLIC ${LIBRARIES})

set_target_properties(Engine PROPERTIES 
    #ARCHIVE_OUTPUT_DIRECTORY "./lib"
    #LIBRARY_OUTPUT_DIRECTORY "./lib"
    RUNTIME_OUTPUT_DIRECTORY "./bin"
)