#include "TeaPot/application/TeaPot.hpp"

namespace TP
{
    void TeaPot::ReloadProject()
    {
        SetTitle("TeaPot Engine - " + m_project.m_name);
    }
}