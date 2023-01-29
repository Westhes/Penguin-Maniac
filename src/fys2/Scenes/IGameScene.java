/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys2.Scenes;

import fys2.Game.*;
import processing.core.PApplet;

/**
 *
 * @author Westhes
 */
public interface IGameScene
{
    public PApplet getPApplet();
    public Terrain getTerrain();
    public Player getPlayer();
    public Score getScore();
    public int getWorldX();
    public int getWorldY();
}
