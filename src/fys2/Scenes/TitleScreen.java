/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys2.Scenes;

import static processing.core.PApplet.sin;
import processing.core.PShape;
import processing.core.PVector;

/**
 *
 * @author *Removed*
 */
public class TitleScreen
        extends BaseScene {

    private SceneManager parent;

    PShape skyBackground;
    PShape sea;
    PShape fadeOutBox;

    final int charWidth = 160, charHeight = 160;
    PVector charYPosition;
    PVector charYVelocity;
    float charGravity = 0.05f;
    int charVelocityLimit = 3;

    public int terrainColor;
    public int skyColor;

    int fadeOutBoxOpacity;

    private int textYPosition = 1;
    float textYVelocity = 2;
    float textGravity = 0.2f;

    private boolean allowNextScene;
    private boolean gameStart;

    private PShape sprSlide;
    private PShape sprGlide;

    public TitleScreen(SceneManager scene) {
        super(scene);
        parent = scene;
    }

    @Override
    public void initialize() {
        allowNextScene = false;
        gameStart = false;
        fadeOutBoxOpacity = 1;

        terrainColor = p.color(148, 185, 255);
        skyColor = p.color(0, 102, 255);

        charYPosition = new PVector(0, p.width / 2);
        charYVelocity = new PVector(0, 0.0f);

        skyBackground = p.createShape(p.RECT, 0, 0, p.width, p.height);
        skyBackground.setFill(skyColor);
        skyBackground.setStroke(skyColor);

        sea = p.createShape(p.RECT, 0, p.height - 250, p.width, p.height);
        sea.setFill(terrainColor);
        sea.setStroke(terrainColor);

        fadeOutBox = p.createShape(p.RECT, 0, 0, p.width, p.height);

        sprGlide = p.loadShape("sprGlide.svg");
        sprSlide = p.loadShape("sprSlide.svg");
    }

    public void initialize(Object obj) {
        initialize();
    }

    @Override
    public void update() {
    }

    public boolean titleSlideIn() {
        p.textSize(64);
        p.textAlign(p.CENTER);
        p.fill(10, 50, 200);
        p.text("Penguin Maniac", p.width / 2, textYPosition);

        textYPosition += textYVelocity;
        textYVelocity += textGravity;

        if (textYPosition <= 0) {
            textYPosition = 0;
            textYVelocity *= -0.05;
        } else if (textYPosition >= p.height / 2) {
            textYPosition = p.height / 2;
            return true;
        }

        return false;
    }

    //Causes the 'Press any button to continue' to move on a sin wave.
    private void animateText() {
        //Creates the sin wave for the text size.
        //This causes the text to get bigger and smaller.
        float size = 32 - sin(p.millis() / 1000f) * 2;
        p.textSize(size);
        p.textAlign(p.CENTER);
        p.fill(10, 50, 200);
        //p.pushMatrix();
        //float rotate = 10 - sin(p.millis() / 1000f) * 2;
        p.text("Press any button to continue", p.width / 2, (p.height / 2) + (p.height / 4));
        //p.popMatrix();
        //When this text is shown, only then can the player advance to the game.
        allowNextScene = true;
    }

    //When the player presses any key, the screen will slowly fade out.
    //When the screen is fully covered, the next screen is called.
    private void gameStartFadeOut() {
        //Updates the PShape's opacity.
        fadeOutBox.setFill(p.color(0, 0, 0, fadeOutBoxOpacity));
        fadeOutBox.setStroke(p.color(0, 0, 0, fadeOutBoxOpacity));
        p.shape(fadeOutBox);

        if (fadeOutBoxOpacity >= 255) {
            //Once the screen is fully dark, move to the next screen.
            //The next screen in this case is the game.
            parent.jumpToScene(2);
        } else {
            //Animates the player to move forward.
            charYVelocity.y += charGravity;
            charYVelocity.limit(charVelocityLimit);
            charYPosition.add(charYVelocity);
            //Updates the fadeout PShape opacity value.
            fadeOutBoxOpacity += 3;
        }
    }

    @Override
    public void draw() {
        p.shape(skyBackground);
        p.shape(sea);

        if (titleSlideIn()) {
            animateText();
        }

        p.fill(255, 0, 0);
        p.shape(gameStart ? sprSlide : sprGlide, charYPosition.y - charWidth / 2, p.height - 250 - charWidth / 2 - charWidth * 0.12f, charWidth, charHeight);

        if (gameStart) {
            gameStartFadeOut();
        }
    }

    @Override
    public void terminate() {
    }

    @Override
    public void keyPressedEvent(char key) {

    }

    @Override
    public void keyReleasedEvent(char key) {
        if (allowNextScene) {
            gameStart = true;
        }
    }
}
