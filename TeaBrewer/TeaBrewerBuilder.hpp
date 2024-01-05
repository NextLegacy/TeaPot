#pragma once

#include "TeaBrewer/TeaBrewerConfig.hpp"
#include "TeaBrewer/TeaBrewerBuildTarget.hpp"

namespace TB
{
    void CMakeBuild(const TeaBrewerConfig& config, BuildTarget target);
    void Build     (const TeaBrewerConfig& config, BuildTarget target);
}