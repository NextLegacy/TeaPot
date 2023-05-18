project(EngineWrapper)

add_executable(engine_wrapper WIN32 
    #${CORE_FILES} 
    ${WRAPPER_FILES}
)

target_include_directories(engine_wrapper PUBLIC 
    ${INCLUDE_DIRS}
    #${CORE_DIR}
    ${WRAPPER_DIR}
)

target_link_libraries(engine_wrapper PUBLIC ${LIBRARIES} engine_core)

set_target_properties(engine_wrapper PROPERTIES 
    #ARCHIVE_OUTPUT_DIRECTORY "./lib"
    #LIBRARY_OUTPUT_DIRECTORY "./lib"
    RUNTIME_OUTPUT_DIRECTORY_DEBUG ${OUTPUT_DIR}
    RUNTIME_OUTPUT_DIRECTORY ${OUTPUT_DIR}
)

install(TARGETS engine_wrapper RUNTIME DESTINATION bin)