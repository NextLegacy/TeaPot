cmake_minimum_required(VERSION 3.26.3)

project(DigitalTea)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

include(cmake/project.cmake)     # set up project variables

add_subdirectory(BoilingHotWater)
add_subdirectory(TeaCup)
add_subdirectory(TeaPot)

#add_subdirectory(TeaBag)
#add_subdirectory(TeaPot)
#add_subdirectory(TeaMath)

#add_subdirectory(core)           # Core library        -> engine_core.lib

#add_subdirectory(editor)         # Editor Executable    -> engine_editor.exe

#add_subdirectory(launcher)       # Launcher Executable  -> engine_launcher.exe
#add_subdirectory(runtime)        # Runtime Executable  -> engine_wrapper.exe

#add_subdirectory(scripting/java) # Java api          -> engine_java_api.dll 
                                  # Java core         -> engine_java_core.jar 

#add_subdirectory(demo)           # Java Demo Project -> engine_demo.jar