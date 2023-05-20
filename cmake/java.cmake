find_package(Java REQUIRED)
find_package(JNI REQUIRED)

include(UseJava)

set(CMAKE_JAVA_COMPILE_FLAGS "-source" "17" "-target" "17")

set(JAVA_INCLUDE_DIRS
    ${JNI_INCLUDE_DIRS} 
    ${_classDir} 
    ${_stubDir}
)

set(JAVA_LIBRARIES
    ${JNI_LIBRARIES}
)