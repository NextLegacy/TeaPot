#pragma once

#include "graphics/window/Window.hpp"
#include "utils/Loop.hpp"
#include "utils/Console.hpp"

#include "ApplicationThread.hpp"

namespace BHW
{
    class Application : public ApplicationThread
    {
    private:
        Window* m_window;

        bool m_isActive;

        static Application* s_instance;
    public:
        Application();
        virtual ~Application();

        inline Window* GetWindow() const { return m_window; }

        inline static Application* GetInstance() { return s_instance; }

    protected:
        virtual void Tick () override;
        virtual void Frame() override;
    
        virtual void Update();
        virtual void Render();

        bool IsActive() override;
    private:
        void Initialize() override;
        void Release   () override;
    };
}