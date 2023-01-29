/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys2.Game;

import fys2.Scenes.IGameScene;
import processing.core.*;
import static processing.core.PApplet.*;

/**
 *
 * @author Westhes
 */
public class FlagPole {

    private IGameScene parent;
    private PApplet p;
    private Terrain terrain;

    private PShape flagPoleShape;
    private int maxFlags;
    private int progress;
    private PVector[] flagPositions;

    public float flagSeparationDistance = 1000f;

    // Initialise
    public FlagPole(IGameScene parent) {
        this.parent = parent;
        p = parent.getPApplet();
        terrain = parent.getTerrain();

        // Create as many flags as the screen could possibly need at a single time.
        maxFlags = ceil((p.width + 1) / flagSeparationDistance);
        flagPositions = new PVector[maxFlags];

        println("Max flags on screen = " + maxFlags);

        // Create the Buoy.
        // Should've just done this with a texture tbh.
        flagPoleShape = p.createShape(GROUP);
        PShape flag = p.createShape(RECT, 2, -30, 18, 10);
        PShape pole = p.createShape(RECT, -2, -30, 4, 30);
        PShape bouy = p.createShape(PShape.PATH);

        // Make the flag red
        flag.setFill(p.color(255, 0, 0));

        bouy.beginShape();
        bouy.vertex(2, -5);
        bouy.bezierVertex(10, -5, 10, 5, 2, 5);
        bouy.vertex(-2, 5);
        bouy.bezierVertex(-10, 5, -10, -5, 2, -5);
        bouy.endShape();

        flagPoleShape.addChild(flag);
        flagPoleShape.addChild(pole);
        flagPoleShape.addChild(bouy);
    }

    public void update() {
        for (int i = 0; i < maxFlags; i++) {
            // Check every update if our flags are considered offscreen.
            float tmp = parent.getWorldX() + (progress + i) * flagSeparationDistance;

            // if tmp is out of left bound
            if (tmp <= -50) {

                progress++;
                tmp = parent.getWorldX() + (progress + i) * flagSeparationDistance;
            }

            flagPositions[i] = terrain.getRawPointAtX(tmp);
        }
        flagPoleShape.resetMatrix();
        // Rotate our object for the time being.
        // To explain the following line. Sin creates a value between -1 and 1.
        // Millis/1000f is a float seconds. causing sin to change every frame. 
        float rotation = 360 - sin(p.millis() / 1000f) * 30;
        flagPoleShape.rotate(radians(rotation));
    }

    public void draw() {
        p.translate(parent.getWorldX(), 0);
        for (PVector pos : flagPositions) {
            p.shape(flagPoleShape, pos.x, pos.y);
        }
        p.translate(-parent.getWorldX(), 0);
    }
}
