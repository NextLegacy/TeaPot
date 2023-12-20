#pragma once

#include <BHW/utils/reflection/Reflection.hpp>

#include "TP/application/EventSubscriber.hpp"

#include <BHW/utils/reflection/Reflection.hpp>

struct TestInspector
{
    int         m_int    = 0;
    float       m_float  = 0.0f;
    double      m_double = 0.0;
    bool        m_bool   = false;
    std::string m_string = "Hello World!";
};

BHW_REFLECT(TestInspector);
BHW_REFLECT_MEMBERS(TestInspector,
    BHW_REFLECT_MEMBER("int"   , &TestInspector::m_int   ),
    BHW_REFLECT_MEMBER("float" , &TestInspector::m_float ),
    BHW_REFLECT_MEMBER("double", &TestInspector::m_double),
    BHW_REFLECT_MEMBER("bool"  , &TestInspector::m_bool  ),
    BHW_REFLECT_MEMBER("string", &TestInspector::m_string)
)

namespace TP
{
    namespace View
    {
        struct Inspector
        {   
            std::string   m_name;
            bool          m_open;
            TestInspector m_test;

            //void (*m_render)(void* data);

            Inspector(std::string name, bool open = true);
        };

        class InspectorRenderer : public EventSubscriber
        {
        public:
            void Render(TeaPot& teaPot);

        private:
            void RenderInspector(TeaPot& teaPot, Inspector& data);
        };
    }
}