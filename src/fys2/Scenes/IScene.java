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
public interface IScene 
{
    public PApplet getPApplet();

    public void initialize();
    public void initialize(Object obj);
    public void update();
    public void draw();
    public void terminate();
    
    // Events
    public void keyPressedEvent(char key);
    public void keyReleasedEvent(char key);
}