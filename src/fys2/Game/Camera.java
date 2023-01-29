/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys2.Game;

import fys2.Scenes.IGameScene;
import processing.core.*;

/**
 *
 * @author *Removed*
 */
public class Camera {
    
    private PApplet p;
    private Terrain terrain;
    private Player player;
    
    float scale = 0.6f;
    final float maxScale = 0.3f; //technically min, but lower value = more zoom
    final float sens = 0.65f; //sensitivity, best around 0.5f - 0.7f
    final int xOffset = 300;
    final float ease = 0.06f;
    float bottomY = 0f;
    float diffY = 0f;
    float terrainY;
    
    public Camera(IGameScene parent) {
        this.p = parent.getPApplet();
        this.terrain = parent.getTerrain();
        this.player = parent.getPlayer();
    }
    
    public void update(){
        terrainY = (100 + 100) / 2;
        diffY = terrainY - bottomY;
        bottomY += diffY * ease;
        
        scale = p.pow(sens, ((bottomY - player.getPosition().y) / (p.height / 2)));
        if(scale < maxScale)  //really cheap limit
            scale = maxScale;
        
    }
    
    public void draw() {
        p.translate(p.width / 2 - player.getPosition().x * scale - xOffset,
                p.height / 2 - player.getPosition().y * scale);  //get player centered
        p.scale(scale);
        //System.out.println("cam scale= " + scale);
    }
    
    public float getRBound() {
        return (p.width-xOffset)/scale;
    }
    
    public float getLBound() {
        return -xOffset/scale;
    }
}