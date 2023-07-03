#include "Activateable.hpp"

namespace Engine
{
    void Activateable::SetActive(bool isActive)
    {
        if (m_isActive == isActive) return;

        if   (isActive) OnActivate();
        else            OnDeactivate();
    }

    void Activateable::Activate  () { SetActive(true ); }
    void Activateable::Deactivate() { SetActive(false); }

    void Activateable::OnActivate  () { }
    void Activateable::OnDeactivate() { }
}