#include "TeaBrewer/TeaBrewer.hpp"

#include <BHW/utils/Debug.hpp>
#include <BHW/utils/io/FileHandler.hpp>
#include <BHW/utils/json/JSON.hpp>

#include "TeaBrewer/TeaBrewerUpdater.hpp"
#include "TeaBrewer/TeaBrewerBuilder.hpp"

namespace TB
{
    TeaBrewer::TeaBrewer(const std::string& path, BHW::Logger logger) : m_config(LoadOrCreateConfig(path)), m_logger(logger)
    {
        if (m_config.IsInvalid())
        {
            m_logger.Log("Could not load TeaBrewer configuration! " + m_config.ProjectDirectory + " is not a valid TeaBrewer project directory.");
        }
    }

    void TeaBrewer::Update()
    {
        TB::Update(m_config);
    }

    void TeaBrewer::Build(BuildTarget target)
    {
        TB::Build(m_config, target);
    }
}