/*
 * This class serves as a container for various different sinewaves.
 */
package fys2.Game;

import fys2.Scenes.IGameScene;
import java.io.File;
import java.util.ArrayList;
import processing.core.*;
import processing.data.*;

/**
 *
 * @author Westhes
 */
public class Terrain implements ITerrain {

    private final static int DEFAULT_COLOR = -13395457;
    
    private IGameScene parent;
    private PApplet p;

    private PVector totalWaveLength = new PVector();
    private ArrayList<SuperSineWave> waves;

    
    // Constructor
    public Terrain(IGameScene parent) {
        this.parent = parent;
        p = parent.getPApplet();
        waves = new ArrayList<SuperSineWave>();

        String path = (new File("data/level.json").getAbsolutePath());
        JSONArray wavesJson = p.loadJSONArray(path);

        for (int i = 0; i < wavesJson.size(); i++) {
            JSONObject value = wavesJson.getJSONObject(i);
            float redAmplitude = value.getFloat("redAmplitude");
            float redPeriod = value.getFloat("redPeriod");
            float blueAmplitude = value.getFloat("blueAmplitude");
            float bluePeriod = value.getFloat("bluePeriod");

            SuperSineWave sw = new SuperSineWave(parent, redAmplitude, redPeriod, blueAmplitude, bluePeriod, totalWaveLength);
            totalWaveLength = PVector.add(totalWaveLength, sw.getWaveLength());
            waves.add(sw);
        }
    }

    // Method to render the terrain
    public void draw() {
        p.beginShape();
        p.fill(7, 203, 245);
        // Forces the begin of the line to always be off camera.
        p.vertex(-1000,0);
        for (int i = 0; i < waves.size(); i++) {
            waves.get(i).draw();
        }

        p.vertex(p.width + SuperSineWave.XSPACING - parent.getWorldX(), p.height);
        //p.vertex(0 - SuperSineWave.XSPACING - parent.getWorldX(), p.height);
        // Forces the end of the line to always be off camera.
        p.vertex(-1000, p.height);
        p.endShape();
    }

    // Returns the position of the wave at the given parameter.
    public PVector getRawPointAtX(float x) {
        for (SuperSineWave wave : waves) {
            if (wave.ContainsX(x)) {
                float tmp = x - wave.startPosition.x;
                PVector result = wave.getRawPointAtX(tmp);
                result.x += wave.startPosition.x;
                return result;
            }
        }
        //p.println("position not found!");
        return new PVector();
    }
}
