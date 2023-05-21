project(EngineJavaCore)

add_jar(engine_java_core ${JAVA_CORE_FILES}
    VERSION ${PROJECT_VERSION}
    OUTPUT_NAME engine_java_core
    OUTPUT_DIR ${OUTPUT_DIR}
    #INCLUDE_JARS ${CMAKE_BINARY_DIR}/bin/engine_java_core.jar
    #ENTRY_POINT com.engine.Engine
)

get_target_property(_jarFile engine_java_core JAR_FILE)
get_target_property(_classDir engine_java_core CLASSDIR)

install_jar(engine_java_core ${OUTPUT_DIR})