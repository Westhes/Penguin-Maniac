/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys2.Game;

import fys2.Scenes.IGameScene;
import processing.core.*;
import java.util.Random;

/**
 *
 * @author *Removed*
 */
public class Coin {
    
    private IGameScene parent;
    private PApplet p;
    private Player player;
    private Terrain terrain;
    private Score score;
    private CoinManager manager;
    private PVector pos;
    private boolean collected = false;
    private int collTime = 0;
    private final int collTimeOut = 30;
    private float worldX;
    private PVector[] particlePos = new PVector[6];
    
    public Coin(IGameScene parent, CoinManager manager, PVector pos) 
    {
        this.parent = parent;
        p = parent.getPApplet();
        player = parent.getPlayer();
        terrain = parent.getTerrain();
        score = parent.getScore();
        this.manager = manager;
        this.pos = pos.add(0,-32);
    }
    
    public void update()
    {
        /*
        worldX = parent.getWorldX();
        if(collected)
        {
            if(collTime>collTimeOut)
                destroy();
            collTime++;
        }
        else
        {
            //remove when offscreen
            if(worldX+pos.x<worldX+terrain.terrainSegments.get(0).x)
                destroy();
            
            //collision with player. pretty forgiving since it's checking the player's position as a point.
            if(player.position.x>worldX+pos.x-48&&player.position.x<worldX+pos.x+48&&
                    player.position.y>pos.y-48&&player.position.y<pos.y+48)
            {
                //this will only be executed once, put score & speed bonus here
                collected = true;
                score.coinScore+=10;
                //p.println("coin get");
                
                Random random = new Random();
                for(int i=0;i<particlePos.length;i++){particlePos[i] = new PVector((float)random.nextGaussian()*24,(float)random.nextGaussian()*24);}
                //fills up the particle position array with random positions (gaussian so range is -1.0 to 1.0)
            }
        }
*/
    }
    
    public void draw()
    {
        if(collected)
        {
            //draw particles
            p.pushStyle();
            p.noStroke();
            p.fill(255,255,255);
            for(int i=0;i<particlePos.length;i++)
            {
                p.ellipse(worldX+pos.x+particlePos[i].x,pos.y+particlePos[i].y,12-collTime/(collTimeOut/12f),12-collTime/(collTimeOut/12f));
                //draws particle at given position with a size based on how much time has passed since it was collected
            }
            p.popStyle();
        }
        else
        {
            p.pushStyle();
            p.stroke(136, 102, 0);
            p.fill(255, 231, 47);
            p.ellipse(worldX+pos.x, pos.y, 48, 48);
            p.popStyle();
        }
    }
    
    public void destroy()
    {
        manager.coins.remove(this);
    }
}