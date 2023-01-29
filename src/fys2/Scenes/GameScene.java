/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys2.Scenes;

import processing.core.*;
import fys2.Game.*;

/**
 *
 * @author Westhes
 */
public class GameScene
        extends BaseScene
        implements IGameScene {

    // Instance variables.
    int worldX = 0;
    int worldY = 0;
    
    // BG Image
    PImage bg;
    
    // Set Images
    PImage sun;
    PImage bigCloud;
    PImage smallCloud;
    PImage smallCloudLeft;

    boolean pauseState;
    boolean tutorial;

    Terrain terrain;
    //FlagPole flagPole;
    Player player;
    Camera camera;
    CoinManager coins;
    Score score;
    AudioManager audio;

    private SceneManager parent;

    PShape fadeOutBox;

    // Properties.
    @Override
    public Terrain getTerrain() {
        return terrain;
    }

    @Override
    public int getWorldX() {
        return worldX;
    }

    @Override
    public int getWorldY() {
        return worldY;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Score getScore() {
        return score;
    }

    public SceneManager getSceneManager() {
        return parent;
    }

    // Methods.
    public GameScene(SceneManager scene) {
        super(scene);
        parent = scene;
    }

    @Override
    public void initialize() {
        pauseState = true;
        tutorial = true;
        terrain = new Terrain(this);
        //flagPole = new FlagPole(this);
        player = new Player(this, terrain);
        camera = new Camera(this);
        coins = new CoinManager(this);
        score = new Score(this);
        audio = new AudioManager(this);

        fadeOutBox = p.createShape(p.RECT, 0, 0, p.width, p.height);
    }

    public void initialize(Object obj) {
        initialize();
    }

    @Override
    public void update() {
        worldX = -p.round(player.getPosition().x);
        //flagPole.update();
        coins.update();

        if (!pauseState) {
            player.update();
            score.update();
            if(!audio.isPlaying)
                audio.play();
        } else if (tutorial) {
            score.pauseUpdate();
        } else {
            if(audio.isPlaying)
                audio.stop();
        }
        camera.update();
    }

    @Override
    public void draw() {
        // Set background first
        //bg = p.loadImage("bg.png");
        p.background(226, 241, 255);

        // Draw Background Images
        sun = p.loadImage("sun.png");
        bigCloud = p.loadImage("cloud-right.png");
        smallCloud = p.loadImage("smaller-cloud.png");
        smallCloudLeft = p.loadImage("smaller-cloud-left.png");
        
        p.image(sun, 970, 81);
        p.image(bigCloud, 770, 40);
        p.image(smallCloud, 250, 85);
        p.image(smallCloudLeft, 560, 134);
        
        // Draw visuals
        p.pushMatrix();
        camera.draw();
        
        //flagPole.draw();
        terrain.draw();
        coins.draw();
        player.draw();
        p.popMatrix();

        // Draw the UI
        score.draw();

        // idk
        pauseStateHandler();
    }

    //Handles what needs to be shown when paused.
    private void pauseStateHandler() {
        if (pauseState) {
            //Fills the screen with a transparent rectangle.
            p.fill(0, 0, 0, 125);
            p.rect(0, 0, p.width, p.height);
            p.noFill();

            if (tutorial) {
                //When the player gets to this screen for the first time
                //the game will be paused and shows the player position
                //and what he or she has to do to play the game.
                p.pushMatrix();
                camera.draw();
                player.draw();
                p.popMatrix();

                p.textAlign(p.CENTER);
                p.fill(0);
                p.textSize(36);
                p.text("Space", p.width / 2, p.height / 2);
                p.noFill();
            } else {
                p.textAlign(p.CENTER);
                p.fill(0);
                p.textSize(36);
                p.text("Paused", p.width / 2, p.height / 2);
                p.noFill();
            }
        }
    }

    @Override
    public void terminate() {
        audio.end();
    }

    @Override
    public void keyPressedEvent(char key) {
        switch (key) {
            case 'p':
                //This will prevent the tutorial from going away if the
                //player presses the p key.
                if (tutorial) {
                    return;
                }
                
                if (pauseState) {
                    pauseState = false;
                } else {
                    pauseState = true;
                }
                break;
            case ' ':
                pauseState = false;
                tutorial = false;
                break;
        }
    }

    @Override
    public void keyReleasedEvent(char key) {
    }
}
