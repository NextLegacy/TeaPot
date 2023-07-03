#pragma once 

namespace Engine
{
    class Activateable
    {
    private:
        bool m_isActive = true;

    public:
        bool IsActive() const { return m_isActive; }

        void SetActive(bool isActive);

        virtual void Activate  ();
        virtual void Deactivate();

        virtual void OnActivate  ();
        virtual void OnDeactivate();
    };
}