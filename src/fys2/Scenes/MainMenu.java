/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys2.Scenes;

import processing.core.PApplet;
import processing.core.PShape;

/**
 *
 * @author *Removed*
 */
public class MainMenu
        extends BaseScene {

    private SceneManager parent;

    PShape seaBox;
    PShape skyBox;

    int indexSelectOptions = 0;

    int cursorWidth = 10;
    int cursorHeight = 10;

    String[] selectOptions;

    public MainMenu(SceneManager scene) {
        super(scene);
        parent = scene;
    }

    @Override
    public void initialize() {
        seaBox = p.createShape(p.RECT, 0, 0, p.width, p.height - 150);
        seaBox.setFill(p.color(0, 102, 255));
        seaBox.setStroke(p.color(0, 102, 255));

        skyBox = p.createShape(p.RECT, 0, p.height - 150, p.width, p.height);
        skyBox.setFill(p.color(153, 204, 255));
        skyBox.setStroke(p.color(153, 204, 255));

        selectOptions = new String[]{
            "Start",
            "Highscores",
            "Options"
        };
    }

    public void initialize(Object obj) {
        initialize();
    }

    @Override
    public void update() {
    }

    @Override
    public void draw() {
        p.background(255);

        p.shape(seaBox);
        p.shape(skyBox);

        p.textAlign(p.CENTER);
        p.fill(10, 50, 200);

        p.text("Start", p.width / 2, (p.height / 2) - 50);
        p.text("Highscores", p.width / 2, p.height / 2);
        p.text("Options", p.width / 2, (p.height / 2) + 50);

        switch (indexSelectOptions) {
            case 0:
                p.rect(p.width / 2, (p.height / 2) - 50, cursorWidth, cursorHeight);
                break;
            case 1:
                p.rect(p.width / 2, (p.height / 2), cursorWidth, cursorHeight);
                break;
            case 2:
                p.rect(p.width / 2, (p.height / 2) + 50, cursorWidth, cursorHeight);
                break;
        }
    }

    @Override
    public void terminate() {
    }

    @Override
    public void keyPressedEvent(char key) {
        switch (key) {
            case 's':
                if (indexSelectOptions < 2) {
                    indexSelectOptions++;
                } else {
                    indexSelectOptions = 0;
                }
                break;
            case 'w':
                if (indexSelectOptions <= 0) {
                    indexSelectOptions = 2;
                } else {
                    indexSelectOptions--;
                }
                break;
        }
        System.out.println("indexSelectOptions: " + indexSelectOptions);
    }

    @Override
    public void keyReleasedEvent(char key) {
    }
}
