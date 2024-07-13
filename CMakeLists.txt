cmake_minimum_required(VERSION 3.28.1)

project(TeaPot VERSION 0.0.1 LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

set(CMAKE_INSTALL_PREFIX ${CMAKE_CURRENT_SOURCE_DIR}/bin/install)

find_package(BoilingHotWater REQUIRED)
find_package(TeaCup REQUIRED)
find_package(Threads REQUIRED)
find_package(OpenGL REQUIRED)

add_subdirectory(Tea   )
add_subdirectory(TeaPot)

include(GNUInstallDirs)
include(CPack)

install(TARGETS Tea TeaPot
    EXPORT TeaPot
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
    ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR}
    RUNTIME DESTINATION ${CMAKE_INSTALL_BINDIR}/../..
)

install(
    DIRECTORY TeaPot
    DESTINATION ${CMAKE_INSTALL_INCLUDEDIR}
    PATTERN "CMakeLists.txt" EXCLUDE
    PATTERN "*.cpp" EXCLUDE
)

install(
    EXPORT TeaPot
    NAMESPACE TeaPot::
    DESTINATION ${CMAKE_INSTALL_DATAROOTDIR}/TeaPot
    FILE TeaPotConfig.cmake
)

include(CMakePackageConfigHelpers)

write_basic_package_version_file(
    ${CMAKE_CURRENT_BINARY_DIR}/TeaPotConfigVersion.cmake
    VERSION ${PROJECT_VERSION}
    COMPATIBILITY AnyNewerVersion
)

install(
    FILES ${CMAKE_CURRENT_BINARY_DIR}/TeaPotConfigVersion.cmake
    DESTINATION ${CMAKE_INSTALL_DATAROOTDIR}/TeaPot
)