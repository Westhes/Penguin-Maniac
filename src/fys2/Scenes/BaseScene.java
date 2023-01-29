/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys2.Scenes;

import processing.core.PApplet;

/**
 *
 * @author Westhes
 */
public abstract class BaseScene implements IScene {
    
    final public PApplet p;
    
    public BaseScene(IScene scene)
    {
        this.p = scene.getPApplet();
    }
    
    public BaseScene(PApplet pApplet)
    {
        this.p = pApplet;
    }
    
    @Override
    public PApplet getPApplet() {
        return p;
    }
}
