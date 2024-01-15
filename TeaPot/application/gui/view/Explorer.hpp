#pragma once

#include <string>
#include <vector>
#include <map>

#include "TeaPot/application/EventSubscriber.hpp"
#include <filesystem>

namespace TP
{
    namespace View
    {
        struct Explorer
        {
            std::string m_name;
            bool        m_open;

            std::filesystem::path m_currentDirectory;
            std::filesystem::path m_baseDirectory;

            Explorer(std::string name, std::string path = "", bool open = true);
        };

        struct File 
        {
            std::string m_fileName;
        };

        class ExplorerRenderer : public EventSubscriber
        {
        public:
            void Render(TeaPot& teaPot);

        private:
            void RenderExplorer(TeaPot& teaPot, Explorer& data);

            void RenderPath(Explorer& data);

            std::map<std::string, std::vector<File>> m_files;
        };
    }
}