# TeaPot 3D Game Engine 

## Abstract
This is a general purpose 3D Game Engine. It is build on top of the [BoilingHotWater](https://github.com/nextlegacy/BoilingHotWater) library, which is a collection of utilities and tools that I have created for my projects and the
[TeaCup](https://github.com/nextlegacy/TeaCup) graphics library, which is a wrapper around various modern graphics APIs. 

My goal is to make a descent Engine that opens up the possibility for me to get my hand on various concepts and algorithms in the field of game development, graphics programming, and computer science in general.
And in the process, I try to use best practices and modern design patterns of game engine architecture.

It's worth mentioning that it is currently under development; consequently, **eveything is subject to change and may not work as intendent**, furthermore, **the only supported platform currently is Windows**.

This is pretty much just here so that I can get my hand on various concepts and algorithms and implement them by myself to get a good look and understanding of how they work; I thought: before I throw in a bunch of libraris on my projects, I may as well, for the sake of learning a thing or two, reinvent the wheel in the process.

Feel free to grab code snippets or the whole Engine if you find it useful, but keep in mind that it is still under development and may not be suitable for production use.

## Table of Contents
- [TeaPot 3D Game Engine](#teapot-3d-game-engine)
  - [Abstract](#abstract)
  - [Table of Contents](#table-of-contents)
  - [Currently Supported Platforms](#currently-supported-platforms)
  - [Features](#features)
  - [Examples](#examples)
  - [Build and Install](#build-and-install)
    - [CMake](#cmake)
  - [License](#license)

## Currently Supported Platforms
- [X] Windows
- [ ] Linux
- [ ] MacOS

## Features

A quick overview of partially implemented and planed features:

- [X] Build System
- [X] Project Handling
- [ ] Scene System
- [ ] Content Browser
- [ ] Entity Component System
- [ ] Physics Engine
- [ ] Audio Engine
- [ ] Scripting Engine
- [X] Editor
- [ ] Renderer
- [X] Input System
- [ ] Networking
- [ ] Profiling
- [ ] Debugging
- [X] Serialization
- [X] Inspector
- [ ] Hot Reloading

## Examples
  
There are currently no examples available, but I will add some in the future when the Engine is in a more stable state.

## Build and Install

### CMake

This TeaPot Engine uses [CMake](https://cmake.org/) as its build system.

To build the Engine, you can run the following commands:

```bash
# Explicitly specify a build directory.
cmake -B build

# Build the Engine using the following command. You will find the build Engine in the /bin directory.
cmake --build build --target ALL_BUILD --config Release
```

make sure to set the BoilingHotWater_DIR and TeaCup_DIR to the install directory if you have specified a custom install directory. You may do this trough the folwing ways:

```bash
# Set the BoilingHotWater_DIR environment variable to the install directory
cmake -D BoilingHotWater_DIR=PATH_TO_INSTALL_DIR

# Set the TeaCup_DIR environment variable to the install directory
cmake -D TeaCup_DIR=PATH_TO_INSTALL_DIR
```

or
  
```cmake
set(BoilingHotWater_DIR PATH_TO_INSTALL_DIR)
set(TeaCup_DIR PATH_TO_INSTALL_DIR)
```

or

```cmake
list(APPEND CMAKE_PREFIX_PATH path/to/BoilingHotWater/install)
list(APPEND CMAKE_PREFIX_PATH path/to/TeaCup/install)
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.