/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys2.Scenes;

import fys2.Globals;
import processing.core.PApplet;

/**
 *
 * @author Westhes
 */
public class SceneManager
        extends BaseScene
{
    IScene[] gameScenes;
    IScene currentScene;
    int sceneProgress;

    // Constructor
    public SceneManager(PApplet p)
    {
        super(p);
    }

    // Initialize
    public void initialize()
    {
        gameScenes = new IScene[]
        {
            new TitleScreen(this), // Titlescreen
            new MainMenu(this), // Main menu
            new GameScene(this), // Game
            new ScoreScene(this), // High scores
        };

        // If the Debug is active, jump right to the core-game. 
        // Otherwise, show titlescreen.
        sceneProgress = (!Globals.DEBUG) ? GameScenes.TITLESCREEN : GameScenes.GAME;
        currentScene = gameScenes[sceneProgress];
        currentScene.initialize();
    }
    
    public void initialize(Object obj){
        initialize();
    }
    
    // Update
    public void update()
    {
        currentScene.update();
    }

    // Draw
    public void draw()
    {
        if (Globals.DEBUG)
        {
            p.background(0);
            p.fill(255);
            p.text("Whoopsie, you found an empty scene.\nPress 1-3 to get out.", p.width / 2, p.height / 2);
        }

        currentScene.draw();

        // Top left fps counter.
        // p.textAlign(p.CENTER);
        // p.textSize(32);
        // p.fill(0);
        //p.text(p.round(p.frameRate), p.width-40, 40);
    }

    // Keyboard pressed event
    public void keyPressedEvent(char key)
    {
        currentScene.keyPressedEvent(key);

        if (Globals.DEBUG)
        {
            // Jump to scene.. F1 etc doesn't work.
            if (key == '1')
            {
                jumpToScene(0);
            }
            if (key == '2')
            {
                jumpToScene(1);
            }
            if (key == '3')
            {
                jumpToScene(2);
            }
            if (key == '4')
            {
                jumpToScene(3);
            }
        }
    }

    // Keyboard release event
    public void keyReleasedEvent(char key)
    {
        currentScene.keyReleasedEvent(key);
    }

    // Show next scene
    public void nextScene()
    {
        jumpToScene(
                (sceneProgress >= gameScenes.length - 1)
                        ? 0
                        : sceneProgress + 1);
    }

    // Show previous screen
    public void previousScene()
    {
        jumpToScene(
                (sceneProgress <= 0)
                        ? gameScenes.length - 1
                        : sceneProgress - 1);
    }

    // Jump to scene at said index.
    public void jumpToScene(int index)
    {
        // Make sure we aren't currently in given scene.
        if (index == sceneProgress)
        {
            return;
        }

        // Make sure we're not out of range.
        if (index >= 0 && index <= gameScenes.length)
        {
            // This method might require more to stop the draw and update for a certain amount of time.
            currentScene.terminate();
            System.out.println(currentScene.toString() + " terminated!");

            sceneProgress = index;
            currentScene = gameScenes[sceneProgress];

            currentScene.initialize();
            System.out.println(currentScene.toString() + " initialized!");
        }
    }
    
    public void jumpToScene(int index, Object obj)
    {
        // Make sure we aren't currently in given scene.
        if (index == sceneProgress)
        {
            return;
        }

        // Make sure we're not out of range.
        if (index >= 0 && index <= gameScenes.length)
        {
            // This method might require more to stop the draw and update for a certain amount of time.
            currentScene.terminate();
            System.out.println(currentScene.toString() + " terminated!");

            sceneProgress = index;
            currentScene = gameScenes[sceneProgress];

            currentScene.initialize(obj);
            System.out.println(currentScene.toString() + " initialized!");
        }
    }

    public void terminate()
    {
        // You can't terminate me!
    }
}

// C# PogChamp https://stackoverflow.com/a/41570762/6590240
// Give every scene name a number..
class GameScenes
{

    final static short TITLESCREEN = 0,
            MAINMENU = 1,
            GAME = 2,
            HIGHSCORES = 3;
}
