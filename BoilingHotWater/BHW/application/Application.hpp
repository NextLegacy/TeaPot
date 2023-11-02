#pragma once

namespace BHW
{
    class Application
    {
    public:
        Application();

        void Run();
        void Stop();

        inline bool IsRunning() { return m_isRunning; }

    protected:
        virtual void ApplicationEntryPoint() = 0;
    
    private:
        bool m_isRunning;
    };
}