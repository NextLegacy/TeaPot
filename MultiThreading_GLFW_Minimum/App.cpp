#include "App.hpp"

GLFWwindow* m_window = nullptr;

uint64_t m_fps                     = 60000; uint64_t m_tps                    = 60;
float    m_deltaFrameTime          = 0 ; float    m_deltaTickTime          = 0 ;
float    m_measuredFramesPerSecond = 0 ; float    m_measuredTicksPerSecond = 0 ;

int InitWindow()
{
    glfwSetErrorCallback([](int error, const char* description)
    {
        BHW::Console::WriteLine("GLFW Error: " + std::to_string(error) + " - " + description);
    });

    if (!glfwInit())
        return 1;

    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

    m_window = glfwCreateWindow(1080, 720, "TeaCup Engine", nullptr, nullptr);

    if (m_window == nullptr)
        return 1;

    return 0;
}

void DestroyWindow()
{
    glfwDestroyWindow(m_window);
    glfwTerminate();
}

void Tick()
{
    BHW::Console::WriteLine("TPS: " + std::to_string(m_measuredTicksPerSecond) + " FPS: " + std::to_string(m_measuredFramesPerSecond));

    glfwPollEvents();
}

void Frame()
{
    glfwSwapBuffers(m_window);
}

void RenderThread(BHW::Thread* thread)
{
    glfwMakeContextCurrent(m_window);

    glfwSwapInterval(0);

    // fixing fps
    uint64_t deltaTime = 0;
    uint64_t deltaF    = 0;
    uint64_t last      = BHW::Time::NanoSeconds();

    // measuring frame time
    uint64_t lastFrameTime = BHW::Time::NanoSeconds();

    while (!glfwWindowShouldClose(m_window))
    {
        if (deltaF >= 1e9)
        {
            m_deltaFrameTime = (float) (BHW::Time::NanoSeconds() - lastFrameTime) / 1.0e9f;
            lastFrameTime = BHW::Time::NanoSeconds();

            m_measuredFramesPerSecond = 1 / m_deltaFrameTime;

            Frame(); 

            deltaF -= 1e9;
        }

        deltaTime = BHW::Time::NanoSeconds() - last;
        last     += deltaTime;
        deltaF   += deltaTime * m_fps;
    }

    glfwMakeContextCurrent(nullptr);
}

void UpdateThread()
{
    // fixing tps
    uint64_t deltaTime = 0;
    uint64_t deltaT    = 0;
    uint64_t last      = BHW::Time::NanoSeconds();

    // measuring tick time
    uint64_t lastTickTime = BHW::Time::NanoSeconds();

    while (!glfwWindowShouldClose(m_window))
    {
        if (deltaT >= 1e9)
        {
            m_deltaTickTime = (float) (BHW::Time::NanoSeconds() - lastTickTime) / 1.0e9f;
            lastTickTime    = BHW::Time::NanoSeconds();

            m_measuredTicksPerSecond = 1 / m_deltaTickTime;

            Tick();

            deltaT -= 1e9;
        }

        deltaTime  = BHW::Time::NanoSeconds() - last;
        last      += deltaTime;
        deltaT    += deltaTime * m_tps;
    }
}

void StartApplicationLoop()
{
    BHW::Thread renderThread(RenderThread);

    renderThread.Activate();

    UpdateThread();

    renderThread.Join();
}

int BHW::EntryPoint(std::vector<std::string> args)
{
    BHW::Console::InitConsole();
    BHW::Console::WriteLine("Hello, World!");

    BHW::ASSERT(InitWindow() == 0, "Failed to create window!");

    StartApplicationLoop();

    DestroyWindow();

    BHW::Console::Pause();

    return 0;
}