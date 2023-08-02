#pragma once

namespace BHW
{
    struct MouseWheel : public MouseButton
    {
    public:
        MouseWheel();

        inline int Delta() const { return m_delta; }

    private:
        void SetDelta(int delta);

        friend class Input;
    private:
        int m_delta;
    };
}