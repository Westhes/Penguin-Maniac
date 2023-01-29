/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys2.Game;

import fys2.Scenes.IGameScene;
import java.util.ArrayList;
import processing.core.*;

/**
 *
 * @author *Removed*
 */
public class CoinManager {

    private IGameScene parent;
    private PApplet p;
    private Player player;
    private Terrain terrain;

    public ArrayList<Coin> coins = new ArrayList<Coin>();
    private int nextDist = 20000;   //distance till next coin
    private int nextCoinX = 1280;
    private int minX, maxX;
    //private TerrainSegment terrainSeg;

    public CoinManager(IGameScene parent) {
        this.parent = parent;
        p = parent.getPApplet();
        player = parent.getPlayer();
        terrain = parent.getTerrain();
    }

    public void update() {
        /*terrainSeg = terrain.terrainSegments.get(terrain.terrainSegments.size()-1);
        if(nextCoinX <= terrainSeg.x2)
        {
            //creates new coin at the end of the terrain
            coins.add(new Coin(parent,this,new PVector(terrainSeg.x2,terrainSeg.y2)));
            nextCoinX+=nextDist;
        }
        for(int i=0;i<coins.size();i++){coins.get(i).update();} //update every coin
         */
    }

    public void draw() {
        p.translate(parent.getWorldX(), 0);
        for (Coin coin : coins) {
            coin.draw();
        }    //draw every coin
        p.translate(-parent.getWorldX(), 0);
    }
}
