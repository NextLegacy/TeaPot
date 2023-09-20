#pragma once

#include <glm/glm.hpp>

#include "TC/input/MouseButton.hpp"

namespace TC
{
    struct MouseWheel : public MouseButton
    {
    public:
        MouseWheel();

        inline glm::vec2 Delta() const { return m_delta; }

    private:
        void SetDelta(double x, double y);

        friend class Input;

    private:
        glm::vec2 m_delta;
    };
}