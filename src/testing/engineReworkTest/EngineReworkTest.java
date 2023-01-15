package testing.engineReworkTest;

import engine.Engine;
import engine.GameObject;
import engine.Scene;
import engine.Script;
import engine.utils.Screen;

import static engine.utils.MathUtils.*;

import java.awt.event.KeyEvent;

public class EngineReworkTest 
{
    public static void main(String[] args) 
    {
        Engine engine = new Engine(Screen.get(0), vec(1080, 720), 60, 60, "main");

        engine.setActiveScene(new TestScene());

        engine.activate();
    }

    public static class TestScene extends Scene
    {
        @Override
        protected void init() 
        {
            System.out.println("init");
            GameObject gameObject = new GameObject();

            addGameObject(gameObject);

            gameObject.addScript(new TestScript());

            gameObject.activate();
        }

        @Override
        protected void end() 
        {
            System.out.println("end");
        }
    }

    public static class TestScript extends Script
    {
        public TestScript() { activate(); }

        @Override
        protected void start() 
        {
            System.out.println("start");
        }

        @Override
        protected void update() 
        {
            System.out.println("update");

            if (engine().input().key(KeyEvent.VK_ENTER).isClicked())
                engine().setActiveScene(new TestScene());

            if (engine().input().key(KeyEvent.VK_ESCAPE).isDown())
                engine().deactivate();
        }

        @Override
        protected void render() 
        {
            System.out.println("render");
        }

        @Override
        protected void onActivate() {
            System.out.println("onActivate");
        }

        @Override
        protected void onDeactivate() {
            System.out.println("onDeactivate");
        }

        @Override
        protected void onDestroy() {
            System.out.println("onDestroy");
        }

        @Override
        protected void onGameObjectActivate() {
            System.out.println("onGameObjectActivate");
        }

        @Override
        protected void onGameObjectDeactivate() {
            System.out.println("onGameObjectDeactivate");
        }

        @Override
        protected void onGameObjectDestroy() {
            System.out.println("onGameObjectDestroy");
        }

        @Override
        protected void onSceneChange() {
            System.out.println("onSceneChange");
        }
    }
}
