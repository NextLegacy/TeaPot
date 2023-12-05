#pragma once

#include "TB/TeaBrewerConfig.hpp"
#include "TB/TeaBrewerBuildTarget.hpp"

namespace TB
{
    void CMakeBuild(const TeaBrewerConfig& config, BuildTarget target);
    void Build     (const TeaBrewerConfig& config, BuildTarget target);
}