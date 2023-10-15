#pragma once

#include <type_traits>

namespace BHW
{
    class Application
    {
    public:
        Application();
        ~Application();

        void Run();

        inline bool IsRunning() { return m_isRunning; }

    protected:
        virtual void ApplicationEntryPoint() = 0;
    
    private:
        bool m_isRunning;
    };

    namespace
    {
        extern Application* s_instance;
    }

    template <typename TApplication>
    TApplication& GetApplication()
    {
        return static_cast<TApplication&>(Application::GetInstance());
    }

    template <typename TApplication>
    TApplication CreateApplication()
    {
        TApplication application();

        return GetApplication<TApplication>();
    }

    template <typename TApplication>
    TApplication& CreateAndRunApplication()
    {
        CreateApplication<TApplication>().Run();

        return GetApplication<TApplication>();
    }
}