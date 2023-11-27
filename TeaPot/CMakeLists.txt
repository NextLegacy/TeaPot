project(TeaPot)

add_executable(TeaPot WIN32
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/TeaPot.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/TeaPotEntryPoint.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/TeaPotEntryPoint.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/ecs/components/DebugComponent.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/ecs/components/DebugComponent.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/ecs/systems/DebugSystem.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/ecs/systems/DebugSystem.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/project/TeaPotProject.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/project/TeaPotProject.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/Dockspace.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/Dockspace.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/MenuBar.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/MenuBar.cpp
    
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/Build.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/Build.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/Performance.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/Performance.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/Console.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/Console.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/Explorer.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/Explorer.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/Inspector.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/Inspector.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/ProjectLoader.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/ProjectLoader.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/ProjectSettings.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/ProjectSettings.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/SceneEditor.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/SceneEditor.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/SceneView.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/SceneView.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/Settings.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/TP/application/gui/view/Settings.cpp
)

target_include_directories(TeaPot PUBLIC
    ${CMAKE_CURRENT_SOURCE_DIR}
)

target_link_libraries(TeaPot PUBLIC 
    BoilingHotWater
    TeaCup
    Tea
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