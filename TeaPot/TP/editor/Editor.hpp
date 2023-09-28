#pragma once

#include <TC/TeaCup.hpp>
#include <TC/gapi/impl/GLFW_OpenGL_DearImGui.hpp>

//#include "TP/editor/TeaPotECS.hpp"

namespace TP
{/*
    struct TestComponent_1
    {
        uint32_t m_test;
    };

    struct TestComponent_2
    {
        uint32_t m_test;
    };
    
    struct TestComponent_3
    {
        uint32_t m_test;
    };

    struct TeaPotData : public 
        BHW::DataStorages<
            BHW::FixedDataStorages                 // Renderable Entitys
            < 
                100,
                BHW::DataStorage<TestComponent_1>,
                BHW::DataStorage<TestComponent_2>,
                BHW::DataStorage<TestComponent_3>
            >,
            BHW::FixedDataStorages                 // Physics Entitys
            <
                100,
                BHW::DataStorage<TestComponent_1>,
                BHW::DataStorage<TestComponent_3>
            >
        >
    { };*/

    struct Editor;

    struct TeaPotBaseEvent : public TC::TeaCupBaseEvent
    {
        inline TeaPotBaseEvent(Editor* editor) : TC::TeaCupBaseEvent(), m_editor(editor) { }

    protected:
        Editor* m_editor;
    };

    struct Editor : public TC::TeaCup<TC::GLFW_OpenGL_DearImGui, TeaPotBaseEvent>/*, public BHW::ECS<TeaPotBaseEvent, TeaPotData>*/ { };
}