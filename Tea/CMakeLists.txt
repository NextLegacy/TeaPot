file(GLOB_RECURSE TeaSources *.cpp)
file(GLOB_RECURSE TeaHeaders *.hpp)

add_library(Tea STATIC ${TeaSources} ${TeaHeaders})

target_include_directories(Tea PUBLIC 
    $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/..>
    $<INSTALL_INTERFACE:include>
)

target_link_libraries(Tea PUBLIC
    BoilingHotWater::BoilingHotWater
    TeaCup::TeaCup
)

#add_library(Tea::Tea ALIAS TeaCup)

include(GNUInstallDirs)
include(CPack)

install(TARGETS Tea
    EXPORT Tea
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
    ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR}
)

install(
    DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}
    DESTINATION ${CMAKE_INSTALL_INCLUDEDIR}
    PATTERN "CMakeLists.txt" EXCLUDE
    PATTERN "*.cpp" EXCLUDE
)

install(
    EXPORT Tea
    NAMESPACE Tea::
    DESTINATION ${CMAKE_INSTALL_DATAROOTDIR}/Tea
    FILE TeaConfig.cmake
)

include(CMakePackageConfigHelpers)

write_basic_package_version_file(
    ${CMAKE_CURRENT_BINARY_DIR}/TeaConfigVersion.cmake
    VERSION ${PROJECT_VERSION}
    COMPATIBILITY AnyNewerVersion
)

install(
    FILES ${CMAKE_CURRENT_BINARY_DIR}/TeaConfigVersion.cmake
    DESTINATION ${CMAKE_INSTALL_DATAROOTDIR}/Tea
)