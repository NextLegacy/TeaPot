#pragma once

namespace BHW
{
    template <typename TData>
    struct DataStorage
    {
    public:
        TData m_data;
    };

    template <typename ...TDataStorages>
    struct DataStorages : public DataStorage<std::tuple<TDataStorages...>> 
    { 
        template <typename TDataStorage>
        inline TDataStorage& Get() 
        {
            return std::get<TDataStorage>(DataStorage::m_data);
        }
    };
}