#pragma once

#include <string_view>

namespace BHW
{
    template <typename T>
    inline constexpr std::string_view WrappedTypeName()
    {
#ifdef __clang__
        return __PRETTY_FUNCTION__;
#elif defined(__GNUC__)
        return __PRETTY_FUNCTION__;
#elif defined(_MSC_VER)
        return __FUNCSIG__;
#else
#error "Unsupported compiler"
#endif
    }
    
    template <typename T>
    constexpr std::string_view TypeName();

    template <> 
    inline constexpr std::string_view TypeName<void>() { return "void"; }

    inline constexpr std::size_t WrappedTypeNamePrefixLength()
    {
        return WrappedTypeName<void>().find(TypeName<void>());
    }
    
    inline constexpr size_t WrappedTypeNameSuffixLength()
    {
        return WrappedTypeName<void>().length() - WrappedTypeNamePrefixLength() - TypeName<void>().length();
    }

	template <typename T>
	inline constexpr std::string_view TypeName()
	{
		constexpr auto wrapped_name     = WrappedTypeName<T>();
		constexpr auto prefix_length    = WrappedTypeNamePrefixLength();
		constexpr auto suffix_length    = WrappedTypeNameSuffixLength();
		constexpr auto type_name_length = wrapped_name.length() - prefix_length - suffix_length;

		return wrapped_name.substr(prefix_length, type_name_length);
	}

    // FNV-1a
    inline constexpr uint64_t Hash(const std::string_view& name)
    {
        uint64_t hash = 0xcbf29ce484222325ULL;
        for (const auto c : name)
        {
            hash ^= c;
            hash *= 0x100000001b3ULL;
        }
        return hash;
    }

    template <typename T>
    inline constexpr uint64_t Hash()
    {
        return Hash(TypeName<T>());
    }
}