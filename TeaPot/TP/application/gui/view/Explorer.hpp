#pragma once

#include <string>
#include <vector>
#include <map>

#include "TP/application/EventSubscriber.hpp"

namespace TP
{
    namespace View
    {
        struct Explorer
        {
            std::string m_name;
            bool        m_open;

            std::string m_path;

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

            void RenderFiles(TeaPot& teaPot, Explorer& data);

            std::map<std::string, std::vector<File>> m_files;
        };
    }
}