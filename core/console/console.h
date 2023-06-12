#pragma once

#include "core.h"

namespace engine
{
    namespace console
    {
        class Console
        {
        public:
            Console();
            ~Console();

            void create();
            void destroy();

            void print(std::string message);

            void setVisibility(bool visible);
            bool isVisible() const;

            void show();
            void hide();
        };
    }
}