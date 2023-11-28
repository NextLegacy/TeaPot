#pragma once

#include "TP/application/EventSubscriber.hpp"

#include <BHW/utils/reflection/Reflection.hpp>

struct SomeTest
{
    uint64_t abc;
    std::string hello_world;
    std::vector<std::string> test;
    std::vector<std::vector<std::string>> test2;

    SomeTest(uint64_t abc, std::string hello_world, std::vector<std::string> test, std::vector<std::vector<std::string>> test2) 
        : abc(abc), hello_world(hello_world), test(test), test2(test2) { }

    SomeTest() : SomeTest(0, "", { }, { }) { }
};

/*
BHW_REFLECT(SomeTest)

BHW_REFLECT_MEMBERS(SomeTest,
    BHW_REFLECT_MEMBER("abc"        , &SomeTest::abc        ),
    BHW_REFLECT_MEMBER("hello_world", &SomeTest::hello_world),
    BHW_REFLECT_MEMBER("test"       , &SomeTest::test       ),
    BHW_REFLECT_MEMBER("test2"      , &SomeTest::test2      )
)*/

namespace TP
{
    class DebugSystem : public EventSubscriber
    {
        void Start (TeaPot& teaPot) override;
        void Update(TeaPot& teaPot) override;
        void Render(TeaPot& teaPot) override;
    };
}