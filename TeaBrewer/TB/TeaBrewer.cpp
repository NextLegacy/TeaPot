#include "TB/TeaBrewer.hpp"

#include <BHW/utils/Debug.hpp>
#include <BHW/utils/io/FileHandler.hpp>
#include <BHW/utils/json/JSON.hpp>

#include "TB/TeaBrewerUpdater.hpp"
#include "TB/TeaBrewerBuilder.hpp"

namespace TB
{
    TeaBrewer::TeaBrewer(const std::string& path) : TeaBrewer(LoadOrCreateConfig(path))
    {

    }

    TeaBrewer::TeaBrewer(const TeaBrewerConfig& config) : m_config(config)
    {
        if (m_config.IsInvalid())
        {
            BHW::Console::WriteLine("Could not load TeaBrewer configuration! " + m_config.ProjectDirectory + " is not a valid TeaBrewer project directory.");
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