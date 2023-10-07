#include "App.hpp"

GLFWwindow* m_window = nullptr;

ImGuiIO*      m_imguiIO      = nullptr;
ImGuiStyle*   m_imguiStyle   = nullptr;
ImGuiContext* m_imguiContext = nullptr;

uint64_t m_fps                     = 120; uint64_t m_tps                    = 60;
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

void InitializeImGuiContext()
{
    IMGUI_CHECKVERSION();

    ImGui::CreateContext();

    m_imguiIO = &ImGui::GetIO();

    m_imguiIO->ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard; // Enable Keyboard Controls
    m_imguiIO->ConfigFlags |= ImGuiConfigFlags_NavEnableGamepad ; // Enable Gamepad Controls
    m_imguiIO->ConfigFlags |= ImGuiConfigFlags_DockingEnable    ; // Enable Docking
    m_imguiIO->ConfigFlags |= ImGuiConfigFlags_ViewportsEnable  ; // Enable Multi-Viewport / Platform Windows

    ImGui::StyleColorsDark();

    m_imguiStyle = &ImGui::GetStyle();
    if (m_imguiIO->ConfigFlags & ImGuiConfigFlags_ViewportsEnable)
    {
        m_imguiStyle->WindowRounding              = 0.0f;
        m_imguiStyle->Colors[ImGuiCol_WindowBg].w = 1.0f;
    }

    // Setup Platform/Renderer backends
    ImGui_ImplGlfw_InitForOpenGL(m_window, true);
    ImGui_ImplOpenGL3_Init("#version 330");
}

void DestroyImGuiContext()
{
    ImGui_ImplOpenGL3_Shutdown();
    ImGui_ImplGlfw_Shutdown();
    ImGui::DestroyContext();
}

void DestroyWindow()
{
    glfwDestroyWindow(m_window);
    glfwTerminate();
}

void Tick()
{
    BHW::Console::WriteLine("TPS: " + std::to_string(m_measuredTicksPerSecond) + " FPS: " + std::to_string(m_measuredFramesPerSecond));
}

void Frame()
{
    glfwPollEvents(); 

    ImGui_ImplOpenGL3_NewFrame();
    ImGui_ImplGlfw_NewFrame();
    ImGui::NewFrame();

    ///

    static float timer = 0;
    static float fps   = 0;
    static float tps   = 0;

    timer += m_deltaFrameTime;

    if (timer >= 1)
    {
        fps = m_measuredFramesPerSecond;
        tps = m_measuredTicksPerSecond;

        timer = 0;
    }

    ImGui::ShowDemoWindow();

    ImGui::Begin("Data");

    ImGui::Text("TPS: %f", tps );
    ImGui::Text("FPS: %f", fps);

    ImGui::End();

    ///

    ImGui::Render();
    int display_w, display_h;
    glfwGetFramebufferSize(m_window, &display_w, &display_h);
    glViewport(0, 0, display_w, display_h);
    glClearColor(0.45f, 0.55f, 0.60f, 1.00f);
    glClear(GL_COLOR_BUFFER_BIT);
    ImGui_ImplOpenGL3_RenderDrawData(ImGui::GetDrawData());

    glfwSwapBuffers(m_window);

    if (m_imguiIO->ConfigFlags & ImGuiConfigFlags_ViewportsEnable)
    {
        ImGui::UpdatePlatformWindows();
        ImGui::RenderPlatformWindowsDefault();

        glfwMakeContextCurrent(m_window);
    }
}

void RenderThread(BHW::Thread* thread)
{
    glfwMakeContextCurrent(m_window);

    glfwSwapInterval(0);

    InitializeImGuiContext();

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

    //glfwMakeContextCurrent(nullptr);

    DestroyImGuiContext();
}

void UpdateThread(BHW::Thread* thread)
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
    BHW::Thread updateThread(UpdateThread);

    renderThread.Activate();
    updateThread.Activate();

    while (!glfwWindowShouldClose(m_window))
    {
        glfwWaitEvents();
    }

    updateThread.Join();
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