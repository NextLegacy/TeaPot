project(EngineJavaDemo)

add_jar(engine_java_demo ${DEMO_JAVA_FILES}
    VERSION ${PROJECT_VERSION}
    OUTPUT_NAME engine_java_demo
    OUTPUT_DIR ${OUTPUT_DIR}
    INCLUDE_JARS engine_java_core
    #ENTRY_POINT com.engine.Engine
)

message(STATUS ${OUTPUT_DIR}/engine_java_core.jar)

get_target_property(_jarFile engine_java_demo JAR_FILE)
get_target_property(_classDir engine_java_demo CLASSDIR)

install_jar(engine_java_demo ${OUTPUT_DIR})