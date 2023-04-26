# Java 3D Engine

## Description

A simple 3D engine written in plain Java for learning purposes. 

Currently, rendering is done by setting individual pixels one by one. On a large scale, this can be very slow. Therefore an OpenGL renderer is currently being implemented with Jogl. 

## Example

```java
class App
{
    public static void main(String[] args)
    {
        // get the primary screen
        Screen screen = Screen.get(0);

        // define a vector for the size of the window
        Vector size = new Vector(1080, 720);

        // create an instance of the engine
        Engine engine = new Engine(screen, size, 60, 60, "game", "ui");

        // craete an instance of a class that extends Scene
        MyScene scene = new MyScene();

        // set the active scene to the scene created above
        engine.setActiveScene(scene);

        // start the engine
        engine.activate();
    }
}
```

```java	
class MyScene extends Scene
{
    // this method should be used to initialize the scene
    @Override
    public void init()
    {
        // craete a new gameObject
        GameObject gameObject = new GameObject();

        // create an instance of a class that extends Script
        MyScript script = new MyScript();

        // add the script to the gameObject
        gameObject.addScript(script);

        // add the gameObject to the scene
        addGameObject(gameObject);
    }


    // this method is called when the scene ends
    @Override
    public void end()
    {
        // do something, like freeing resources
    }
}
```

```java
class MyScript extends Script
{
    // note: this is of type engine.graphics.Image
    private Image helloWorldImage;

    protected void start() 
    {
        // load the image
        image = Image.fromFile("./rsc/hello_world.png")
    }

    @Override
    protected void render() 
    {
        // only render, if it is in the "ui" layer
        if (image().isNot("ui")) return;
        
        // draw the image
        image().drawImage(helloWorldImage, 10, 10);
    }
}
```

## Requirements
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or higher