file(GLOB_RECURSE TeaBrewerSources *.cpp)
file(GLOB_RECURSE TeaBrewerHeaders *.hpp)

add_library(TeaBrewer STATIC ${TeaBrewerSources} ${TeaBrewerHeaders})

target_include_directories(TeaBrewer PUBLIC 
    $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/..>
    $<INSTALL_INTERFACE:include>
)

target_link_libraries(TeaBrewer PUBLIC
    BoilingHotWater::BoilingHotWater
    Tea::Tea
)

add_library(TeaBrewer::TeaBrewer ALIAS TeaCup)

include(GNUInstallDirs)
include(CPack)

install(TARGETS TeaBrewer
    EXPORT TeaBrewer
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
    EXPORT TeaBrewer
    NAMESPACE TeaBrewer::
    DESTINATION ${CMAKE_INSTALL_DATAROOTDIR}/TeaBrewer
    FILE TeaBrewerConfig.cmake
)

include(CMakePackageConfigHelpers)

write_basic_package_version_file(
    ${CMAKE_CURRENT_BINARY_DIR}/TeaBrewerConfigVersion.cmake
    VERSION ${PROJECT_VERSION}
    COMPATIBILITY AnyNewerVersion
)

install(
    FILES ${CMAKE_CURRENT_BINARY_DIR}/TeaBrewerConfigVersion.cmake
    DESTINATION ${CMAKE_INSTALL_DATAROOTDIR}/TeaBrewer
)

message(STATUS "Copying ProjectTemplateFiles to ${CMAKE_INSTALL_PREFIX}/ProjectTemplateFiles")

add_custom_command(
        TARGET TeaBrewer POST_BUILD
        COMMAND ${CMAKE_COMMAND} -E copy_directory
        ${CMAKE_CURRENT_SOURCE_DIR}/../ProjectTemplateFiles ${CMAKE_CURRENT_BINARY_DIR}/ProjectTemplateFiles
        COMMENT "Copying ProjectTemplateFiles to ${OUTPUT_DIR}/ProjectTemplateFiles"
)