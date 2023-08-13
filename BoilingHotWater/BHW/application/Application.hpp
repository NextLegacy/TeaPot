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

        inline static Application* GetInstance() { return s_instance; }

    protected:
        virtual void ApplicationEntryPoint() = 0;
    
    private:
        bool m_isRunning;
    
        static Application* s_instance;
    };
}