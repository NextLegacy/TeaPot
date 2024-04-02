function(add_resource file_name variable_name)

    file(READ ${file_name} file_content)

    file(APPEND ${CMAKE_CURRENT_SOURCE_DIR}/Resources.cpp "        {\"${variable_name}\",\nR\"(${file_content})\"\n        },\n")

endfunction()

file(GLOB_RECURSE RESOURCES ${CMAKE_CURRENT_SOURCE_DIR}/../Resources/*)

file(WRITE ${CMAKE_CURRENT_SOURCE_DIR}/Resources.cpp "// Auto generated file.\n#include \"Resources.hpp\"\nnamespace TP{\n    const std::unordered_map<std::string, const std::string> Resources = {\n")

foreach(RESOURCE ${RESOURCES})
    get_filename_component(RESOURCE_NAME ${RESOURCE} NAME)
    add_resource(${RESOURCE} ${RESOURCE_NAME})
endforeach()

file(APPEND ${CMAKE_CURRENT_SOURCE_DIR}/Resources.cpp "    };\n}")