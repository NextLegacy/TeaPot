#include "launcher.hpp"

int engine_main()
{
    Engine::Window window("Test", glm::ivec2(16, 9) * 70);


    window.Shutdown();

    return 0;
}