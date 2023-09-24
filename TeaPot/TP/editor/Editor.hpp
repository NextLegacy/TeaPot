#pragma once

#include <TC/TeaCup.hpp>
#include <TC/gapi/impl/GLFW_OpenGL_DearImGui.hpp>

#include "TP/editor/TeaPotECS.hpp"

namespace TP
{
    struct TeaPotData : public 
        BHW::FixedDataStorages
        <
            100,
            BHW::DataStorage<uint32_t>
        >
    { };

    struct Editor;

    template<typename T>
    struct TeaPotBaseEventSystem : public TC::TeaCupBaseEvent<T>, public BHW::ECSBaseEventSystem<Editor> 
    {
        inline TeaPotBaseEventSystem(Editor* editor) : BHW::ECSBaseEventSystem<Editor>(editor), TC::TeaCupBaseEvent() { }
    };

    struct TeaPotECS : public BHW::ECS<TeaPotBaseEventSystem, TeaPotData> { };

    struct Editor : public TC::TeaCup<TC::GLFW_OpenGL_DearImGui, TeaPotBaseEventSystem>, public TeaPotECS { };
}