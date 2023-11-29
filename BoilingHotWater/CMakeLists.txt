project(BoilingHotWater)

set(BoilingHotWaterHeaders
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/EntryPoint

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/DLLEntryPoint

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/ecs/ECS.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/ecs/Component.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/ecs/ComponentBitMask.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/ecs/EventSystem.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/ecs/Entity.hpp
    
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/Time.hpp
    
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/data/Data.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/data/FixedData.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/Debug.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/threads/Thread.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/json/JSON.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/random/Random.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/random/PRNG.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/random/PRNGAlgorithms.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/random/Seed.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/random/UUID.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/logger/Logger.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/logger/LoggerLevel.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/logger/LoggedMessage.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/event/EventSystem.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/event/DynamicEventSystem.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/console/Console.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/String.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/console/ANSI.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/application/Application.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/io/FileHandler.hpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/reflection/Reflection.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/reflection/ReflectionDefinitions.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/reflection/Type.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/reflection/Value.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/reflection/TypeInfo.hpp
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/reflection/Member.hpp
)

set(BoilingHotWaterSources
    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/EntryPoint.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/DLLEntryPoint.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/Time.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/threads/Thread.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/json/JSON.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/random/Random.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/random/PRNGAlgorithms.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/random/Seed.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/random/UUID.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/logger/Logger.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/logger/LoggerLevel.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/logger/LoggedMessage.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/console/Console.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/String.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/console/ANSI.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/application/Application.cpp

    ${CMAKE_CURRENT_SOURCE_DIR}/BHW/utils/io/FileHandler.cpp
)

add_library(BoilingHotWater STATIC)

target_include_directories(BoilingHotWater PRIVATE ${PROJECT_SOURCE_DIR})

target_sources(BoilingHotWater 
    PRIVATE ${BoilingHotWaterSources}
    PUBLIC FILE_SET HEADERS
    BASE_DIRS ${PROJECT_SOURCE_DIR}
    FILES ${BoilingHotWaterHeaders}
)

target_link_libraries(BoilingHotWater PUBLIC)

install(TARGETS BoilingHotWater FILE_SET HEADERS)

