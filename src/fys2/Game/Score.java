/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys2.Game;
import fys2.Globals;
import fys2.Scenes.*;
import processing.core.*;

/**
 *
 * @author *Removed*
 */
public class Score {
    private GameScene parent;
    private Player player;
    private PApplet p;
    private SceneManager sceneManager;
    public int countdown;
    public int startTime;
    public int score;
    public int coinScore;
    private float sceneTime;
    private float pauseTime;
    
    PImage scoreUI;
    PImage timerUI;
    PFont f;

    
    public Score(GameScene parent) {
        this.parent = parent;
        p = parent.getPApplet();

        player = parent.getPlayer();
        sceneManager = parent.getSceneManager();
        score = 0;
        startTime = 60;
        sceneTime = p.millis();
        pauseTime = 0;
        coinScore = 0;
    }

    public void update() {
                                   // huidige tijd - 
        countdown = p.floor(startTime - (p.millis() - sceneTime) / 1000f) + coinScore/10;   //coin time bonus
        score = p.floor(player.getPosition().x) / 100 + coinScore;   //coin score bonus

        if (countdown == 0) {
            // setHighScores();
            sceneManager.jumpToScene(3, this);
        }
    }
    
    public void pauseUpdate()
    {
        //pauseTime = p.millis();
        sceneTime = p.millis();
    }
    
//    public void setHighScores() {
//    Globals.highScores = p.sort(Globals.highScores);
//        if (score > p.min(Globals.highScores)){
//                
//            Globals.highScores[0] = score;
//                
//        }
//        Globals.highScores = p.sort(Globals.highScores);
//        //System.out.println(Arrays.toString(Globals.highScores));
//    }
    
    public void draw() {
        // Load Font
        f = p.createFont("coiny-regular.ttf", 24);
        p.textFont(f);
        p.fill(255, 255, 255);
                
        // Draw the UI
        scoreUI = p.loadImage("score.png");
        p.image(scoreUI, 15, 15);
        p.textSize(24);
        p.text((score), 130, 47);
        
        timerUI = p.loadImage("timer.png");
        p.image(timerUI, 1125, 15);
        p.text((countdown), 1245, 47);

        // p.fill(0);
        // p.text("Score: " + (score) + "\n" + "Time Left: " + (countdown), 100, 300);
    }
}
