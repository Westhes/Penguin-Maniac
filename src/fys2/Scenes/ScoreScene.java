/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys2.Scenes;
import fys2.Game.*;
import fys2.Globals;
import java.util.Arrays;
import processing.core.PImage;

/**
 *
 * @author Westhes
 */
public class ScoreScene
        extends BaseScene
        
        
    {   
    Score score;
    public SceneManager parent;
    
    PImage bg;
    
    
    public ScoreScene(SceneManager scene)
    {
        super(scene);
        parent = scene;
    }
    
    public void initialize(Object obj){
        
        if (obj instanceof Score)
        {
            score = (Score)obj;
        }
        
        Globals.highScores = p.sort(Globals.highScores);
        if (score.score > p.min(Globals.highScores)){
                
            Globals.highScores[0] = score.score;
                
        }
        Globals.highScores = p.sort(Globals.highScores);
        System.out.println(Arrays.toString(Globals.highScores));
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public void update()
    { 
    }

    @Override
    public void draw()
    {  
        bg = p.loadImage("scorescreen.png");
        p.background(bg);
        
        p.text(score.score, 637, 285);
        
        for (int i = 0; i < Globals.highScores.length; i++){
            
            String notif = (Globals.highScores[i] == score.score) ? "NEW " : "";
            
            p.fill(255, 255, 255);
            p.text(notif + Globals.highScores[i], 637, 356 + i*60);
            
        }
    }

    @Override
    public void terminate()
    {
    }

    @Override
    public void keyPressedEvent(char key)
    {
    }

    @Override
    public void keyReleasedEvent(char key)
    {
        parent.jumpToScene(2);
    }
}
