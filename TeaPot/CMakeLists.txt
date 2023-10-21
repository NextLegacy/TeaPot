project(TeaPot)

add_executable(TeaPot WIN32
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/TeaPotEntryPoint.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/TeaPotEntryPoint.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/TeaPot.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/TeaPot.hpp
    
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/EventSubscriber.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/EventSubscriber.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/ecs/components/DebugComponent.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/ecs/components/DebugComponent.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/ecs/systems/DebugSystem.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/ecs/systems/DebugSystem.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/project/TeaPotProject.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/project/TeaPotProject.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/windows/ConsoleWindow.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/windows/ConsoleWindow.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/windows/Dockspace.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/windows/Dockspace.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/windows/MenuBar.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/windows/MenuBar.cpp
)

target_include_directories(TeaPot PUBLIC
    ${CMAKE_CURRENT_SOURCE_DIR}
)

target_link_libraries(TeaPot PUBLIC 
    BoilingHotWater
    TeaCup
)

set_target_properties(TeaPot PROPERTIES 
    ARCHIVE_OUTPUT_DIRECTORY ${OUTPUT_DIR}
    LIBRARY_OUTPUT_DIRECTORY ${OUTPUT_DIR}
    RUNTIME_OUTPUT_DIRECTORY ${OUTPUT_DIR}
    ARCHIVE_OUTPUT_DIRECTORY_DEBUG ${OUTPUT_DIR}
    LIBRARY_OUTPUT_DIRECTORY_DEBUG ${OUTPUT_DIR}
    RUNTIME_OUTPUT_DIRECTORY_DEBUG ${OUTPUT_DIR}
    ARCHIVE_OUTPUT_DIRECTORY_RELEASE ${OUTPUT_DIR}
    LIBRARY_OUTPUT_DIRECTORY_RELEASE ${OUTPUT_DIR}
    RUNTIME_OUTPUT_DIRECTORY_RELEASE ${OUTPUT_DIR}
)

install(TARGETS TeaPot RUNTIME DESTINATION ${OUTPUT_DIR})