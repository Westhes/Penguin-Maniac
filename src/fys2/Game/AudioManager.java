/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys2.Game;
import ddf.minim.*;
import fys2.Scenes.IGameScene;
import processing.core.PApplet;

/**
 *
 * @author *Removed*
 */
public class AudioManager {
    private Minim minim;
    private AudioPlayer inGameBGM;
    public boolean isPlaying = false;
    private PApplet p;
    
    public AudioManager(IGameScene parent) {
        p = parent.getPApplet();
        minim = new Minim(p);
        inGameBGM = minim.loadFile("bgm.wav");
    }
    
    public void play() {
        inGameBGM.loop();
        isPlaying = true;
    }
    
    public void stop() {
        isPlaying = false;
        inGameBGM.pause();
    }
    
    public void end() {
        //unloads file
        inGameBGM.close();
        isPlaying = false;
    }
}
