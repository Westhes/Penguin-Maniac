/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys2.Game;

import fys2.Main;
import fys2.Scenes.IGameScene;
import processing.core.*;

/**
 *
 * @author Westhes
 */
public class Player {

    // I mean, I could make them private.. But what are ya gonna do? Change the value?
    // Also, WHY ARE WE SCREAMING JAVA?!! ARE WE ANGRY?
    final static float GRAVITY = -0.1f;
    final static float GRAVITY_PRESSED = -0.7f;
    final static boolean DEBUG = false;
    final static boolean FORCE_GOING_FORWARD = true;
    final static float MININUM_SPEED = 3f;
    final static float JUMP_BOOST = 1.5f;

    private IGameScene parent;
    private PApplet p;
    private ITerrain sw;

    private PVector position = new PVector(40, -190);
    private PVector velocity = new PVector(0.1f, 0);
    private PVector waveDir = new PVector();
    private float speed = 3f;
    private float angle = 0f;
    private float steepestCurve = -1;
    private float airTime = 0f;

    private PShape sprSlide;
    private PShape sprGlide;

    boolean isGrounded = false;
    // Properties

    public float getGravity() {
        return (p.keyPressed) ? GRAVITY_PRESSED : GRAVITY;
    }

    public PVector getPosition() {
        return position;
    }

    public int IsGoingForward() {
        return (speed > 0) ? -1 : 3;
    }

    // Constructor
    public Player(IGameScene parent, ITerrain sw) {
        this.parent = parent;
        p = parent.getPApplet();
        this.sw = sw;
        sprSlide = p.loadShape("sprSlide.svg");
        sprGlide = p.loadShape("sprGlide.svg");
    }

    // Update method
    public void update() {
        // Get terrain difference between 2 points
        PVector ground = sw.getRawPointAtX(position.x);
        PVector forward = sw.getRawPointAtX(position.x + speed);

        // If we only want to move forward
        if (FORCE_GOING_FORWARD && speed < MININUM_SPEED) {
            speed = MININUM_SPEED;
        }

        // If in air
        if (position.y < ground.y) {
            // Apply gravity
            velocity.y -= getGravity();
            position.add(velocity);

            // Reset the curve
            steepestCurve = 0;
            airTime += Main.getDeltaTime();
            
            // Calculate angle based on movement
            angle = p.atan2(velocity.y,velocity.x);
        } // Else if grounded.
        else {
            if (airTime > 1f) {
                // Check terrain and if needed recalculate it so that it doesn't
                p.println("WICKED SICK AIRTIME BROOO!");
            }
            airTime = 0f;
            // Correct position to make sure it doesn't go underneath the ground
            position = forward;

            // Check which way we're moving
            waveDir = ((speed > 0) ? PVector.sub(forward, ground)
                    : PVector.sub(ground, forward)).normalize();

            // Obtain the angle out of the wave direction
            angle = p.atan2(waveDir.y, waveDir.x);

            // Apply gravity devided by the angle to the speed
            speed -= p.sin(angle) * getGravity();

            //velocity.y = 0;
            velocity = PVector.mult(waveDir, speed * JUMP_BOOST);

            // Only allow to jump when no keys are pressed.
            if (!p.keyPressed) {
                jumpCheck();
            }
        }

        speed *= 0.998f;
    }

    int charWidth = 100;
    int charHeight = 100;
    // Draw method

    public void draw() {
        p.fill(255, 0, 0);
        p.pushMatrix();
        p.translate(position.x,
                position.y);
        p.rotate(angle);
        p.shape((airTime == 0f || p.keyPressed) ? sprGlide : sprSlide,
                 - charWidth / 2, - charHeight / 2 - charHeight * 0.12f,
                charWidth, charHeight);
        p.popMatrix();

        /* debug */
        if (DEBUG) {
            p.fill(0);
            p.text("Speed = " + speed,
                    position.x + p.width - 400, 100);
            p.text("AirTime = " + airTime,
                    position.x + p.width - 400, 110);
            p.text("Sin(angle) = " + p.sin(angle),
                    position.x + p.width - 400, 90);

            // Draw the way gravity affects the speed.
            p.stroke(0, 0, 0);
            Line(position, new PVector(
                    position.x,
                    position.y - p.sin(angle) * speed * 30)
            );

            // Draw the direction the terrain is going.
            p.stroke(255, 0, 0);
            Line(position, new PVector(
                    position.x + (speed * waveDir.x * 30),
                    position.y + (speed * waveDir.y * 30)
            ));
            // Reset stroke to the default color.
            p.stroke(0);
        }
        /* end debug */
    }

    // TODO: remove this
    void Line(PVector a, PVector b) {
        p.line(a.x, a.y, b.x, b.y);
    }

    // Method that checks the terrain for when the ball should launch
    private void jumpCheck() {
        // Check if speed is just above (-)2.5
        if (p.abs(speed) > 2.5f) {
            // Modifier needed for forward and backward calculations
            int multiplier = -IsGoingForward();

            // Check if the current angle is higher that the last
            if (-p.sin(angle) * multiplier >= steepestCurve) {
                steepestCurve = -p.sin(angle) * multiplier;

                if (DEBUG) {
                    // p.println("Steepest curve = " + steepestCurve);
                    p.println("Speed = " + speed);
                    p.println("AirTime = " + airTime);
                }
            } else if (steepestCurve > 0.1f) {
                // Release the the player into the wild.
                position.y -= 1;
            }
        }
    }
}
