#pragma once

#include "TB/TeaBrewerConfig.hpp"
#include "TB/TeaBrewerBuildTarget.hpp"

namespace TB
{
    class TeaBrewer
    {
    public:
        TeaBrewer(const std::string&     path  );
        TeaBrewer(const TeaBrewerConfig& config);

    public:
        void Update();
        void Build (BuildTarget target);

    private:
        TeaBrewerConfig m_config;
    };
}