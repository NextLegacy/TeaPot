file(GLOB_RECURSE TeaPotSources *.cpp)
file(GLOB_RECURSE TeaPotHeaders *.hpp)

add_executable(TeaPot WIN32 ${TeaPotSources} ${TeaPotHeaders})

target_include_directories(TeaBrewer PUBLIC 
    $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/..>
    $<INSTALL_INTERFACE:include>
)

target_link_libraries(TeaPot PUBLIC 
    BoilingHotWater::BoilingHotWater
    TeaCup::TeaCup
    Tea
)