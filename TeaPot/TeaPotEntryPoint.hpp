#pragma once

#include <BHW/EntryPoint.hpp>

#include <source_location>
#include <filesystem>

std::string super_path()
{
    std::source_location loc = std::source_location::current();

    std::filesystem::path path = loc.file_name();

    return std::filesystem::relative(path, "C:/Users/NextLegacy/Documents/Projects/Engine/").string();
}