#pragma once

#include <BHW/utils/logger/Logger.hpp>

#include "TeaBrewer/TeaBrewerConfig.hpp"
#include "TeaBrewer/TeaBrewerBuildTarget.hpp"

namespace TB
{
    class TeaBrewer
    {
    public:
        TeaBrewer(const std::string& path = "", BHW::Logger logger = BHW::Logger());

    public:
        void Update();
        void Build (BuildTarget target);

    private:
        TeaBrewerConfig m_config;
        BHW::Logger     m_logger;
    };
}