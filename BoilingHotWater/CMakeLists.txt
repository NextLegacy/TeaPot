project(BoilingHotWater)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

add_library(BoilingHotWater
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/EntryPoint
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/EntryPoint.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/ecs/ECS.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/ecs/Component.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/ecs/ComponentBitMask.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/ecs/EventSystem.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/ecs/Entity.hpp
    
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/Time.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/Time.cpp 
    
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/data/Data.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/data/FixedData.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/Assert.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/Assert.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/threads/Thread.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/threads/Thread.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/serialization/JSON.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/serialization/JSON.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/random/Random.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/random/Random.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/random/PRNG.hpp
    
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/random/PRNGAlgorithms.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/random/PRNGAlgorithms.cpp
    
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/random/Seed.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/random/Seed.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/logger/Logger.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/logger/Logger.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/logger/LoggerLevel.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/logger/LoggerLevel.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/logger/LoggedMessage.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/logger/LoggedMessage.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/event/EventSystem.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/event/DynamicEventSystem.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/console/Console.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/console/Console.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/String.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/String.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/console/ANSI.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/console/ANSI.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/application/Application.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/application/Application.cpp
)

target_include_directories(BoilingHotWater PUBLIC 
    ${CMAKE_CURRENT_SOURCE_DIR}
)

target_link_libraries(BoilingHotWater PUBLIC 
  
)

set_target_properties(BoilingHotWater PROPERTIES 
    #SUFFIX ".dll"
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